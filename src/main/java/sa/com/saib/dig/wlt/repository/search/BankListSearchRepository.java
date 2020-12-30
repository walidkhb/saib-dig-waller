package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.BankList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BankList} entity.
 */
public interface BankListSearchRepository extends ElasticsearchRepository<BankList, Long> {
}
