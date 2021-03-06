package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.Amount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Amount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {
}
