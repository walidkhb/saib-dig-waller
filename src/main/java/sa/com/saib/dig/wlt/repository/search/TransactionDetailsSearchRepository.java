package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.TransactionDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TransactionDetails} entity.
 */
public interface TransactionDetailsSearchRepository extends ElasticsearchRepository<TransactionDetails, Long> {
}
