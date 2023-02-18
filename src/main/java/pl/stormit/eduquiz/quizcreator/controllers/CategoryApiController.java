package pl.stormit.eduquiz.quizcreator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("{id}")
    Category updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
