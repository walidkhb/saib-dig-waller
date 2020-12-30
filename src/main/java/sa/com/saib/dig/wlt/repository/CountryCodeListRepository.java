package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.CountryCodeList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountryCodeList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryCodeListRepository extends JpaRepository<CountryCodeList, Long> {
}
