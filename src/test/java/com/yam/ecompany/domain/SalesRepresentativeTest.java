package com.yam.ecompany.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yam.ecompany.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesRepresentativeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesRepresentative.class);
        SalesRepresentative salesRepresentative1 = new SalesRepresentative();
        salesRepresentative1.setId(1L);
        SalesRepresentative salesRepresentative2 = new SalesRepresentative();
        salesRepresentative2.setId(salesRepresentative1.getId());
        assertThat(salesRepresentative1).isEqualTo(salesRepresentative2);
        salesRepresentative2.setId(2L);
        assertThat(salesRepresentative1).isNotEqualTo(salesRepresentative2);
        salesRepresentative1.setId(null);
        assertThat(salesRepresentative1).isNotEqualTo(salesRepresentative2);
    }
}
