package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.BranchList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BranchList} entity.
 */
public interface BranchListSearchRepository extends ElasticsearchRepository<BranchList, Long> {
}
