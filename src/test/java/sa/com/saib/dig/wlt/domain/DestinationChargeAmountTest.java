package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class DestinationChargeAmountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DestinationChargeAmount.class);
        DestinationChargeAmount destinationChargeAmount1 = new DestinationChargeAmount();
        destinationChargeAmount1.setId(1L);
        DestinationChargeAmount destinationChargeAmount2 = new DestinationChargeAmount();
        destinationChargeAmount2.setId(destinationChargeAmount1.getId());
        assertThat(destinationChargeAmount1).isEqualTo(destinationChargeAmount2);
        destinationChargeAmount2.setId(2L);
        assertThat(destinationChargeAmount1).isNotEqualTo(destinationChargeAmount2);
        destinationChargeAmount1.setId(null);
        assertThat(destinationChargeAmount1).isNotEqualTo(destinationChargeAmount2);
    }
}
