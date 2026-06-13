package Service;

import Model.Country;
import Model.Player;
import Repository.CrudRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerService implements IPlayerService {

    private final CrudRepository<Player> repo;

    public PlayerService(CrudRepository<Player> repo) {
        this.repo = repo;
    }

    @Override
    public List<Player> findAll() {
        return repo.findAll();
    }


    @Override
    public void save(Player p) {
        repo.save(p);
    }

    @Override
    public void update(Player p) {
        repo.update(p);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }

    @Override
    public List<Player> filter(Predicate<Player> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public <R> List<R> map(Function<Player, R> mapper) {
        return findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public void forEach(Consumer<Player> action) {
        findAll().forEach(action);
    }

    @Override
    public Optional<Player> findTopRanked() {
        return findAll().stream().min(Comparator.comparingInt(Player::getRanking));
    }

    @Override
    public Optional<Player> findLowestRanked() {
        return findAll().stream().max(Comparator.comparingInt(Player::getRanking));
    }

    @Override
    public List<Player> findAllExceptTopN(int n) {
        return findSortedByRanking().stream().skip(n).collect(Collectors.toList());
    }

    @Override
    public List<String> getDistinctCountryNames() {
        return findAll().stream().map(Player::getCountry).filter(Objects::nonNull).map(Country::getName).distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public Set<Country> getDistinctCountries() {
        return findAll().stream().map(Player::getCountry).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Map<Country, List<Player>> groupByCountry() {
        return findAll().stream().filter(p -> p.getCountry() != null).collect(Collectors.groupingBy(Player::getCountry));
    }

    @Override
    public boolean hasPlayerRankedBelow(int ranking) {
        return findAll().stream().anyMatch(p -> p.getRanking() < ranking);
    }

    @Override
    public boolean noPlayersFromCountry(Country country) {
        return findAll().stream().noneMatch(p -> country.equals(p.getCountry()));
    }

    @Override
    public Optional<Player> findFirstByLastName(String lastName) {
        return findAll().stream().filter(p -> p.getLastName().equalsIgnoreCase(lastName)).findFirst();
    }
}