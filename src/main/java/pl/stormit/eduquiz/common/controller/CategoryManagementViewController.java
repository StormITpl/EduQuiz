package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/categoryManagement")
@AllArgsConstructor
public class CategoryManagementViewController {
    private final CategoryService categoryService;

    @GetMapping
    public String categoryManagement(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "categoryManagement";
    }

    @PostMapping
    public String addCategory(@RequestParam String categoryName) {
        CategoryDto categoryDto = new CategoryDto(null, categoryName);
        categoryService.createCategory(categoryDto);
        return "redirect:/categoryManagement";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return "redirect:/categoryManagement";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable UUID id, Model model) {
        CategoryDto categoryDto = categoryService.getCategory(id);
        model.addAttribute("category", categoryDto);
        return "editCategory";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable UUID id, @RequestParam String categoryName) {
        CategoryDto categoryDto = new CategoryDto(id, categoryName);
        categoryService.updateCategory(id, categoryDto);
        return "redirect:/categoryManagement";
    }
}
