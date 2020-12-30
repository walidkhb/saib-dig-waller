package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CalculationInfoDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalculationInfoDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalculationInfoDetailsRepository extends JpaRepository<CalculationInfoDetails, Long> {
}
