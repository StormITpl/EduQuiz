package pl.stormit.eduquiz.authenticator;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {


    public CustomDaoAuthenticationProvider(CustomPasswordEncoder customPasswordEncoder,
                                           CustomUserDetailService customUserDetailService) {
        super.setPasswordEncoder(customPasswordEncoder);
        super.setUserDetailsService(customUserDetailService);
    }
}
