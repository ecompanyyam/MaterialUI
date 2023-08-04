package com.yam.ecompany.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yam.ecompany.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedBackFromEmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBackFromEmployee.class);
        FeedBackFromEmployee feedBackFromEmployee1 = new FeedBackFromEmployee();
        feedBackFromEmployee1.setId(1L);
        FeedBackFromEmployee feedBackFromEmployee2 = new FeedBackFromEmployee();
        feedBackFromEmployee2.setId(feedBackFromEmployee1.getId());
        assertThat(feedBackFromEmployee1).isEqualTo(feedBackFromEmployee2);
        feedBackFromEmployee2.setId(2L);
        assertThat(feedBackFromEmployee1).isNotEqualTo(feedBackFromEmployee2);
        feedBackFromEmployee1.setId(null);
        assertThat(feedBackFromEmployee1).isNotEqualTo(feedBackFromEmployee2);
    }
}
