package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {

    private static final UUID FIRST_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0221");

    private static final UUID SECOND_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0123");

    private static final UUID THIRD_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0117");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenFoundAllResults() throws Exception {
        // given
        UserDto firstDtoUser = new UserDto(
                FIRST_ID,
                "Ananiasz",
                "ananiasz@gmail.com",
                "password",
                null,
                null,
                null,
                List.of());

        UserDto secondDtoUser = new UserDto(
                SECOND_ID,
                "Wojski",
                "wojski@gmail.com",
                "password",
                null,
                null,
                null,
                List.of());
        UserDto thirdDtoUser = new UserDto(
                THIRD_ID,
                "Dajmiech",
                "dajmiech@gmail.com",
                "password",
                null,
                null,
                null,
                List.of());
        List<UserDto> expectedDtoUsers = Arrays.asList(firstDtoUser, secondDtoUser, thirdDtoUser);
        given(userService.getUsers()).willReturn((expectedDtoUsers));

        // when
        MockHttpServletRequestBuilder content = get("/api/v1/users");

        // then
        mockMvc.perform(content).andExpect(status().isOk())
                .andExpect(content().string(containsString("Ananiasz")))
                .andExpect(content().string(containsString("Wojski")))
                .andExpect(content().string(containsString("Dajmiech")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturn200IfUserIsFoundByIdCorrectly() throws Exception {
        // given
        UserDto expectedDtoUser = new UserDto(FIRST_ID,
                "Ananiasz",
                "ananiasz@gmail.com",
                "password",
                null,
                null,
                null,
                List.of());
        String userUrl = "/api/v1/users/" + expectedDtoUser.id();

        // when
        given(userService.getUser(FIRST_ID)).willReturn(expectedDtoUser);

        // then
        mockMvc.perform(get(userUrl)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Ananiasz")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Rollback
    @Test
    void shouldReturn201WhenUserCreatedCorrectly() throws Exception {
        // given
        UserDto createdUserDto = new UserDto(FIRST_ID,
                "Ananiasz",
                "ananiasz@gmail.com",
                "Password123!",
                null,
                null,
                null,
                List.of());

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdUserDto));

        // then
        mockMvc.perform(content).andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Rollback
    @Test
    void shouldReturn200WhenUserUpdatedCorrectly() throws Exception {
        // given
        UserDto userDto = new UserDto(
                FIRST_ID,
                "Ananiasz",
                "ananiasz@gmail.com",
                "Password123!",
                null,
                null,
                null,
                List.of());
        UserRequestDto requestDto = new UserRequestDto(
                "Dajmiech",
                "dajmiech@gmail.com",
                "Password123!",
                null,
                null,
                null,
                List.of());

        // then
        MockHttpServletRequestBuilder content = put("/api/v1/users/{userId}", FIRST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(requestDto)));

        // when
        mockMvc.perform(content).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1))
                .updateUser(eq(FIRST_ID), eq(requestDto));
    }

    @Rollback
    @Test
    void shouldReturn204WhenUserDeletedCorrectly() throws Exception {
        // then
        MockHttpServletRequestBuilder content = delete("/api/v1/users/{userId}", FIRST_ID).contentType(MediaType.APPLICATION_JSON);

        // when
        mockMvc.perform(content).andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1))
                .deleteUser(eq(FIRST_ID));
    }
}
