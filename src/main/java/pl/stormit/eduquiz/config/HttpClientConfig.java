package pl.stormit.eduquiz.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class HttpClientConfig {

    @Value("${http-client.eduquiz-users.base-url}")
    private String eduQuizUsers;

}
