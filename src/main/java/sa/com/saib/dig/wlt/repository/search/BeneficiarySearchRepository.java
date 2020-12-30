package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Beneficiary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Beneficiary} entity.
 */
public interface BeneficiarySearchRepository extends ElasticsearchRepository<Beneficiary, Long> {
}
