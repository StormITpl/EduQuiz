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

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("There is no such user"));
        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
