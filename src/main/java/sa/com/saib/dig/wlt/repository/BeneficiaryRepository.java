package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.Beneficiary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Beneficiary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
}
