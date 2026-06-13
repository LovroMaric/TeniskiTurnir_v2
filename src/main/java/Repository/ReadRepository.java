package Repository;

import java.util.List;

public interface ReadRepository<T> {
    List<T> findAll();
}