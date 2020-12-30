package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CurrencyList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurrencyList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyListRepository extends JpaRepository<CurrencyList, Long> {
}
