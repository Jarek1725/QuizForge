package tomaszewski.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.ExamModel;
import tomaszewski.openapi.model.CreateExamDTO;
import tomaszewski.out.entities.ExamEntity;

@Mapper(componentModel = "spring", uses = {UniversityMapper.class, CategoryMapper.class})
public interface ExamMapper {
    @Mapping(target = "categories", source = "categories")
    ExamModel toExamModel(CreateExamDTO createExamDTO);

    @Mapping(target = "categories", source = "categories")
    ExamEntity toExamEntity(ExamModel examModel);

    @Mapping(target = "categories", source = "categories")
    ExamModel toModel(ExamEntity save);
}
