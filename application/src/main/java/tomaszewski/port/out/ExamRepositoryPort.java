package tomaszewski.port.out;

import tomaszewski.model.ExamModel;

import java.util.List;

public interface ExamRepositoryPort {
    void save(ExamModel examModel);
}
