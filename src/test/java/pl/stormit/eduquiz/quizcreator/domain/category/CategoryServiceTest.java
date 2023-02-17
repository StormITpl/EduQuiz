package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
        Category category = new Category("Math");
        //when
        Category createdCategory = categoryService.createCategory(category);
        //then
        assertEquals(createdCategory.getName(), category.getName());
    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category category = new Category("Math");
        categoryRepository.save(category);
        Category categoryToUpdate = categoryService.getCategory(category.getId());
        categoryToUpdate.setName("Economy");
        //when
        Category updatedCategory = categoryService.updateCategory(category.getId(), categoryToUpdate);
        //then
        assertEquals(updatedCategory.getName(), "Economy");
        assertEquals(updatedCategory.getId(), category.getId());
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