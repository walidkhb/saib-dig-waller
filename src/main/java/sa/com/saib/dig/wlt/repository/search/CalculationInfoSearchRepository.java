package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CalculationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CalculationInfo} entity.
 */
public interface CalculationInfoSearchRepository extends ElasticsearchRepository<CalculationInfo, Long> {
}
