package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.CountryCodeList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CountryCodeList} entity.
 */
public interface CountryCodeListSearchRepository extends ElasticsearchRepository<CountryCodeList, Long> {
}
