package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.UniversityMapper;
import tomaszewski.model.UniversityModel;
import tomaszewski.out.entities.UniversityEntity;
import tomaszewski.out.repositories.JpaUniversityRepository;
import tomaszewski.port.out.UniversityRepositoryPort;

@RequiredArgsConstructor
@Service
public class UniversityPersistenceAdapter implements UniversityRepositoryPort {

    private final JpaUniversityRepository jpaUniversityRepository;
    private final UniversityMapper universityMapper;

    @Override
    public UniversityModel getOrCreateByName(String name) {
        return jpaUniversityRepository.findByName(name)
                .map(universityMapper::toModel)
                .orElseGet(() -> {
                    UniversityEntity newEntity = new UniversityEntity();
                    newEntity.setName(name);
                    UniversityEntity saved = jpaUniversityRepository.save(newEntity);
                    return universityMapper.toModel(saved);
                });
    }
}
