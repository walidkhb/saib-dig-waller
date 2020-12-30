package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.AccountScheme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AccountScheme} entity.
 */
public interface AccountSchemeSearchRepository extends ElasticsearchRepository<AccountScheme, Long> {
}
