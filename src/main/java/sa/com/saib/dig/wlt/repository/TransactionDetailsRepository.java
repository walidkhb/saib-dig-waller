package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.TransactionDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
}
