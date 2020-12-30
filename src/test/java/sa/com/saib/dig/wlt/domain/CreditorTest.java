package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CreditorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Creditor.class);
        Creditor creditor1 = new Creditor();
        creditor1.setId(1L);
        Creditor creditor2 = new Creditor();
        creditor2.setId(creditor1.getId());
        assertThat(creditor1).isEqualTo(creditor2);
        creditor2.setId(2L);
        assertThat(creditor1).isNotEqualTo(creditor2);
        creditor1.setId(null);
        assertThat(creditor1).isNotEqualTo(creditor2);
    }
}
