package com.yam.ecompany.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yam.ecompany.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetail.class);
        BankDetail bankDetail1 = new BankDetail();
        bankDetail1.setId(1L);
        BankDetail bankDetail2 = new BankDetail();
        bankDetail2.setId(bankDetail1.getId());
        assertThat(bankDetail1).isEqualTo(bankDetail2);
        bankDetail2.setId(2L);
        assertThat(bankDetail1).isNotEqualTo(bankDetail2);
        bankDetail1.setId(null);
        assertThat(bankDetail1).isNotEqualTo(bankDetail2);
    }
}
