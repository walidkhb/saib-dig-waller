package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.DestinationChargeAmount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link DestinationChargeAmount} entity.
 */
public interface DestinationChargeAmountSearchRepository extends ElasticsearchRepository<DestinationChargeAmount, Long> {
}
