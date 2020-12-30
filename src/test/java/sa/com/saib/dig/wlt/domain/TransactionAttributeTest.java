package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class TransactionAttributeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAttribute.class);
        TransactionAttribute transactionAttribute1 = new TransactionAttribute();
        transactionAttribute1.setId(1L);
        TransactionAttribute transactionAttribute2 = new TransactionAttribute();
        transactionAttribute2.setId(transactionAttribute1.getId());
        assertThat(transactionAttribute1).isEqualTo(transactionAttribute2);
        transactionAttribute2.setId(2L);
        assertThat(transactionAttribute1).isNotEqualTo(transactionAttribute2);
        transactionAttribute1.setId(null);
        assertThat(transactionAttribute1).isNotEqualTo(transactionAttribute2);
    }
}
