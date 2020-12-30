package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class ChargeAmountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargeAmount.class);
        ChargeAmount chargeAmount1 = new ChargeAmount();
        chargeAmount1.setId(1L);
        ChargeAmount chargeAmount2 = new ChargeAmount();
        chargeAmount2.setId(chargeAmount1.getId());
        assertThat(chargeAmount1).isEqualTo(chargeAmount2);
        chargeAmount2.setId(2L);
        assertThat(chargeAmount1).isNotEqualTo(chargeAmount2);
        chargeAmount1.setId(null);
        assertThat(chargeAmount1).isNotEqualTo(chargeAmount2);
    }
}
