package sa.com.saib.dig.wlt.repository.search;

import sa.com.saib.dig.wlt.domain.Wallet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Wallet} entity.
 */
public interface WalletSearchRepository extends ElasticsearchRepository<Wallet, Long> {
}
