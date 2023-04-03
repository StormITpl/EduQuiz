package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    ResponseEntity<List<CategoryDto>> getCategories() {

        List<CategoryDto> categoryDtoList = categoryService.getCategories();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The list of categories has been successfully found");

        return new ResponseEntity<>(categoryDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("{category-id}")
    ResponseEntity<CategoryDto> getCategory(@NotNull @PathVariable("category-id") UUID categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The category has been successfully found");

        return new ResponseEntity<>(categoryDto, headers, HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryRequest) {

        CategoryDto createdCategory = categoryService.createCategory(categoryRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The category has been successfully created");

        return new ResponseEntity<>(createdCategory, headers, HttpStatus.CREATED);
    }

    @PutMapping("{category-id}")
    ResponseEntity<CategoryDto> updateCategory(@NotNull @PathVariable("category-id") UUID categoryId,
                                               @RequestBody CategoryDto categoryRequest) {
        CategoryDto createdCategory = categoryService.updateCategory(categoryId, categoryRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The category has been successfully updated");

        return new ResponseEntity<>(createdCategory,headers, HttpStatus.OK);
    }
    
    @DeleteMapping("{category-id}")
    ResponseEntity<Void> deleteCategory(@NotNull @PathVariable("category-id") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The category has been successfully deleted");

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
