package Model;

public class Player extends BaseEntity {

    private String firstName;
    private String lastName;
    private int ranking;
    private String imagePath;
    private Country country;

    public Player() {}

    public Player(Long id, String firstName, String lastName, int ranking, String imagePath) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.ranking = ranking;
        this.imagePath = imagePath;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getRanking() { return ranking; }
    public String getImagePath() { return imagePath; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setRanking(int ranking) { this.ranking = ranking; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}