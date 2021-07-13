package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.AbstractEntity;
import rogachev7.library_application.repository.CommonRepository;

import java.util.List;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<E> getAll(Specification<E> specification) {
        return repository.findAll(specification);
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public E getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No entity with id %s exists!", id)));
    }

    @Override
    public E create(E entity) {
        return repository.save(entity);
    }

    @Override
    public List<E> createAll(List<E> listEntity) {
        return repository.saveAll(listEntity);
    }

    @Override
    public E edit(E entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No entity with id %s exists!", id))));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}