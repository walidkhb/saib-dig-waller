package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Debtor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Debtor} entity.
 */
public interface DebtorSearchRepository extends ElasticsearchRepository<Debtor, Long> {
}
