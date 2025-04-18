package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.CategoryModel;
import tomaszewski.model.ExamModel;
import tomaszewski.openapi.model.CreateExamDTO;
import tomaszewski.openapi.model.ExamDTO;
import tomaszewski.out.entities.ExamEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UniversityMapper.class, CategoryMapper.class, QuestionMapper.class})
public interface ExamMapper {
    @Mapping(target = "categories", source = "categories")
    ExamModel toExamModel(CreateExamDTO createExamDTO);

    @Mapping(target = "categories", source = "categories")
    ExamEntity toExamEntity(ExamModel examModel);

    @Mapping(target = "categories", source = "categories")
    ExamModel toExamModel(ExamEntity save);

    @Mapping(target = "categories", source = "categories")
    ExamDTO toExamDTO(ExamModel examModel);


}
