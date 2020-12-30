package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class TransactionHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistory.class);
        TransactionHistory transactionHistory1 = new TransactionHistory();
        transactionHistory1.setId(1L);
        TransactionHistory transactionHistory2 = new TransactionHistory();
        transactionHistory2.setId(transactionHistory1.getId());
        assertThat(transactionHistory1).isEqualTo(transactionHistory2);
        transactionHistory2.setId(2L);
        assertThat(transactionHistory1).isNotEqualTo(transactionHistory2);
        transactionHistory1.setId(null);
        assertThat(transactionHistory1).isNotEqualTo(transactionHistory2);
    }
}
