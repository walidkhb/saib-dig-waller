package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class DistrictListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictList.class);
        DistrictList districtList1 = new DistrictList();
        districtList1.setId(1L);
        DistrictList districtList2 = new DistrictList();
        districtList2.setId(districtList1.getId());
        assertThat(districtList1).isEqualTo(districtList2);
        districtList2.setId(2L);
        assertThat(districtList1).isNotEqualTo(districtList2);
        districtList1.setId(null);
        assertThat(districtList1).isNotEqualTo(districtList2);
    }
}
