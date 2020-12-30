package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CalculationInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalculationInfo.class);
        CalculationInfo calculationInfo1 = new CalculationInfo();
        calculationInfo1.setId(1L);
        CalculationInfo calculationInfo2 = new CalculationInfo();
        calculationInfo2.setId(calculationInfo1.getId());
        assertThat(calculationInfo1).isEqualTo(calculationInfo2);
        calculationInfo2.setId(2L);
        assertThat(calculationInfo1).isNotEqualTo(calculationInfo2);
        calculationInfo1.setId(null);
        assertThat(calculationInfo1).isNotEqualTo(calculationInfo2);
    }
}
