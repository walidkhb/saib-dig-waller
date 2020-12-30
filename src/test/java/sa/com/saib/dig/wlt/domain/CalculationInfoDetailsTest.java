package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CalculationInfoDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalculationInfoDetails.class);
        CalculationInfoDetails calculationInfoDetails1 = new CalculationInfoDetails();
        calculationInfoDetails1.setId(1L);
        CalculationInfoDetails calculationInfoDetails2 = new CalculationInfoDetails();
        calculationInfoDetails2.setId(calculationInfoDetails1.getId());
        assertThat(calculationInfoDetails1).isEqualTo(calculationInfoDetails2);
        calculationInfoDetails2.setId(2L);
        assertThat(calculationInfoDetails1).isNotEqualTo(calculationInfoDetails2);
        calculationInfoDetails1.setId(null);
        assertThat(calculationInfoDetails1).isNotEqualTo(calculationInfoDetails2);
    }
}
