package mini_projects.authServer.config;

import mini_projects.authServer.repository.FilteredRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = "mini_projects.authServer.repository",
        repositoryBaseClass = FilteredRepositoryImpl.class
)
@Configuration
public class JpaConfig {}
