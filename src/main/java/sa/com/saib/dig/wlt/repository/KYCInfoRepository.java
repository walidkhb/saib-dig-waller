package sa.com.saib.dig.wlt.repository;

import sa.com.saib.dig.wlt.domain.KYCInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KYCInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KYCInfoRepository extends JpaRepository<KYCInfo, Long> {
}
