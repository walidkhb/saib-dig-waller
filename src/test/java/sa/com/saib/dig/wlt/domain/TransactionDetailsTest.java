package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class TransactionDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDetails.class);
        TransactionDetails transactionDetails1 = new TransactionDetails();
        transactionDetails1.setId(1L);
        TransactionDetails transactionDetails2 = new TransactionDetails();
        transactionDetails2.setId(transactionDetails1.getId());
        assertThat(transactionDetails1).isEqualTo(transactionDetails2);
        transactionDetails2.setId(2L);
        assertThat(transactionDetails1).isNotEqualTo(transactionDetails2);
        transactionDetails1.setId(null);
        assertThat(transactionDetails1).isNotEqualTo(transactionDetails2);
    }
}
