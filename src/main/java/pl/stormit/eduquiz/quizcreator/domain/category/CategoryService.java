package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryMapper;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper  categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapCategoryEntityToCategoryDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
        });
        return categoryMapper.mapCategoryEntityToCategoryDto(category);
    }

    @Transactional
    public CategoryDto createCategory(@NotNull CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setId(categoryRequest.id());
        return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto updateCategory(UUID categoryId, @NotNull CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
                });
        category.setName(categoryRequest.name());
        return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
        }
    }
}

