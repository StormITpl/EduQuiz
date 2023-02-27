package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@RequiredArgsConstructor
@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetUsers() throws Exception {
        // given
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d3");
        UserDto firstDtoUser = new UserDto(ID_1, "Ananiasz");
        final UUID ID_2 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d4");
        UserDto secondDtoUser = new UserDto(ID_2, "Wojski");
        final UUID ID_3 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d5");
        UserDto thirdDtoUser = new UserDto(ID_2, "Dajmiech");

        //when
        List<UserDto> expectedDtoUsers = Arrays.asList(firstDtoUser, secondDtoUser, thirdDtoUser);
        when(userService.getUsers()).thenReturn((expectedDtoUsers));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("Ananiasz")))
                .andExpect(content().string(containsString("Wojski")))
                .andExpect(content().string(containsString("Dajmiech")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldGetUserById() throws Exception {
        // given
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d3");
        UserDto expectedDtoUser = new UserDto(ID_1, "Ananiasz");
        String userUrl = "/api/v1/users/" + expectedDtoUser.id();

        //when
        given(userService.getUser(any())).willReturn(expectedDtoUser);

        //then
        mockMvc.perform(get(userUrl))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ananiasz")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Rollback
    @Test
    void shouldCreateUser() throws Exception {
        // given
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d6");
        UserDto createdUserDto = new UserDto(ID_1, "Hegemon");

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdUserDto));

        // then
        mockMvc.perform(content)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Rollback
    @Test
    void shouldUpdateUser() throws Exception {
        //given
        User user = new User("Hegemon");
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d6");
        UserDto updatedUserDto = new UserDto(ID_1, "Hegemon The Great");

        //then
        MockHttpServletRequestBuilder content = put(
                "/api/v1/users/{userId}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDto));

        //when
        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Rollback
    @Test
    void shouldDeleteUser() throws Exception {
        //given
        User deletedUser = new User("Hegemon");

        //then
        MockHttpServletRequestBuilder content = delete(
                "/api/v1/users/{userId}", deletedUser.getId())
                .contentType(MediaType.APPLICATION_JSON);

        //when
        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}