package mini_projects.authServer.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import java.io.Serializable;
import java.util.List;

public class FilteredRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public FilteredRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll() {
        enableFilter();
        return super.findAll();
    }

    private void enableFilter() {
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter("activeUserFilter") == null) {
            session.enableFilter("activeUserFilter");
        }
    }
}
