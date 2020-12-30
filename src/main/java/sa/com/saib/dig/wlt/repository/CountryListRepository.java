package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CountryList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountryList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryListRepository extends JpaRepository<CountryList, Long> {
}
