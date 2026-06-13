package Service;

import Model.Country;
import Model.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IPlayerService {

    List<Player> findAll();

    void save(Player player);

    void update(Player player);

    void delete(Long id);

    List<Player> filter(Predicate<Player> predicate);

    <R> List<R> map(Function<Player, R> mapper);

    void forEach(Consumer<Player> action);

    Optional<Player> findTopRanked();

    Optional<Player> findLowestRanked();

    List<Player> findAllExceptTopN(int n);

    List<String> getDistinctCountryNames();

    Set<Country> getDistinctCountries();

    Map<Country, List<Player>> groupByCountry();

    boolean hasPlayerRankedBelow(int ranking);

    boolean noPlayersFromCountry(Country country);

    Optional<Player> findFirstByLastName(String lastName);

    default List<Player> findSortedByRanking() {
        return findAll().stream()
                .sorted(Comparator.comparingInt(Player::getRanking))
                .collect(Collectors.toList());
    }
}