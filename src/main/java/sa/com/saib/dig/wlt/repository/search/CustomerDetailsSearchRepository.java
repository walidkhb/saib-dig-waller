package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CustomerDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CustomerDetails} entity.
 */
public interface CustomerDetailsSearchRepository extends ElasticsearchRepository<CustomerDetails, Long> {
}
