package pl.stormit.eduquiz.admin.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;

import java.util.UUID;

import static pl.stormit.eduquiz.common.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String indexView(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        String reverseSort;

        if ("asc".equals(direction)) {
            reverseSort = "desc";
        } else {
            reverseSort = "asc";
        }

        Page<Category> categoriesPage = categoryService.getCategories(search, pageable);
        model.addAttribute("categoriesPage", categoriesPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);

        paging(model, categoriesPage);

        return "admin/category/index";
    }

    @GetMapping("add")
    public String addView(Model model) {
        model.addAttribute("category", new CategoryDto(null, null));

        return "admin/category/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("category") Category category,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {

        if (categoryService.checkIfCategoryNameAvailable(category.getName())) {

            if (bindingResult.hasErrors()) {

                return "admin/category/add";
            }

            CategoryDto categoryDto = new CategoryDto(null, category.getName());
            categoryService.createCategory(categoryDto);
            redirectAttributes.addFlashAttribute("message", "Category successfully added");

            return "redirect:/admin/categories";
        }
        else
        {
            model.addAttribute("sameCategory", "A category with the same name already exists in the database");

            return "admin/category/add";
        }
    }

    @GetMapping("{id}")
    public String editView(@PathVariable UUID id, Model model) {

        CategoryDto categoryDto = categoryService.getCategory(id);
        model.addAttribute("category", categoryDto);

        return "admin/category/edit";
    }

    @PostMapping("{id}")
    public String edit(
            @PathVariable UUID id,
            @Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (categoryService.checkIfCategoryNameAvailable(category.getName())) {

            if (bindingResult.hasErrors()) {

                return "admin/category/edit";
            }
                try {
                    CategoryDto categoryDto = new CategoryDto(id, category.getName());
                    categoryService.updateCategory(id, categoryDto);
                    redirectAttributes.addFlashAttribute("message", "Category successfully saved");

                } catch (Exception ex) {
                    model.addAttribute("message", "An error occured. Category not saved");

                    return "admin/category/edit";
                }

            return "redirect:/admin/categories";
        }
        else
        {
            model.addAttribute("sameCategory", "A category with the same name already exists in the database");

            return "admin/category/edit";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", "Category successfully deleted");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "An error occured. Category not deleted");

            return "redirect:/admin/categories";
        }
        return "redirect:/admin/categories";
    }
}



