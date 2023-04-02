package pl.stormit.eduquiz.result.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles({"test"})
@WebMvcTest(ResultApiController.class)
class ResultApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResultService resultService;

    @Test
    void shouldCreateResultUsingGameId() throws Exception {

        GameIdDto exemplaryGameIdDto = new GameIdDto(UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82502"));

        MockHttpServletRequestBuilder content = post("/api/v1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryGameIdDto));

        mockMvc.perform(content)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnResultById() throws Exception {

        ResultDto exemplaryResultDto = new ResultDto(UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82502"), null);
        given(resultService.createResult(any())).willReturn(exemplaryResultDto);

        MockHttpServletRequestBuilder content = get("/api/v1/results/" + exemplaryResultDto.id())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(content)
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteResult() throws Exception {

        ResultDto exemplaryResultDto = new ResultDto(UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82502"), null);

        MockHttpServletRequestBuilder content = delete("/api/v1/results/" + exemplaryResultDto.id())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(content)
                .andExpect(status().isNoContent());
    }
}
