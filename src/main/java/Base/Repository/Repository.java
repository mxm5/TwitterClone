package Base.Repository;

import Base.Domain.BaseEntity;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public abstract class Repository
        <E extends BaseEntity<ID>, ID extends Serializable>
        implements RepositoryApi<E, ID> {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public abstract Class<E> getType();

    public Repository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("twitter_clone");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public EntityTransaction getTransaction() {
        if(entityManager.isOpen())
        return entityManager.getTransaction();
        else throw new IllegalStateException();
    }

    public EntityManager getEntityManager() {
        if(entityManager.isOpen())
        return entityManager;
        else throw new IllegalStateException();
    }

    @Override
    public void save(E e) throws Exception {
        if(!entityManager.isOpen())
            throw new IllegalStateException();
        if (e.getId() == null) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(e);
                transaction.commit();
            } catch (Exception exception) {
                if (transaction.isActive()) transaction.rollback();
                exception.printStackTrace();

            }
        }
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(e);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
        }
    }

    @Override
    public void delete(E e) {
        entityManager.getTransaction().begin();
        entityManager.remove(e);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<E> getById(ID id) {
        return Optional.ofNullable(entityManager.find(getType(), id));
    }

    @Override
    public Collection<E> getAll() {

        return entityManager.
                createQuery("from " + getType().getSimpleName(), getType()).getResultList();
    }

    @Override
    public Collection<E> getChunkOfAll(int chunkSize, int chunkCount) {
        return entityManager.
                createQuery("from " + getType().getSimpleName(), getType()).setMaxResults(chunkSize).setFirstResult(chunkCount * chunkSize).getResultList();

    }


}
