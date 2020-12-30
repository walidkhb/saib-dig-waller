package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CalculationInfoDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CalculationInfoDetails} entity.
 */
public interface CalculationInfoDetailsSearchRepository extends ElasticsearchRepository<CalculationInfoDetails, Long> {
}
