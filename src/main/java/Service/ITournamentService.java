package Service;

import Model.Tournament;

import java.util.List;

public interface ITournamentService {

    void add(Tournament tournament);

    List<Tournament> findAll();

    void update(Tournament tournament);

    void delete(Long id);

    void addPlayerToTournament(Long tournamentId, Long playerId, String result);
}