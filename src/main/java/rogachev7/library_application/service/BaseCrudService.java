package rogachev7.library_application.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.AbstractEntity;

import java.util.List;

public abstract class BaseCrudService <T extends AbstractEntity> {

    abstract JpaRepository<T, Long> getRepository();

    public List<T> getAll() {
        return getRepository().findAll();
    }

    public T getById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    public T create(T entity) {
        return getRepository().save(entity);
    }

    public List<T> createAll(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    public T edit(T entity) {
        return getRepository().save(entity);
    }

    public void deleteById(Long id) {
        getRepository().delete(getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No entity with id %s exists!", id))));
    }

    public boolean existsById(Long id) {
        return getRepository().existsById(id);
    }

}