package pl.stormit.eduquiz.quizcreator.domain.category.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapCategoryEntityToCategoryDto(Category category);
}
