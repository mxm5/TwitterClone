package Base.Repository;

import Base.Domain.BaseEntity;
import org.yaml.snakeyaml.events.Event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface RepositoryApi<E extends BaseEntity<ID>, ID extends Serializable> {

    void save(E e);

    void delete(E e);

    Optional<E> getById(ID id);

    Collection<E> getAll();

    Collection<E> getChunkOfAll(int chunkSize, int chunkCount);

    void safeRemove(E e);

}
