package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import rogachev7.library_application.model.entity.AbstractEntity;
import rogachev7.library_application.model.entity.Book;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {
}
