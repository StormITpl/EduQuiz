package pl.stormit.eduquiz.quizcreator.category.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapCategoryEntityToCategoryDto(Category category);
}
