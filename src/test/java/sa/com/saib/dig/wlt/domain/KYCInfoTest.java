package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class KYCInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KYCInfo.class);
        KYCInfo kYCInfo1 = new KYCInfo();
        kYCInfo1.setId(1L);
        KYCInfo kYCInfo2 = new KYCInfo();
        kYCInfo2.setId(kYCInfo1.getId());
        assertThat(kYCInfo1).isEqualTo(kYCInfo2);
        kYCInfo2.setId(2L);
        assertThat(kYCInfo1).isNotEqualTo(kYCInfo2);
        kYCInfo1.setId(null);
        assertThat(kYCInfo1).isNotEqualTo(kYCInfo2);
    }
}
