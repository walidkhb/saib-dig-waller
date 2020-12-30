package sa.com.saib.dig.wlt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.dig.wlt.web.rest.TestUtil;

public class AccountSchemeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountScheme.class);
        AccountScheme accountScheme1 = new AccountScheme();
        accountScheme1.setId(1L);
        AccountScheme accountScheme2 = new AccountScheme();
        accountScheme2.setId(accountScheme1.getId());
        assertThat(accountScheme1).isEqualTo(accountScheme2);
        accountScheme2.setId(2L);
        assertThat(accountScheme1).isNotEqualTo(accountScheme2);
        accountScheme1.setId(null);
        assertThat(accountScheme1).isNotEqualTo(accountScheme2);
    }
}
