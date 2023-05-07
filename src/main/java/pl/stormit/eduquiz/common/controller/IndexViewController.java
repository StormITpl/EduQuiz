package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String indexView(Model model){

        model.addAttribute("categories", categoryService.getCategories());

        return "index";
    }
}
