package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.PaymentDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PaymentDetails} entity.
 */
public interface PaymentDetailsSearchRepository extends ElasticsearchRepository<PaymentDetails, Long> {
}
