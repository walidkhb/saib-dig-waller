package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class BeneficiaryBankTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryBank.class);
        BeneficiaryBank beneficiaryBank1 = new BeneficiaryBank();
        beneficiaryBank1.setId(1L);
        BeneficiaryBank beneficiaryBank2 = new BeneficiaryBank();
        beneficiaryBank2.setId(beneficiaryBank1.getId());
        assertThat(beneficiaryBank1).isEqualTo(beneficiaryBank2);
        beneficiaryBank2.setId(2L);
        assertThat(beneficiaryBank1).isNotEqualTo(beneficiaryBank2);
        beneficiaryBank1.setId(null);
        assertThat(beneficiaryBank1).isNotEqualTo(beneficiaryBank2);
    }
}
