package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class TransactionInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionInfo.class);
        TransactionInfo transactionInfo1 = new TransactionInfo();
        transactionInfo1.setId(1L);
        TransactionInfo transactionInfo2 = new TransactionInfo();
        transactionInfo2.setId(transactionInfo1.getId());
        assertThat(transactionInfo1).isEqualTo(transactionInfo2);
        transactionInfo2.setId(2L);
        assertThat(transactionInfo1).isNotEqualTo(transactionInfo2);
        transactionInfo1.setId(null);
        assertThat(transactionInfo1).isNotEqualTo(transactionInfo2);
    }
}
