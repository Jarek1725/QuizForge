package tomaszewski.mapper;


import org.mapstruct.Mapper;
import tomaszewski.model.CategoryModel;
import tomaszewski.model.UniversityModel;
import tomaszewski.out.entities.CategoryEntity;
import tomaszewski.out.entities.UniversityEntity;

@Mapper(componentModel = "spring")
public interface UniversityMapper {
    default UniversityModel map(String name) {
        return new UniversityModel(null, name);
    }

    UniversityModel toModel(UniversityEntity universityEntity);

    default String mapUniversityToString(UniversityModel universityModel) {
        return universityModel != null ? universityModel.name() : null;
    }
}
