package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class BankListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankList.class);
        BankList bankList1 = new BankList();
        bankList1.setId(1L);
        BankList bankList2 = new BankList();
        bankList2.setId(bankList1.getId());
        assertThat(bankList1).isEqualTo(bankList2);
        bankList2.setId(2L);
        assertThat(bankList1).isNotEqualTo(bankList2);
        bankList1.setId(null);
        assertThat(bankList1).isNotEqualTo(bankList2);
    }
}
