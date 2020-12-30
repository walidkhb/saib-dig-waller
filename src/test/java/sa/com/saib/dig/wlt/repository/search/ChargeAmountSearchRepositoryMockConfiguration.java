package sa.com.saib.dig.wlt.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ChargeAmountSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ChargeAmountSearchRepositoryMockConfiguration {

    @MockBean
    private ChargeAmountSearchRepository mockChargeAmountSearchRepository;

}
