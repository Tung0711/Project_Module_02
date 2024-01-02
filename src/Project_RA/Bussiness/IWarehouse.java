package Project_RA.Bussiness;

import java.util.List;

public interface IWarehouse<T, K, S, I> {
    List<T> getAll(I i);

    boolean create(T t);

    boolean update(T t);

    T findById(K k);

    T findByName(S s);

    List<T> search(S s, I i);
}
