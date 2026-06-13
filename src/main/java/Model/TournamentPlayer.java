package Model;

public class TournamentPlayer extends BaseEntity {

    private Tournament tournament;
    private Player player;

    private String result;

    public TournamentPlayer(Long id, Tournament tournament, Player player, String result) {
        super(id);
        this.tournament = tournament;
        this.player = player;
        this.result = result;
    }

    public Tournament getTournament() { return tournament; }
    public void setTournament(Tournament tournament) { this.tournament = tournament; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    @Override
    public String toString() {
        return player.getFullName() + " - " + result;
    }
}