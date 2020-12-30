package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.BranchList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BranchList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchListRepository extends JpaRepository<BranchList, Long> {
}
