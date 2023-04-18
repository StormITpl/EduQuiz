package pl.stormit.eduquiz.http.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserHttpService {

    private static final String EDUQUIZ_USERS = "/api/v1/users";

    private static final String GET_ALL_USERS = EDUQUIZ_USERS;

    private static final String GET_ONE_USER = EDUQUIZ_USERS + "{userId}";

    private static final String CREATE_USER = EDUQUIZ_USERS;

    private static final String UPDATE_USER = EDUQUIZ_USERS + "{userId}";

    private static final String DELETE_USER = EDUQUIZ_USERS + "{userId}";

    @Autowired
    @Qualifier("EduQuizUsers")
    private RestTemplate restTemplate;

    public UserDto getUsers(final UUID id, String nickname, List<Quiz> quizzes, final String message
    ) {

        String url = UriComponentsBuilder
                .fromUriString(GET_ALL_USERS)
                .buildAndExpand(id, nickname)
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("The list of users has been successfully found", message);

        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(quizzes, headers), UserDto.class).getBody();
    }
}
