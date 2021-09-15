package Base.Service;

import Base.Domain.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface ServiceApi<E extends BaseEntity<ID>, ID extends Serializable> {
    void save(E e) throws Exception;

    void delete(E e);

    Optional<E> getById(ID id);

    Collection<E> getAll();

    Collection<E> getChunkOfAll(int chunkSize, int chunkCount);

    void safeRemove(E e);
}
