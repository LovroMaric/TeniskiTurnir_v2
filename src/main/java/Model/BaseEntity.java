package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseEntity {

    protected Long id;

    protected BaseEntity() {}

    protected BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}