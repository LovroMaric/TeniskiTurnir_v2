package Repository;

public interface CrudRepository<T> extends ReadRepository<T> {

    void save(T entity);
    void update(T entity);
    void delete(Long id);
}