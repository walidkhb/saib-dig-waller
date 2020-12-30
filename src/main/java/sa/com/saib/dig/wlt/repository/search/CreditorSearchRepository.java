package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Creditor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Creditor} entity.
 */
public interface CreditorSearchRepository extends ElasticsearchRepository<Creditor, Long> {
}
