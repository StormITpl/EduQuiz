package pl.stormit.eduquiz.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.stats.controller.AdminViewControllerStats;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminViewController {

    private final AdminViewControllerStats statsService;

    @GetMapping
    public String indexView(Model model) {
        model.addAttribute("totalUsers", statsService.getTotalUsers());
        model.addAttribute("usersRegisteredLastWeek", statsService.getUsersRegisteredLastWeek());
        return "admin/index";
    }
}
