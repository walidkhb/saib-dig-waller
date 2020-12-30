package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.ChargeAmount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChargeAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargeAmountRepository extends JpaRepository<ChargeAmount, Long> {
}
