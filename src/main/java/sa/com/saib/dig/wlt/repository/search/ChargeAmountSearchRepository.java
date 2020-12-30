package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.ChargeAmount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ChargeAmount} entity.
 */
public interface ChargeAmountSearchRepository extends ElasticsearchRepository<ChargeAmount, Long> {
}
