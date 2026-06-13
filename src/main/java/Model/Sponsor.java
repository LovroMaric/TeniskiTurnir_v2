package Model;

public class Sponsor extends BaseEntity {

    private String name;

    public Sponsor(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}