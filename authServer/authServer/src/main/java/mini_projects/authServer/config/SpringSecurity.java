package mini_projects.authServer.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {


    private final CorsConfig config;

    public SpringSecurity(CorsConfig config){
        this.config=config;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(cors -> cors.configurationSource(config.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf->csrf.disable()) both means same, disable does not take an argument, hence not used this syntax
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }




}
