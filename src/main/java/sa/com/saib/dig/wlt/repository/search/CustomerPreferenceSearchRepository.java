package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CustomerPreference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CustomerPreference} entity.
 */
public interface CustomerPreferenceSearchRepository extends ElasticsearchRepository<CustomerPreference, Long> {
}
