package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CurrencyList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CurrencyList} entity.
 */
public interface CurrencyListSearchRepository extends ElasticsearchRepository<CurrencyList, Long> {
}
