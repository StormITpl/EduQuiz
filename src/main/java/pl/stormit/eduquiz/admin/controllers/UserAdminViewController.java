package pl.stormit.eduquiz.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminViewController {

    private final UserService userService;
}
