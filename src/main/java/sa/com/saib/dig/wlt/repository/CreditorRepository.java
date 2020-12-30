package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.Creditor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Creditor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditorRepository extends JpaRepository<Creditor, Long> {
}
