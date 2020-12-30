package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CountryList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CountryList} entity.
 */
public interface CountryListSearchRepository extends ElasticsearchRepository<CountryList, Long> {
}
