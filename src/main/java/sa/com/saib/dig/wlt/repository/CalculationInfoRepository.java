package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CalculationInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalculationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalculationInfoRepository extends JpaRepository<CalculationInfo, Long> {
}
