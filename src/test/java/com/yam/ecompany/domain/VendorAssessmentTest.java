package com.yam.ecompany.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yam.ecompany.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VendorAssessmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorAssessment.class);
        VendorAssessment vendorAssessment1 = new VendorAssessment();
        vendorAssessment1.setId(1L);
        VendorAssessment vendorAssessment2 = new VendorAssessment();
        vendorAssessment2.setId(vendorAssessment1.getId());
        assertThat(vendorAssessment1).isEqualTo(vendorAssessment2);
        vendorAssessment2.setId(2L);
        assertThat(vendorAssessment1).isNotEqualTo(vendorAssessment2);
        vendorAssessment1.setId(null);
        assertThat(vendorAssessment1).isNotEqualTo(vendorAssessment2);
    }
}
