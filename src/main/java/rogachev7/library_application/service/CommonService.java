package rogachev7.library_application.service;

import rogachev7.library_application.model.entity.AbstractEntity;

import java.util.List;

public interface CommonService<E extends AbstractEntity> {

    List<E> getAll();

    E getById(Long id);

    E create(E entity);

    List<E> createAll(List<E> listEntity);

    E edit(E entity);

    void deleteById(Long id);

    boolean existsById(Long id);
}