package Model;

public class Country extends BaseEntity {

    private String name;

    public Country() {}

    public Country(Long id, String name) {
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