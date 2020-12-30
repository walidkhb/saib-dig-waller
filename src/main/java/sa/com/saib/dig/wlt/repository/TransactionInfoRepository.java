package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.TransactionInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {
}
