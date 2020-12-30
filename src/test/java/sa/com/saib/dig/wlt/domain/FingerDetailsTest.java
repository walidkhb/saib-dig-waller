package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class FingerDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FingerDetails.class);
        FingerDetails fingerDetails1 = new FingerDetails();
        fingerDetails1.setId(1L);
        FingerDetails fingerDetails2 = new FingerDetails();
        fingerDetails2.setId(fingerDetails1.getId());
        assertThat(fingerDetails1).isEqualTo(fingerDetails2);
        fingerDetails2.setId(2L);
        assertThat(fingerDetails1).isNotEqualTo(fingerDetails2);
        fingerDetails1.setId(null);
        assertThat(fingerDetails1).isNotEqualTo(fingerDetails2);
    }
}
