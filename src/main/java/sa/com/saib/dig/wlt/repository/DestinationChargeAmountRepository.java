package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.DestinationChargeAmount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DestinationChargeAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DestinationChargeAmountRepository extends JpaRepository<DestinationChargeAmount, Long> {
}
