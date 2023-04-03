package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldReturnTwoCategories() {
        //given
        Category firstCategory = new Category();
        firstCategory.setName("Chemistry");
        Category secondCategory = new Category();
        secondCategory.setName("Biology");
        categoryRepository.saveAll(List.of(firstCategory, secondCategory));
        //when
        List<CategoryDto> categoriesDto = categoryService.getCategories();
        //then
        assertThat(categoriesDto)
                .hasSize(2)
                .extracting(CategoryDto::name)
                .containsExactlyInAnyOrder("Chemistry", "Biology");
    }

    @Test
    void shouldReturnOneCategoryFoundById() {
        //given
        Category category = new Category();
        category.setName("Math");
        categoryRepository.save(category);
        //when
        CategoryDto categoryDtoFoundById = categoryService.getCategory(category.getId());
        //then
        assertEquals(categoryDtoFoundById.name(), category.getName());
    }

    @Test
    void shouldCreateCategory() {
        //given
        Category category = new Category();
        category.setName("Chemistry");
        CategoryDto categoryRequestDto = new CategoryDto(
                category.getId(),category.getName());
        //when
        CategoryDto createdCategory = categoryService.createCategory(categoryRequestDto);
        //then
        assertEquals(createdCategory.name(), categoryRequestDto.name());
        assertNotNull(createdCategory.id());
    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category category = new Category();
        category.setName("Math");
        categoryRepository.save(category);
        CategoryDto categoryToUpdate = new CategoryDto(category.getId(),"Physics");
        //when
        CategoryDto updatedCategory = categoryService.updateCategory(category.getId(), categoryToUpdate);
        //then
        assertEquals(updatedCategory.name(), "Physics");
    }

    @Test
    void shouldDeleteCategory() {
        //given
        Category category = new Category();
        category.setName("Physics");
        categoryRepository.save(category);
        //when
        categoryService.deleteCategory(category.getId());
        //then
        assertTrue(categoryRepository.findById(category.getId()).isEmpty());
    }
}