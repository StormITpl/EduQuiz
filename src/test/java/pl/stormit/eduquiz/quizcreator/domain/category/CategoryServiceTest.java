package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryRepository;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Category firstCategory = new Category("Chemistry");
        Category secondCategory = new Category("Biology");
        categoryRepository.saveAll(List.of(firstCategory, secondCategory));
        //when
        List<Category> categories = categoryService.getCategories();
        //then
        assertThat(categories)
                .hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Chemistry", "Biology");
    }

    @Test
    void shouldReturnOneCategoryFoundById() {
        //given
        Category category = new Category("Math");
        categoryRepository.save(category);
        //when
        Category categoryFoundById = categoryService.getCategory(category.getId());
        //then
        assertEquals(categoryFoundById, category);
    }

    @Test
    void shouldCreateCategory() {
        //given
        CategoryDto  categoryRequestDto = new CategoryDto("Math");
        //when
        CategoryDto createdCategory = categoryService.createCategory(categoryRequestDto);
        //then
        assertEquals(createdCategory.name(), categoryRequestDto.name());
    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category category = new Category("Math");
        categoryRepository.save(category);
        Category categoryToUpdate = categoryService.getCategory(category.getId());
        categoryToUpdate.setName("Economy");
        CategoryDto categoryToUpdateDto = new CategoryDto(categoryToUpdate.getName());
        //when
        CategoryDto updatedCategory = categoryService.updateCategory(category.getId(), categoryToUpdateDto);
        //then
        assertEquals(updatedCategory.name(), "Economy");
    }

    @Test
    void shouldDeleteCategory() {
        //given
        Category category = new Category("Math");
        categoryRepository.save(category);
        //when
        categoryService.deleteCategory(category.getId());
        //then
        assertTrue(categoryRepository.findById(category.getId()).isEmpty());
    }
}