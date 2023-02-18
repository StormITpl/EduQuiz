package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@WebMvcTest(CategoryApiController.class)
class CategoryApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
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

        String postContent = "name=Economy";

        MockHttpServletRequestBuilder request =
                post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postContent));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Category exemplaryCategory = new Category("Economy");

        Mockito.verify(categoryService, Mockito.times(1)).createCategory(exemplaryCategory);

    }

    @Test
    void shouldUpdateCategory() {
    }

    @Test
    void shouldDeleteCategory() {
    }
}