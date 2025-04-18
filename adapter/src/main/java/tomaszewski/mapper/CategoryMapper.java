package tomaszewski.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.CategoryModel;
import tomaszewski.out.entities.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryModel toModel(CategoryEntity categoryEntity);

    default CategoryModel map(String name) {
        return new CategoryModel(null, name);
    }

    default List<String> mapToNames(List<CategoryModel> categories) {
        return categories.stream()
                .map(CategoryModel::name)
                .collect(Collectors.toList());
    }
}
