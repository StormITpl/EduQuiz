package pl.stormit.eduquiz.authenticator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable();
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/categoryManagement").hasRole("ADMIN")
                        .anyRequest().permitAll());

        return http.build();
    }

    // Przed następnym spotkaniem:
    // Config - zostawić na ten moment tak jak jest
    // Jak zrobić Controller (rejestracja i logowanie)?
    // Czy potrzeba więcej repozytoriów ? (Czy wystarczy tylko obecne UserRepository)
    // AuthenticationManager - jak zrobić i co potrzeba?
    // PasswordEncoder - to co wyżej

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
