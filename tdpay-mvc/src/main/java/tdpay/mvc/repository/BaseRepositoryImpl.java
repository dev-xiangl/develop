package tdpay.mvc.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * BaseRepositoryImpl
 */
@Transactional
public class BaseRepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

    public Long getMaxId() {
        Query query = entityManager.createQuery("select max(tbl.id) from " + this.getDomainClass().getName() + " tbl");
        Object obj = query.getSingleResult();
        if (obj == null) {
            return Long.valueOf(0);
        }

        return (Long)obj;
    }
}
