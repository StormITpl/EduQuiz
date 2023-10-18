package pl.stormit.eduquiz.authenticator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class AuthenticationViewController {

    private final UserService userService;

    @GetMapping
    public String registerUser(final Model model) {
        UserRequestDto userRequestDto = new UserRequestDto(null, null, null, null, null, null, null);

        model.addAttribute("userRequestDto", userRequestDto);

        return "register";
    }

    @PostMapping()
    public String registerUser(@RequestParam(value = "confirmPassword") String confirmPassword,
                               @ModelAttribute UserRequestDto userRequestDto,
                               BindingResult bindingResult,
                               Model model) {
        if (userService.comparePasswords(userRequestDto.password(), confirmPassword)) {
            model.addAttribute("confirmPassword", "Passwords are not the same");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userRequestDto);
        }

        if (userService.checkIfNicknameAvailable(userRequestDto)) {
            bindingResult.rejectValue("nickname",
                    "userRequestDto.nickname",
                    "An account already exists for this nickname.");
        }

        if (model.containsAttribute("confirmPassword")
                || model.containsAttribute("registrationForm")
                || bindingResult.hasErrors()) {
            return "register";
        }

        userService.createUser(userRequestDto);

        model.addAttribute("userRequestDto", new UserRequestDto(null,
                null, null, null, null, null, null));
        model.addAttribute("message", "User was successfully registered!");

        return "register";
    }
}
