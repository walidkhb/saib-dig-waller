package sa.com.saib.dig.wlt.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DistrictListSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DistrictListSearchRepositoryMockConfiguration {

    @MockBean
    private DistrictListSearchRepository mockDistrictListSearchRepository;

}
