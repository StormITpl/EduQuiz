package pl.stormit.eduquiz.authenticator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class AuthenticationViewController {


    private final UserService userService;

    @GetMapping()
    public String registerUser() {
//        UserDto registeredUser = userService.createUser(user);
        return "register";
    }

//    @PostMapping()
//    public String registerUser(@RequestBody UserRequestDto user){
//        UserDto registeredUser = userService.createUser(user);
//        return "register";
//    }
}
