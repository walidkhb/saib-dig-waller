package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CustomerPreferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPreference.class);
        CustomerPreference customerPreference1 = new CustomerPreference();
        customerPreference1.setId(1L);
        CustomerPreference customerPreference2 = new CustomerPreference();
        customerPreference2.setId(customerPreference1.getId());
        assertThat(customerPreference1).isEqualTo(customerPreference2);
        customerPreference2.setId(2L);
        assertThat(customerPreference1).isNotEqualTo(customerPreference2);
        customerPreference1.setId(null);
        assertThat(customerPreference1).isNotEqualTo(customerPreference2);
    }
}
