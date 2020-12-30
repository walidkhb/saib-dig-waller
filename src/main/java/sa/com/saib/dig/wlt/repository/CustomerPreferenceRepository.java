package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CustomerPreference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, Long> {
}
