package pl.stormit.eduquiz.quizcreator.domain.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryMapper;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapCategoryEntityToCategoryDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable) {
        return getCategories(null, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Category> getCategories(String search, Pageable pageable) {
        if(search == null) {
            return categoryRepository.findAll(pageable);
        } else {
            return categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        }
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategory(@NotNull @PathVariable("category-id") UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
        });
        return categoryMapper.mapCategoryEntityToCategoryDto(category);
    }

    @Transactional
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryRequest) {
            Category category = new Category();
            category.setName(categoryRequest.name());
            category.setId(categoryRequest.id());
            return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto updateCategory(@NotNull @PathVariable("category-id") UUID categoryId,
                                      @Valid @RequestBody CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
                });
        category.setName(categoryRequest.name());

        return categoryMapper.mapCategoryEntityToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(@NotNull @PathVariable("category-id") UUID categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new EntityNotFoundException("The category by id: " + categoryId + ", does not exist.");
        }
    }
    public boolean checkIfCategoryNameAvailable(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName).isEmpty();
    }
}