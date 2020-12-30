package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class DebtorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debtor.class);
        Debtor debtor1 = new Debtor();
        debtor1.setId(1L);
        Debtor debtor2 = new Debtor();
        debtor2.setId(debtor1.getId());
        assertThat(debtor1).isEqualTo(debtor2);
        debtor2.setId(2L);
        assertThat(debtor1).isNotEqualTo(debtor2);
        debtor1.setId(null);
        assertThat(debtor1).isNotEqualTo(debtor2);
    }
}
