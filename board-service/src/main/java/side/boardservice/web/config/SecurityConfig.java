package side.boardservice.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import side.boardservice.web.handler.LoginFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //해당 메서드의 리턴되는 오브젝트를 IOC로 등록해줌
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()

                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/boards/**").authenticated()
                        .anyRequest().permitAll()
                )

                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/boards")
                .failureHandler(loginFailureHandler());

        return http.build();
    }

}
