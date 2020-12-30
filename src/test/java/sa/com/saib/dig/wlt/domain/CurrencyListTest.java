package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CurrencyListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyList.class);
        CurrencyList currencyList1 = new CurrencyList();
        currencyList1.setId(1L);
        CurrencyList currencyList2 = new CurrencyList();
        currencyList2.setId(currencyList1.getId());
        assertThat(currencyList1).isEqualTo(currencyList2);
        currencyList2.setId(2L);
        assertThat(currencyList1).isNotEqualTo(currencyList2);
        currencyList1.setId(null);
        assertThat(currencyList1).isNotEqualTo(currencyList2);
    }
}
