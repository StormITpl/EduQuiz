package pl.stormit.eduquiz.result.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.result.domain.model.Result;

@Mapper(componentModel = "spring")
public interface ResultMapper {

    ResultDto mapResultEntityToResultDto(Result result);
}
