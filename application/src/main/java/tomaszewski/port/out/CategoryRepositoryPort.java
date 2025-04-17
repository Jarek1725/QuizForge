package tomaszewski.port.out;

import tomaszewski.model.CategoryModel;

public interface CategoryRepositoryPort {
    CategoryModel getOrCreateByName(String name);
}
