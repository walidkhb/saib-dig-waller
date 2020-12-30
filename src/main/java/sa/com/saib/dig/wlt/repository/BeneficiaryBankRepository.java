package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.BeneficiaryBank;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BeneficiaryBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaryBankRepository extends JpaRepository<BeneficiaryBank, Long> {
}
