package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.BankList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BankList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankListRepository extends JpaRepository<BankList, Long> {
}
