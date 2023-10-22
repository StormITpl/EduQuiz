package pl.stormit.eduquiz.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminViewController {

    private final UserService userService;

    @GetMapping
    public String indexView(Model model) {
        long totalUsers = userService.getTotalNumberOfUsers();
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        long usersRegisteredLastWeek = userService.getNumberOfUsersCreatedBetween(oneWeekAgo, Instant.now());

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("usersRegisteredLastWeek", usersRegisteredLastWeek);

        return "admin/index";
    }
}
