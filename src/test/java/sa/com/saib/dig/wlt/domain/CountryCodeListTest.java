package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CountryCodeListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryCodeList.class);
        CountryCodeList countryCodeList1 = new CountryCodeList();
        countryCodeList1.setId(1L);
        CountryCodeList countryCodeList2 = new CountryCodeList();
        countryCodeList2.setId(countryCodeList1.getId());
        assertThat(countryCodeList1).isEqualTo(countryCodeList2);
        countryCodeList2.setId(2L);
        assertThat(countryCodeList1).isNotEqualTo(countryCodeList2);
        countryCodeList1.setId(null);
        assertThat(countryCodeList1).isNotEqualTo(countryCodeList2);
    }
}
