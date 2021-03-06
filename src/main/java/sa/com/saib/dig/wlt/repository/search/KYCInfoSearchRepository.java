package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.KYCInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link KYCInfo} entity.
 */
public interface KYCInfoSearchRepository extends ElasticsearchRepository<KYCInfo, Long> {
}
