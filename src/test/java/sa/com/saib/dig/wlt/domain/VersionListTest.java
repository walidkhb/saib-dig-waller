package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class VersionListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionList.class);
        VersionList versionList1 = new VersionList();
        versionList1.setId(1L);
        VersionList versionList2 = new VersionList();
        versionList2.setId(versionList1.getId());
        assertThat(versionList1).isEqualTo(versionList2);
        versionList2.setId(2L);
        assertThat(versionList1).isNotEqualTo(versionList2);
        versionList1.setId(null);
        assertThat(versionList1).isNotEqualTo(versionList2);
    }
}
