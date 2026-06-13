package Repository;

import Model.Tournament;

public interface ITournamentRepository extends CrudRepository<Tournament> {

    void addPlayerToTournament(Long tournamentId, Long playerId, String result);
}