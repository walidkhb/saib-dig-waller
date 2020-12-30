package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.FingerDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FingerDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FingerDetailsRepository extends JpaRepository<FingerDetails, Long> {
}
