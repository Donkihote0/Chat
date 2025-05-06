package messenger.messenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("donkihote").password("{noop}2404").roles("USER").build(),
                User.withUsername("dmm").password("{noop}dmm").roles("USER").build()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // login form mặc định
                .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
                .csrf(csrf -> csrf.disable()); // disable CSRF nếu cần test dễ hơn

        return http.build();
    }
}


