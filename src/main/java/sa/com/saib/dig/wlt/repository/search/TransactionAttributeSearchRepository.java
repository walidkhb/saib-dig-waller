package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.TransactionAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TransactionAttribute} entity.
 */
public interface TransactionAttributeSearchRepository extends ElasticsearchRepository<TransactionAttribute, Long> {
}
