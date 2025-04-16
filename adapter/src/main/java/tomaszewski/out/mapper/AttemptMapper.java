package tomaszewski.out.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.AttemptModel;
import tomaszewski.openapi.model.AttemptDTO;
import tomaszewski.out.entities.AttemptEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface AttemptMapper {
    AttemptModel toAttemptModel(AttemptEntity attemptEntity);

    AttemptDTO toAttemptDTO(AttemptModel attemptModel);

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}
