package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.TransactionInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TransactionInfo} entity.
 */
public interface TransactionInfoSearchRepository extends ElasticsearchRepository<TransactionInfo, Long> {
}
