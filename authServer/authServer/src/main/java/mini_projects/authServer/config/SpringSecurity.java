package mini_projects.authServer.config;


import mini_projects.authServer.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {


    private final CorsConfig config;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SpringSecurity(CorsConfig config, PasswordEncoderConfig passwordEncoderConfig, UserDetailsServiceImpl userDetailsServiceImpl){
        this.config=config;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(cors -> cors.configurationSource(config.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf->csrf.disable()) both means same, disable does not take an argument, hence not used this syntax
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/user/register", "/user/login").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsServiceImpl)
                .build();
    }

}
