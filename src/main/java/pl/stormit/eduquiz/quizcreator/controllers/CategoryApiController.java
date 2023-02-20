package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")

public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("{id}")
    Category getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }


    @PostMapping
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryRequest) {
        CategoryDto createdCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryRequest) {
        CategoryDto createdCategory = categoryService.updateCategory(id, categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
