package pl.stormit.eduquiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI githubRepositoryOpenApi () {

        return new OpenAPI()
                        .info(new Info().title("REST API for 'EduQuiz application'")
                        .description("EduQuiz is a platform for creating and conducting interactive quizzes. It is an/n" +
                                "excellent tool for consolidating knowledge individually or in a group."));
    }
}
