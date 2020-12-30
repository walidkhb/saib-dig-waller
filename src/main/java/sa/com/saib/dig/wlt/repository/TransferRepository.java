package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.Transfer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
