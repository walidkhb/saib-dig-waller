package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Address} entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {
}
