package tomaszewski.in.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.CategoryModel;
import tomaszewski.out.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryModel toModel(CategoryEntity categoryEntity);

    default CategoryModel map(String name) {
        return new CategoryModel(null, name);
    }
}
