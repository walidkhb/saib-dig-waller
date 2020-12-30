package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.BeneficiaryBank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BeneficiaryBank} entity.
 */
public interface BeneficiaryBankSearchRepository extends ElasticsearchRepository<BeneficiaryBank, Long> {
}
