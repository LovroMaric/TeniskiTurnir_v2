package Service;

import Model.Category;
import Repository.ReadRepository;

import java.util.List;

public class CategoryService implements ICategoryService {

    private final ReadRepository<Category> repository;

    public CategoryService(ReadRepository<Category> repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
}