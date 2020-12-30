package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.DistrictList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link DistrictList} entity.
 */
public interface DistrictListSearchRepository extends ElasticsearchRepository<DistrictList, Long> {
}
