package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.in.mapper.CategoryMapper;
import tomaszewski.model.CategoryModel;
import tomaszewski.out.entities.CategoryEntity;
import tomaszewski.out.repositories.JpaCategoryRepository;
import tomaszewski.port.out.CategoryRepositoryPort;

@RequiredArgsConstructor
@Service
public class CategoryPersistenceAdapter implements CategoryRepositoryPort {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryModel getOrCreateByName(String name) {
        return jpaCategoryRepository.findByName(name)
                .map(categoryMapper::toModel)
                .orElseGet(() -> {
                    CategoryEntity newEntity = new CategoryEntity();
                    newEntity.setName(name);
                    CategoryEntity saved = jpaCategoryRepository.save(newEntity);
                    return categoryMapper.toModel(saved);
                });
    }
}
