package pl.stormit.eduquiz.authenticator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class AuthenticationViewController {


    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping()
    public String registerUser(final Model model, Error error) {
        model.addAttribute("userRequestDto", new UserRequestDto(null,
                null, null, null, null, null, null));
        model.addAttribute("error", error);
        return "register";
    }

    @PostMapping()
    public String registerUser(@RequestParam(value = "password") String password,
                               @RequestParam(value = "confirmPassword") String confirmPassword,
                               @ModelAttribute UserRequestDto userRequestDto,
                               BindingResult bindingResult,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("confirmPassword", "Passwords are not the same");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userRequestDto);
        }

        if (userRepository.findByNickname(userRequestDto.nickname()).isPresent()) {
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
