package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryMapper;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper  categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public CategoryDto createCategory(@NotNull CategoryDto categoryRequest) {
        Category category = new Category(categoryRequest.name());
        return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto updateCategory(UUID id, @NotNull CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();
        category.setName(categoryRequest.name());
        return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}

