package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Transfer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Transfer} entity.
 */
public interface TransferSearchRepository extends ElasticsearchRepository<Transfer, Long> {
}
