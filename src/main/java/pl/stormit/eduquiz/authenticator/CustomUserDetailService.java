package pl.stormit.eduquiz.authenticator;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;

import java.util.Collections;

@Component
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;
    private final CustomPasswordEncoder customPasswordEncoder;

    public CustomUserDetailService(UserRepository userRepository,
                                   CustomPasswordEncoder customPasswordEncoder) {
        this.userRepository = userRepository;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user"));
        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                customPasswordEncoder.encode("pass"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
