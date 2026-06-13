package Service;

import Model.Tournament;
import Repository.ITournamentRepository;

import java.util.List;

public class TournamentService implements ITournamentService {

    private final ITournamentRepository repository;

    public TournamentService(ITournamentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Tournament tournament) {
        repository.save(tournament);
    }

    @Override
    public List<Tournament> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(Tournament tournament) {
        repository.update(tournament);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public void addPlayerToTournament(Long tournamentId, Long playerId, String result) {
        repository.addPlayerToTournament(tournamentId, playerId, result);
    }
}