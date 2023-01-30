package pl.stormit.eduquiz.result.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/results")
public class ResultApiController {

    private final ResultService resultService;

    @GetMapping("{id}")
    void getResult(@PathVariable UUID id){

    }

}
