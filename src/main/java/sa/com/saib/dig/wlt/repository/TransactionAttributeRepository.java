package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.TransactionAttribute;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionAttributeRepository extends JpaRepository<TransactionAttribute, Long> {
}
