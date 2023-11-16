package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
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
    void shouldReturn404WhenCategoryIdNotFound() throws Exception {
        // given
        UUID nonExistentCategoryId = UUID.randomUUID();
        given(categoryService.getCategories()).willThrow(new EntityNotFoundException("Category not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/categories/" + nonExistentCategoryId + "/questions")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
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
    void shouldReturn404WhenCategoryNotFound() throws Exception {
        // given
        UUID nonExistentCategoryId = UUID.randomUUID();
        given(categoryService.getCategory(nonExistentCategoryId))
                .willThrow(new EntityNotFoundException("Category not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/categories/" + nonExistentCategoryId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
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
    void shouldReturn404WhenCreatingCategoryForNonExistentQuestion() throws Exception {
        // given
        UUID nonExistentCategoryId = UUID.randomUUID();
        CategoryDto categoryDto = new CategoryDto(nonExistentCategoryId, "Sport");

        given(categoryService.createCategory(categoryDto))
                .willThrow(new EntityNotFoundException("The category by id: " + nonExistentCategoryId + ", does not exist."));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isNotFound());
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
    void shouldReturn404WhenCategoryUpdateFails() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(FIRST_CATEGORY_ID, "Chemistry");
        given(categoryService.updateCategory(FIRST_CATEGORY_ID, categoryDto))
                .willThrow(new EntityNotFoundException("Category update failed"));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/categories/" + FIRST_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Category update failed")));
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

    @Test
    void shouldReturn404WhenCategoryDeletionFails() throws Exception {
        // given
        CategoryDto categoryDto = new CategoryDto(FIRST_CATEGORY_ID, "Biology");
        doThrow(new EntityNotFoundException("Category deletion failed"))
                .when(categoryService)
                .deleteCategory(FIRST_CATEGORY_ID);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/categories/" + FIRST_CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Category deletion failed")));
    }
}
