package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@WebMvcTest(CategoryApiController.class)
class CategoryApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;

    @Test
    void shouldReturnAllCategories() throws Exception {

        Category firstCategory = new Category("Chemistry");
        Category secondCategory = new Category("Biology");

        given(categoryService.getCategories()).willReturn(List.of(firstCategory, secondCategory));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Chemistry")))
                .andExpect(content().string(containsString("Biology")));

    }

    @Test
    void shouldReturnOneCategoryById() throws Exception {

        Category category = new Category("Biology");
        String url = "/api/v1/categories/" + category.getId();

        given(categoryService.getCategory(any())).willReturn(category);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Biology")));
    }

    @Test
    void shouldCreateCategory() throws Exception {

        CategoryDto exemplaryCategoryDto = new CategoryDto("Economy");

        MockHttpServletRequestBuilder content = post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryCategoryDto));


        mockMvc.perform(content)
                .andExpect(status().isCreated());


    }

    @Test
    void shouldUpdateCategory() throws Exception {

        Category exemplaryCategory = new Category("Economy");
        CategoryDto exemplaryCategoryDto = new CategoryDto(exemplaryCategory.getName());

        MockHttpServletRequestBuilder content = put(
                "/api/v1/categories/{categoryId}", exemplaryCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryCategoryDto));

        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldDeleteCategory() throws Exception {

        Category exemplaryCategory = new Category("Biology");

        MockHttpServletRequestBuilder content = delete(
                "/api/v1/categories/{categoryId}", exemplaryCategory.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}