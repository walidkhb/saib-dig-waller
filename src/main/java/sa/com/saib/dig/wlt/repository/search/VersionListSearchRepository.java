package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.VersionList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link VersionList} entity.
 */
public interface VersionListSearchRepository extends ElasticsearchRepository<VersionList, Long> {
}
