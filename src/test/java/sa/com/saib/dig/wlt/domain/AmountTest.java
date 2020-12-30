package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class AmountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amount.class);
        Amount amount1 = new Amount();
        amount1.setId(1L);
        Amount amount2 = new Amount();
        amount2.setId(amount1.getId());
        assertThat(amount1).isEqualTo(amount2);
        amount2.setId(2L);
        assertThat(amount1).isNotEqualTo(amount2);
        amount1.setId(null);
        assertThat(amount1).isNotEqualTo(amount2);
    }
}
