package pl.stormit.eduquiz.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminViewController {

    private final UserService userService;

    @GetMapping
    public String viewUsers(
            @RequestParam(name = "search", required = false) String search,
            Model model) {

        model.addAttribute("users", userService.getUsers(search));

        return "admin/user/index";
    }
}
