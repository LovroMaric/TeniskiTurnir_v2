package Service;

import Model.Country;

import java.util.List;

public interface ICountryService {
    List<Country> findAll();
}