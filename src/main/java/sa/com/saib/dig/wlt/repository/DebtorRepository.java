package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.Debtor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Debtor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Long> {
}
