package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Amount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Amount} entity.
 */
public interface AmountSearchRepository extends ElasticsearchRepository<Amount, Long> {
}
