package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.EntityMetadata;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.AbstractEntity;
import rogachev7.library_application.repository.CommonRepository;

import java.util.List;

public abstract class BaseCrudService <E extends AbstractEntity, R extends CommonRepository<E>> {

    protected final R repository;

    @Autowired
    public BaseCrudService(R repository) {
        this.repository = repository;
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public E getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    public E create(E entity) {
        return repository.save(entity);
    }

    public E edit(E entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No entity with id %s exists!", id))));
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
