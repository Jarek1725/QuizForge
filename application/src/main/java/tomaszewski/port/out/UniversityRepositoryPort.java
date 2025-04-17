package tomaszewski.port.out;

import tomaszewski.model.UniversityModel;

public interface UniversityRepositoryPort {
    UniversityModel getOrCreateByName(String name);
}
