package Base.Service;

import Base.Domain.BaseEntity;
import Base.Repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class Service<E extends BaseEntity<ID>, ID extends Serializable, R extends Repository<E, ID>> implements ServiceApi<E, ID> {

    public R repository;

    public Service(R repository) {
        this.repository = repository;
    }

    @Override
    public void save(E e)  {
        try {
            repository.save(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(E e) {
        repository.delete(e);
    }

    @Override
    public Optional<E> getById(ID id) {
        return repository.getById(id);
    }

    @Override
    public Collection<E> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<E> getChunkOfAll(int chunkSize, int chunkCount) {
        return repository.getChunkOfAll(chunkSize, chunkCount);
    }

    @Override
    public void safeRemove(E e) {

    }
}
