package sa.com.saib.dig.wlt.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sa.com.saib.dig.wlt.domain.User;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {}
