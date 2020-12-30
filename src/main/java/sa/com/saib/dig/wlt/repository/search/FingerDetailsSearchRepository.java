package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.FingerDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link FingerDetails} entity.
 */
public interface FingerDetailsSearchRepository extends ElasticsearchRepository<FingerDetails, Long> {
}
