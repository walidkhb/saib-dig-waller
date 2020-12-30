package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.DistrictList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DistrictList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictListRepository extends JpaRepository<DistrictList, Long> {
}
