package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private static final UUID FIRST_CATEGORY_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0255");

    private static final UUID SECOND_CATEGORY_ID = UUID.fromString("b92315cb-5862-4449-9826-ca09c76e0156");

    @Test
    void shouldReturn200WhenFoundAllCategoriesCorrectly() throws Exception {
        // given
        CategoryDto firstCategory = new CategoryDto(FIRST_CATEGORY_ID, "Chemistry");
        CategoryDto secondCategory = new CategoryDto(SECOND_CATEGORY_ID, "Biology");
        given(categoryService.getCategories()).willReturn(List.of(firstCategory, secondCategory));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(List.of(firstCategory, secondCategory)))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Chemistry")));
        result.andExpect(content().string(containsString("Biology")));
    }

    @Test
    void shouldReturn200WhenFoundOneCategoryByIdCorrectly() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(SECOND_CATEGORY_ID, "Biology");
        given(categoryService.getCategory(any())).willReturn(categoryDto);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/categories/" + SECOND_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Biology")));
        verify(categoryService, times(1)).getCategory((SECOND_CATEGORY_ID));
    }

    @Test
    void shouldReturn201WhenCategoryCreatedCorrectly() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(FIRST_CATEGORY_ID, "Economy");
        given(categoryService.createCategory(categoryDto)).willReturn(categoryDto);

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(content().string(containsString("Economy")));
        verify(categoryService, times(1)).createCategory((categoryDto));
    }

    @Test
    void shouldReturn200WhenCategoryUpdatedCorrectly() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(FIRST_CATEGORY_ID, "Chemistry");
        given(categoryService.updateCategory(FIRST_CATEGORY_ID, categoryDto)).willReturn(new CategoryDto(FIRST_CATEGORY_ID, "Office"));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/categories/" + FIRST_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Office")));
        verify(categoryService, times(1)).updateCategory(FIRST_CATEGORY_ID, categoryDto);
    }

    @Test
    void shouldReturn204WhenCategoryDeletedCorrectly() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(FIRST_CATEGORY_ID, "Biology");

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/categories/" + FIRST_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isNoContent());
    }
}
