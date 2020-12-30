package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class CountryListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryList.class);
        CountryList countryList1 = new CountryList();
        countryList1.setId(1L);
        CountryList countryList2 = new CountryList();
        countryList2.setId(countryList1.getId());
        assertThat(countryList1).isEqualTo(countryList2);
        countryList2.setId(2L);
        assertThat(countryList1).isNotEqualTo(countryList2);
        countryList1.setId(null);
        assertThat(countryList1).isNotEqualTo(countryList2);
    }
}
