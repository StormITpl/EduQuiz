package pl.stormit.eduquiz.authenticator.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class AuthenticationViewController {


    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping()
    public String registerUser(final Model model, Error error) {
        model.addAttribute("userRequestDto", new UserRequestDto(null, null));
        model.addAttribute("error", error);
        return "register";
    }

    @PostMapping()
    public String registerUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userRequestDto);
            return "register";
        }

        if (userRepository.findByNickname(userRequestDto.nickname()).isPresent()) {
            bindingResult.rejectValue("nickname",
                    "userRequestDto.nickname",
                    "An account already exists for this nickname.");
            return "register";
        }

        userService.createUser(userRequestDto);

        model.addAttribute("userRequestDto", new UserRequestDto(null, null));
        model.addAttribute("message", "User was successfully registered");

        return "register";
    }
}
