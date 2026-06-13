package Service;

import Model.Country;
import Repository.CrudRepository;

import java.util.List;

public class CountryService implements ICountryService {

    private final CrudRepository<Country> repo;

    public CountryService(CrudRepository<Country> repo) {
        this.repo = repo;
    }

    @Override
    public List<Country> findAll() {
        return repo.findAll();
    }
}