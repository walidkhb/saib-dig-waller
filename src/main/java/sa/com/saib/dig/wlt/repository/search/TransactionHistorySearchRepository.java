package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.TransactionHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TransactionHistory} entity.
 */
public interface TransactionHistorySearchRepository extends ElasticsearchRepository<TransactionHistory, Long> {
}
