package Model;

import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "tournament")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tournament extends BaseEntity implements Comparable<Tournament> {

    private String name;
    private int foundedYear;
    private BigDecimal prizeMoney;
    private SurfaceType surface;
    private String imagePath;

    private Country hostCountry;
    private Category category;

    @XmlElementWrapper(name = "players")
    @XmlElement(name = "tournamentPlayer")
    private List<TournamentPlayer> tournamentPlayers = new ArrayList<>();

    @XmlElementWrapper(name = "sponsors")
    @XmlElement(name = "sponsor")
    private List<Sponsor> sponsors = new ArrayList<>();

    public Tournament() {
        this.tournamentPlayers = new ArrayList<>();
        this.sponsors = new ArrayList<>();
    }

    public static Tournament createEmpty() {
        return new Tournament(
                null, "", 0, BigDecimal.ZERO,
                SurfaceType.HARD,
                null, null, null,
                new ArrayList<>(), new ArrayList<>()
        );
    }

    public Tournament(Long id, String name, int foundedYear, BigDecimal prizeMoney,
                      SurfaceType surface, String imagePath,
                      Country hostCountry, Category category,
                      List<TournamentPlayer> tournamentPlayers,
                      List<Sponsor> sponsors) {

        super(id);
        this.name = name;
        this.foundedYear = foundedYear;
        this.prizeMoney = prizeMoney;
        this.surface = surface;
        this.imagePath = imagePath;
        this.hostCountry = hostCountry;
        this.category = category;

        this.tournamentPlayers =
                (tournamentPlayers != null) ? tournamentPlayers : new ArrayList<>();

        this.sponsors =
                (sponsors != null) ? sponsors : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getFoundedYear() { return foundedYear; }
    public void setFoundedYear(int foundedYear) { this.foundedYear = foundedYear; }

    public BigDecimal getPrizeMoney() { return prizeMoney; }
    public void setPrizeMoney(BigDecimal prizeMoney) { this.prizeMoney = prizeMoney; }

    public SurfaceType getSurface() { return surface; }
    public void setSurface(SurfaceType surface) { this.surface = surface; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Country getCountry() { return hostCountry; }
    public void setCountry(Country hostCountry) { this.hostCountry = hostCountry; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<TournamentPlayer> getPlayers() { return tournamentPlayers; }
    public void setPlayers(List<TournamentPlayer> players) {
        this.tournamentPlayers = players;
    }

    public List<Sponsor> getSponsors() { return sponsors; }
    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public void addPlayer(Player player, String result) {
        TournamentPlayer tp =
                new TournamentPlayer(null, this, player, result);

        tournamentPlayers.add(tp);
    }

    public void removePlayer(Player player) {
        tournamentPlayers.removeIf(tp ->
                tp.getPlayer().equals(player));
    }

    public void addSponsor(Sponsor sponsor) {
        sponsors.add(sponsor);
    }

    public void removeSponsor(Sponsor sponsor) {
        sponsors.remove(sponsor);
    }

    @Override
    public int compareTo(Tournament o) {
        if (this.id == null || o.id == null) return 0;
        return this.id.compareTo(o.id);
    }
}