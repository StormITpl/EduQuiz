package pl.stormit.eduquiz.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminViewController {

    private final UserService userService;

    @GetMapping
    public String viewUsers(Model model) {

        model.addAttribute("users", userService.getUsers());

        return "admin/user/index";
    }
}
