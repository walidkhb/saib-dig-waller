package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class BranchListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchList.class);
        BranchList branchList1 = new BranchList();
        branchList1.setId(1L);
        BranchList branchList2 = new BranchList();
        branchList2.setId(branchList1.getId());
        assertThat(branchList1).isEqualTo(branchList2);
        branchList2.setId(2L);
        assertThat(branchList1).isNotEqualTo(branchList2);
        branchList1.setId(null);
        assertThat(branchList1).isNotEqualTo(branchList2);
    }
}
