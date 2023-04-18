package pl.stormit.eduquiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final HttpClientConfig httpClientConfig;

    private final ObjectMapper objectMapper;

    @Bean(name = "EduQuizUsers")
    public RestTemplate eduQuizUsers() {
        return new RestTemplateBuilder()
                .rootUri(httpClientConfig.getEduQuizUsers())
                .build();
    }

}
