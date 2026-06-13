package Model;

public class Category extends BaseEntity {

    private String name;

    public Category(Long id, String name) {
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