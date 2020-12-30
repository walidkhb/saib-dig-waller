package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.VersionList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VersionList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionListRepository extends JpaRepository<VersionList, Long> {
}
