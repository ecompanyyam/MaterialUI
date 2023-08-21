package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.domain.VendorAssessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.repository.VendorAssessmentRepository;
import com.yam.ecompany.service.VendorAssessmentService;
import com.yam.ecompany.service.criteria.VendorAssessmentCriteria;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VendorAssessmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VendorAssessmentResourceIT {

    private static final Instant DEFAULT_ASSESSMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSESSMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ASSESSED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ASSESSED_BY = "BBBBBBBBBB";

    private static final Assessment DEFAULT_JOB_KNOWLEDGE = Assessment.POOR;
    private static final Assessment UPDATED_JOB_KNOWLEDGE = Assessment.FAIR;

    private static final String DEFAULT_JOB_KNOWLEDGE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_JOB_KNOWLEDGE_COMMENT = "BBBBBBBBBB";

    private static final Assessment DEFAULT_WORK_QUALITY = Assessment.POOR;
    private static final Assessment UPDATED_WORK_QUALITY = Assessment.FAIR;

    private static final String DEFAULT_WORK_QUALITY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_QUALITY_COMMENT = "BBBBBBBBBB";

    private static final Assessment DEFAULT_ATTENDANCE_PUNCTUALITY = Assessment.POOR;
    private static final Assessment UPDATED_ATTENDANCE_PUNCTUALITY = Assessment.FAIR;

    private static final String DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT = "BBBBBBBBBB";

    private static final Assessment DEFAULT_INITIATIVE = Assessment.POOR;
    private static final Assessment UPDATED_INITIATIVE = Assessment.FAIR;

    private static final String DEFAULT_INITIATIVE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_INITIATIVE_COMMENT = "BBBBBBBBBB";

    private static final Assessment DEFAULT_COMMUNICATION_LISTENING_SKILLS = Assessment.POOR;
    private static final Assessment UPDATED_COMMUNICATION_LISTENING_SKILLS = Assessment.FAIR;

    private static final String DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT = "BBBBBBBBBB";

    private static final Assessment DEFAULT_DEPENDABILITY = Assessment.POOR;
    private static final Assessment UPDATED_DEPENDABILITY = Assessment.FAIR;

    private static final String DEFAULT_DEPENDABILITY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDABILITY_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vendor-assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VendorAssessmentRepository vendorAssessmentRepository;

    @Mock
    private VendorAssessmentRepository vendorAssessmentRepositoryMock;

    @Mock
    private VendorAssessmentService vendorAssessmentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendorAssessmentMockMvc;

    private VendorAssessment vendorAssessment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendorAssessment createEntity(EntityManager em) {
        VendorAssessment vendorAssessment = new VendorAssessment()
            .assessmentDate(DEFAULT_ASSESSMENT_DATE)
            .assessedBY(DEFAULT_ASSESSED_BY)
            .jobKnowledge(DEFAULT_JOB_KNOWLEDGE)
            .jobKnowledgeComment(DEFAULT_JOB_KNOWLEDGE_COMMENT)
            .workQuality(DEFAULT_WORK_QUALITY)
            .workQualityComment(DEFAULT_WORK_QUALITY_COMMENT)
            .attendancePunctuality(DEFAULT_ATTENDANCE_PUNCTUALITY)
            .attendancePunctualityComment(DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT)
            .initiative(DEFAULT_INITIATIVE)
            .initiativeComment(DEFAULT_INITIATIVE_COMMENT)
            .communicationListeningSkills(DEFAULT_COMMUNICATION_LISTENING_SKILLS)
            .communicationListeningSkillsComment(DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT)
            .dependability(DEFAULT_DEPENDABILITY)
            .dependabilityComment(DEFAULT_DEPENDABILITY_COMMENT);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        vendorAssessment.setVendorsName(vendor);
        return vendorAssessment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendorAssessment createUpdatedEntity(EntityManager em) {
        VendorAssessment vendorAssessment = new VendorAssessment()
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .assessedBY(UPDATED_ASSESSED_BY)
            .jobKnowledge(UPDATED_JOB_KNOWLEDGE)
            .jobKnowledgeComment(UPDATED_JOB_KNOWLEDGE_COMMENT)
            .workQuality(UPDATED_WORK_QUALITY)
            .workQualityComment(UPDATED_WORK_QUALITY_COMMENT)
            .attendancePunctuality(UPDATED_ATTENDANCE_PUNCTUALITY)
            .attendancePunctualityComment(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT)
            .initiative(UPDATED_INITIATIVE)
            .initiativeComment(UPDATED_INITIATIVE_COMMENT)
            .communicationListeningSkills(UPDATED_COMMUNICATION_LISTENING_SKILLS)
            .communicationListeningSkillsComment(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT)
            .dependability(UPDATED_DEPENDABILITY)
            .dependabilityComment(UPDATED_DEPENDABILITY_COMMENT);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createUpdatedEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        vendorAssessment.setVendorsName(vendor);
        return vendorAssessment;
    }

    @BeforeEach
    public void initTest() {
        vendorAssessment = createEntity(em);
    }

    @Test
    @Transactional
    void createVendorAssessment() throws Exception {
        int databaseSizeBeforeCreate = vendorAssessmentRepository.findAll().size();
        // Create the VendorAssessment
        restVendorAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isCreated());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeCreate + 1);
        VendorAssessment testVendorAssessment = vendorAssessmentList.get(vendorAssessmentList.size() - 1);
        assertThat(testVendorAssessment.getAssessmentDate()).isEqualTo(DEFAULT_ASSESSMENT_DATE);
        assertThat(testVendorAssessment.getAssessedBY()).isEqualTo(DEFAULT_ASSESSED_BY);
        assertThat(testVendorAssessment.getJobKnowledge()).isEqualTo(DEFAULT_JOB_KNOWLEDGE);
        assertThat(testVendorAssessment.getJobKnowledgeComment()).isEqualTo(DEFAULT_JOB_KNOWLEDGE_COMMENT);
        assertThat(testVendorAssessment.getWorkQuality()).isEqualTo(DEFAULT_WORK_QUALITY);
        assertThat(testVendorAssessment.getWorkQualityComment()).isEqualTo(DEFAULT_WORK_QUALITY_COMMENT);
        assertThat(testVendorAssessment.getAttendancePunctuality()).isEqualTo(DEFAULT_ATTENDANCE_PUNCTUALITY);
        assertThat(testVendorAssessment.getAttendancePunctualityComment()).isEqualTo(DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT);
        assertThat(testVendorAssessment.getInitiative()).isEqualTo(DEFAULT_INITIATIVE);
        assertThat(testVendorAssessment.getInitiativeComment()).isEqualTo(DEFAULT_INITIATIVE_COMMENT);
        assertThat(testVendorAssessment.getCommunicationListeningSkills()).isEqualTo(DEFAULT_COMMUNICATION_LISTENING_SKILLS);
        assertThat(testVendorAssessment.getCommunicationListeningSkillsComment()).isEqualTo(DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT);
        assertThat(testVendorAssessment.getDependability()).isEqualTo(DEFAULT_DEPENDABILITY);
        assertThat(testVendorAssessment.getDependabilityComment()).isEqualTo(DEFAULT_DEPENDABILITY_COMMENT);
    }

    @Test
    @Transactional
    void createVendorAssessmentWithExistingId() throws Exception {
        // Create the VendorAssessment with an existing ID
        vendorAssessment.setId(1L);

        int databaseSizeBeforeCreate = vendorAssessmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVendorAssessments() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorAssessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assessmentDate").value(hasItem(DEFAULT_ASSESSMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].assessedBY").value(hasItem(DEFAULT_ASSESSED_BY)))
            .andExpect(jsonPath("$.[*].jobKnowledge").value(hasItem(DEFAULT_JOB_KNOWLEDGE.toString())))
            .andExpect(jsonPath("$.[*].jobKnowledgeComment").value(hasItem(DEFAULT_JOB_KNOWLEDGE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].workQuality").value(hasItem(DEFAULT_WORK_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].workQualityComment").value(hasItem(DEFAULT_WORK_QUALITY_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].attendancePunctuality").value(hasItem(DEFAULT_ATTENDANCE_PUNCTUALITY.toString())))
            .andExpect(jsonPath("$.[*].attendancePunctualityComment").value(hasItem(DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].initiative").value(hasItem(DEFAULT_INITIATIVE.toString())))
            .andExpect(jsonPath("$.[*].initiativeComment").value(hasItem(DEFAULT_INITIATIVE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].communicationListeningSkills").value(hasItem(DEFAULT_COMMUNICATION_LISTENING_SKILLS.toString())))
            .andExpect(
                jsonPath("$.[*].communicationListeningSkillsComment")
                    .value(hasItem(DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT.toString()))
            )
            .andExpect(jsonPath("$.[*].dependability").value(hasItem(DEFAULT_DEPENDABILITY.toString())))
            .andExpect(jsonPath("$.[*].dependabilityComment").value(hasItem(DEFAULT_DEPENDABILITY_COMMENT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendorAssessmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(vendorAssessmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendorAssessmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vendorAssessmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendorAssessmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vendorAssessmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendorAssessmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(vendorAssessmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVendorAssessment() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get the vendorAssessment
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, vendorAssessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vendorAssessment.getId().intValue()))
            .andExpect(jsonPath("$.assessmentDate").value(DEFAULT_ASSESSMENT_DATE.toString()))
            .andExpect(jsonPath("$.assessedBY").value(DEFAULT_ASSESSED_BY))
            .andExpect(jsonPath("$.jobKnowledge").value(DEFAULT_JOB_KNOWLEDGE.toString()))
            .andExpect(jsonPath("$.jobKnowledgeComment").value(DEFAULT_JOB_KNOWLEDGE_COMMENT.toString()))
            .andExpect(jsonPath("$.workQuality").value(DEFAULT_WORK_QUALITY.toString()))
            .andExpect(jsonPath("$.workQualityComment").value(DEFAULT_WORK_QUALITY_COMMENT.toString()))
            .andExpect(jsonPath("$.attendancePunctuality").value(DEFAULT_ATTENDANCE_PUNCTUALITY.toString()))
            .andExpect(jsonPath("$.attendancePunctualityComment").value(DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT.toString()))
            .andExpect(jsonPath("$.initiative").value(DEFAULT_INITIATIVE.toString()))
            .andExpect(jsonPath("$.initiativeComment").value(DEFAULT_INITIATIVE_COMMENT.toString()))
            .andExpect(jsonPath("$.communicationListeningSkills").value(DEFAULT_COMMUNICATION_LISTENING_SKILLS.toString()))
            .andExpect(jsonPath("$.communicationListeningSkillsComment").value(DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT.toString()))
            .andExpect(jsonPath("$.dependability").value(DEFAULT_DEPENDABILITY.toString()))
            .andExpect(jsonPath("$.dependabilityComment").value(DEFAULT_DEPENDABILITY_COMMENT.toString()));
    }

    @Test
    @Transactional
    void getVendorAssessmentsByIdFiltering() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        Long id = vendorAssessment.getId();

        defaultVendorAssessmentShouldBeFound("id.equals=" + id);
        defaultVendorAssessmentShouldNotBeFound("id.notEquals=" + id);

        defaultVendorAssessmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendorAssessmentShouldNotBeFound("id.greaterThan=" + id);

        defaultVendorAssessmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendorAssessmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessmentDate equals to DEFAULT_ASSESSMENT_DATE
        defaultVendorAssessmentShouldBeFound("assessmentDate.equals=" + DEFAULT_ASSESSMENT_DATE);

        // Get all the vendorAssessmentList where assessmentDate equals to UPDATED_ASSESSMENT_DATE
        defaultVendorAssessmentShouldNotBeFound("assessmentDate.equals=" + UPDATED_ASSESSMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessmentDate in DEFAULT_ASSESSMENT_DATE or UPDATED_ASSESSMENT_DATE
        defaultVendorAssessmentShouldBeFound("assessmentDate.in=" + DEFAULT_ASSESSMENT_DATE + "," + UPDATED_ASSESSMENT_DATE);

        // Get all the vendorAssessmentList where assessmentDate equals to UPDATED_ASSESSMENT_DATE
        defaultVendorAssessmentShouldNotBeFound("assessmentDate.in=" + UPDATED_ASSESSMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessmentDate is not null
        defaultVendorAssessmentShouldBeFound("assessmentDate.specified=true");

        // Get all the vendorAssessmentList where assessmentDate is null
        defaultVendorAssessmentShouldNotBeFound("assessmentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessedBYIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessedBY equals to DEFAULT_ASSESSED_BY
        defaultVendorAssessmentShouldBeFound("assessedBY.equals=" + DEFAULT_ASSESSED_BY);

        // Get all the vendorAssessmentList where assessedBY equals to UPDATED_ASSESSED_BY
        defaultVendorAssessmentShouldNotBeFound("assessedBY.equals=" + UPDATED_ASSESSED_BY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessedBYIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessedBY in DEFAULT_ASSESSED_BY or UPDATED_ASSESSED_BY
        defaultVendorAssessmentShouldBeFound("assessedBY.in=" + DEFAULT_ASSESSED_BY + "," + UPDATED_ASSESSED_BY);

        // Get all the vendorAssessmentList where assessedBY equals to UPDATED_ASSESSED_BY
        defaultVendorAssessmentShouldNotBeFound("assessedBY.in=" + UPDATED_ASSESSED_BY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessedBYIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessedBY is not null
        defaultVendorAssessmentShouldBeFound("assessedBY.specified=true");

        // Get all the vendorAssessmentList where assessedBY is null
        defaultVendorAssessmentShouldNotBeFound("assessedBY.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessedBYContainsSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessedBY contains DEFAULT_ASSESSED_BY
        defaultVendorAssessmentShouldBeFound("assessedBY.contains=" + DEFAULT_ASSESSED_BY);

        // Get all the vendorAssessmentList where assessedBY contains UPDATED_ASSESSED_BY
        defaultVendorAssessmentShouldNotBeFound("assessedBY.contains=" + UPDATED_ASSESSED_BY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAssessedBYNotContainsSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where assessedBY does not contain DEFAULT_ASSESSED_BY
        defaultVendorAssessmentShouldNotBeFound("assessedBY.doesNotContain=" + DEFAULT_ASSESSED_BY);

        // Get all the vendorAssessmentList where assessedBY does not contain UPDATED_ASSESSED_BY
        defaultVendorAssessmentShouldBeFound("assessedBY.doesNotContain=" + UPDATED_ASSESSED_BY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByJobKnowledgeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where jobKnowledge equals to DEFAULT_JOB_KNOWLEDGE
        defaultVendorAssessmentShouldBeFound("jobKnowledge.equals=" + DEFAULT_JOB_KNOWLEDGE);

        // Get all the vendorAssessmentList where jobKnowledge equals to UPDATED_JOB_KNOWLEDGE
        defaultVendorAssessmentShouldNotBeFound("jobKnowledge.equals=" + UPDATED_JOB_KNOWLEDGE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByJobKnowledgeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where jobKnowledge in DEFAULT_JOB_KNOWLEDGE or UPDATED_JOB_KNOWLEDGE
        defaultVendorAssessmentShouldBeFound("jobKnowledge.in=" + DEFAULT_JOB_KNOWLEDGE + "," + UPDATED_JOB_KNOWLEDGE);

        // Get all the vendorAssessmentList where jobKnowledge equals to UPDATED_JOB_KNOWLEDGE
        defaultVendorAssessmentShouldNotBeFound("jobKnowledge.in=" + UPDATED_JOB_KNOWLEDGE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByJobKnowledgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where jobKnowledge is not null
        defaultVendorAssessmentShouldBeFound("jobKnowledge.specified=true");

        // Get all the vendorAssessmentList where jobKnowledge is null
        defaultVendorAssessmentShouldNotBeFound("jobKnowledge.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByWorkQualityIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where workQuality equals to DEFAULT_WORK_QUALITY
        defaultVendorAssessmentShouldBeFound("workQuality.equals=" + DEFAULT_WORK_QUALITY);

        // Get all the vendorAssessmentList where workQuality equals to UPDATED_WORK_QUALITY
        defaultVendorAssessmentShouldNotBeFound("workQuality.equals=" + UPDATED_WORK_QUALITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByWorkQualityIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where workQuality in DEFAULT_WORK_QUALITY or UPDATED_WORK_QUALITY
        defaultVendorAssessmentShouldBeFound("workQuality.in=" + DEFAULT_WORK_QUALITY + "," + UPDATED_WORK_QUALITY);

        // Get all the vendorAssessmentList where workQuality equals to UPDATED_WORK_QUALITY
        defaultVendorAssessmentShouldNotBeFound("workQuality.in=" + UPDATED_WORK_QUALITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByWorkQualityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where workQuality is not null
        defaultVendorAssessmentShouldBeFound("workQuality.specified=true");

        // Get all the vendorAssessmentList where workQuality is null
        defaultVendorAssessmentShouldNotBeFound("workQuality.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAttendancePunctualityIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where attendancePunctuality equals to DEFAULT_ATTENDANCE_PUNCTUALITY
        defaultVendorAssessmentShouldBeFound("attendancePunctuality.equals=" + DEFAULT_ATTENDANCE_PUNCTUALITY);

        // Get all the vendorAssessmentList where attendancePunctuality equals to UPDATED_ATTENDANCE_PUNCTUALITY
        defaultVendorAssessmentShouldNotBeFound("attendancePunctuality.equals=" + UPDATED_ATTENDANCE_PUNCTUALITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAttendancePunctualityIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where attendancePunctuality in DEFAULT_ATTENDANCE_PUNCTUALITY or UPDATED_ATTENDANCE_PUNCTUALITY
        defaultVendorAssessmentShouldBeFound(
            "attendancePunctuality.in=" + DEFAULT_ATTENDANCE_PUNCTUALITY + "," + UPDATED_ATTENDANCE_PUNCTUALITY
        );

        // Get all the vendorAssessmentList where attendancePunctuality equals to UPDATED_ATTENDANCE_PUNCTUALITY
        defaultVendorAssessmentShouldNotBeFound("attendancePunctuality.in=" + UPDATED_ATTENDANCE_PUNCTUALITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByAttendancePunctualityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where attendancePunctuality is not null
        defaultVendorAssessmentShouldBeFound("attendancePunctuality.specified=true");

        // Get all the vendorAssessmentList where attendancePunctuality is null
        defaultVendorAssessmentShouldNotBeFound("attendancePunctuality.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByInitiativeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where initiative equals to DEFAULT_INITIATIVE
        defaultVendorAssessmentShouldBeFound("initiative.equals=" + DEFAULT_INITIATIVE);

        // Get all the vendorAssessmentList where initiative equals to UPDATED_INITIATIVE
        defaultVendorAssessmentShouldNotBeFound("initiative.equals=" + UPDATED_INITIATIVE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByInitiativeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where initiative in DEFAULT_INITIATIVE or UPDATED_INITIATIVE
        defaultVendorAssessmentShouldBeFound("initiative.in=" + DEFAULT_INITIATIVE + "," + UPDATED_INITIATIVE);

        // Get all the vendorAssessmentList where initiative equals to UPDATED_INITIATIVE
        defaultVendorAssessmentShouldNotBeFound("initiative.in=" + UPDATED_INITIATIVE);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByInitiativeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where initiative is not null
        defaultVendorAssessmentShouldBeFound("initiative.specified=true");

        // Get all the vendorAssessmentList where initiative is null
        defaultVendorAssessmentShouldNotBeFound("initiative.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByCommunicationListeningSkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where communicationListeningSkills equals to DEFAULT_COMMUNICATION_LISTENING_SKILLS
        defaultVendorAssessmentShouldBeFound("communicationListeningSkills.equals=" + DEFAULT_COMMUNICATION_LISTENING_SKILLS);

        // Get all the vendorAssessmentList where communicationListeningSkills equals to UPDATED_COMMUNICATION_LISTENING_SKILLS
        defaultVendorAssessmentShouldNotBeFound("communicationListeningSkills.equals=" + UPDATED_COMMUNICATION_LISTENING_SKILLS);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByCommunicationListeningSkillsIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where communicationListeningSkills in DEFAULT_COMMUNICATION_LISTENING_SKILLS or UPDATED_COMMUNICATION_LISTENING_SKILLS
        defaultVendorAssessmentShouldBeFound(
            "communicationListeningSkills.in=" + DEFAULT_COMMUNICATION_LISTENING_SKILLS + "," + UPDATED_COMMUNICATION_LISTENING_SKILLS
        );

        // Get all the vendorAssessmentList where communicationListeningSkills equals to UPDATED_COMMUNICATION_LISTENING_SKILLS
        defaultVendorAssessmentShouldNotBeFound("communicationListeningSkills.in=" + UPDATED_COMMUNICATION_LISTENING_SKILLS);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByCommunicationListeningSkillsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where communicationListeningSkills is not null
        defaultVendorAssessmentShouldBeFound("communicationListeningSkills.specified=true");

        // Get all the vendorAssessmentList where communicationListeningSkills is null
        defaultVendorAssessmentShouldNotBeFound("communicationListeningSkills.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByDependabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where dependability equals to DEFAULT_DEPENDABILITY
        defaultVendorAssessmentShouldBeFound("dependability.equals=" + DEFAULT_DEPENDABILITY);

        // Get all the vendorAssessmentList where dependability equals to UPDATED_DEPENDABILITY
        defaultVendorAssessmentShouldNotBeFound("dependability.equals=" + UPDATED_DEPENDABILITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByDependabilityIsInShouldWork() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where dependability in DEFAULT_DEPENDABILITY or UPDATED_DEPENDABILITY
        defaultVendorAssessmentShouldBeFound("dependability.in=" + DEFAULT_DEPENDABILITY + "," + UPDATED_DEPENDABILITY);

        // Get all the vendorAssessmentList where dependability equals to UPDATED_DEPENDABILITY
        defaultVendorAssessmentShouldNotBeFound("dependability.in=" + UPDATED_DEPENDABILITY);
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByDependabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        // Get all the vendorAssessmentList where dependability is not null
        defaultVendorAssessmentShouldBeFound("dependability.specified=true");

        // Get all the vendorAssessmentList where dependability is null
        defaultVendorAssessmentShouldNotBeFound("dependability.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorAssessmentsByVendorsNameIsEqualToSomething() throws Exception {
        Vendor vendorsName;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendorAssessmentRepository.saveAndFlush(vendorAssessment);
            vendorsName = VendorResourceIT.createEntity(em);
        } else {
            vendorsName = TestUtil.findAll(em, Vendor.class).get(0);
        }
        em.persist(vendorsName);
        em.flush();
        vendorAssessment.setVendorsName(vendorsName);
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);
        Long vendorsNameId = vendorsName.getId();
        // Get all the vendorAssessmentList where vendorsName equals to vendorsNameId
        defaultVendorAssessmentShouldBeFound("vendorsNameId.equals=" + vendorsNameId);

        // Get all the vendorAssessmentList where vendorsName equals to (vendorsNameId + 1)
        defaultVendorAssessmentShouldNotBeFound("vendorsNameId.equals=" + (vendorsNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendorAssessmentShouldBeFound(String filter) throws Exception {
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorAssessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assessmentDate").value(hasItem(DEFAULT_ASSESSMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].assessedBY").value(hasItem(DEFAULT_ASSESSED_BY)))
            .andExpect(jsonPath("$.[*].jobKnowledge").value(hasItem(DEFAULT_JOB_KNOWLEDGE.toString())))
            .andExpect(jsonPath("$.[*].jobKnowledgeComment").value(hasItem(DEFAULT_JOB_KNOWLEDGE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].workQuality").value(hasItem(DEFAULT_WORK_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].workQualityComment").value(hasItem(DEFAULT_WORK_QUALITY_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].attendancePunctuality").value(hasItem(DEFAULT_ATTENDANCE_PUNCTUALITY.toString())))
            .andExpect(jsonPath("$.[*].attendancePunctualityComment").value(hasItem(DEFAULT_ATTENDANCE_PUNCTUALITY_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].initiative").value(hasItem(DEFAULT_INITIATIVE.toString())))
            .andExpect(jsonPath("$.[*].initiativeComment").value(hasItem(DEFAULT_INITIATIVE_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].communicationListeningSkills").value(hasItem(DEFAULT_COMMUNICATION_LISTENING_SKILLS.toString())))
            .andExpect(
                jsonPath("$.[*].communicationListeningSkillsComment")
                    .value(hasItem(DEFAULT_COMMUNICATION_LISTENING_SKILLS_COMMENT.toString()))
            )
            .andExpect(jsonPath("$.[*].dependability").value(hasItem(DEFAULT_DEPENDABILITY.toString())))
            .andExpect(jsonPath("$.[*].dependabilityComment").value(hasItem(DEFAULT_DEPENDABILITY_COMMENT.toString())));

        // Check, that the count call also returns 1
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendorAssessmentShouldNotBeFound(String filter) throws Exception {
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVendorAssessment() throws Exception {
        // Get the vendorAssessment
        restVendorAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVendorAssessment() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();

        // Update the vendorAssessment
        VendorAssessment updatedVendorAssessment = vendorAssessmentRepository.findById(vendorAssessment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVendorAssessment are not directly saved in db
        em.detach(updatedVendorAssessment);
        updatedVendorAssessment
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .assessedBY(UPDATED_ASSESSED_BY)
            .jobKnowledge(UPDATED_JOB_KNOWLEDGE)
            .jobKnowledgeComment(UPDATED_JOB_KNOWLEDGE_COMMENT)
            .workQuality(UPDATED_WORK_QUALITY)
            .workQualityComment(UPDATED_WORK_QUALITY_COMMENT)
            .attendancePunctuality(UPDATED_ATTENDANCE_PUNCTUALITY)
            .attendancePunctualityComment(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT)
            .initiative(UPDATED_INITIATIVE)
            .initiativeComment(UPDATED_INITIATIVE_COMMENT)
            .communicationListeningSkills(UPDATED_COMMUNICATION_LISTENING_SKILLS)
            .communicationListeningSkillsComment(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT)
            .dependability(UPDATED_DEPENDABILITY)
            .dependabilityComment(UPDATED_DEPENDABILITY_COMMENT);

        restVendorAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVendorAssessment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVendorAssessment))
            )
            .andExpect(status().isOk());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
        VendorAssessment testVendorAssessment = vendorAssessmentList.get(vendorAssessmentList.size() - 1);
        assertThat(testVendorAssessment.getAssessmentDate()).isEqualTo(UPDATED_ASSESSMENT_DATE);
        assertThat(testVendorAssessment.getAssessedBY()).isEqualTo(UPDATED_ASSESSED_BY);
        assertThat(testVendorAssessment.getJobKnowledge()).isEqualTo(UPDATED_JOB_KNOWLEDGE);
        assertThat(testVendorAssessment.getJobKnowledgeComment()).isEqualTo(UPDATED_JOB_KNOWLEDGE_COMMENT);
        assertThat(testVendorAssessment.getWorkQuality()).isEqualTo(UPDATED_WORK_QUALITY);
        assertThat(testVendorAssessment.getWorkQualityComment()).isEqualTo(UPDATED_WORK_QUALITY_COMMENT);
        assertThat(testVendorAssessment.getAttendancePunctuality()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY);
        assertThat(testVendorAssessment.getAttendancePunctualityComment()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT);
        assertThat(testVendorAssessment.getInitiative()).isEqualTo(UPDATED_INITIATIVE);
        assertThat(testVendorAssessment.getInitiativeComment()).isEqualTo(UPDATED_INITIATIVE_COMMENT);
        assertThat(testVendorAssessment.getCommunicationListeningSkills()).isEqualTo(UPDATED_COMMUNICATION_LISTENING_SKILLS);
        assertThat(testVendorAssessment.getCommunicationListeningSkillsComment()).isEqualTo(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT);
        assertThat(testVendorAssessment.getDependability()).isEqualTo(UPDATED_DEPENDABILITY);
        assertThat(testVendorAssessment.getDependabilityComment()).isEqualTo(UPDATED_DEPENDABILITY_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendorAssessment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVendorAssessmentWithPatch() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();

        // Update the vendorAssessment using partial update
        VendorAssessment partialUpdatedVendorAssessment = new VendorAssessment();
        partialUpdatedVendorAssessment.setId(vendorAssessment.getId());

        partialUpdatedVendorAssessment
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .jobKnowledge(UPDATED_JOB_KNOWLEDGE)
            .workQualityComment(UPDATED_WORK_QUALITY_COMMENT)
            .attendancePunctuality(UPDATED_ATTENDANCE_PUNCTUALITY)
            .attendancePunctualityComment(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT)
            .initiative(UPDATED_INITIATIVE)
            .communicationListeningSkillsComment(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT);

        restVendorAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendorAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendorAssessment))
            )
            .andExpect(status().isOk());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
        VendorAssessment testVendorAssessment = vendorAssessmentList.get(vendorAssessmentList.size() - 1);
        assertThat(testVendorAssessment.getAssessmentDate()).isEqualTo(UPDATED_ASSESSMENT_DATE);
        assertThat(testVendorAssessment.getAssessedBY()).isEqualTo(DEFAULT_ASSESSED_BY);
        assertThat(testVendorAssessment.getJobKnowledge()).isEqualTo(UPDATED_JOB_KNOWLEDGE);
        assertThat(testVendorAssessment.getJobKnowledgeComment()).isEqualTo(DEFAULT_JOB_KNOWLEDGE_COMMENT);
        assertThat(testVendorAssessment.getWorkQuality()).isEqualTo(DEFAULT_WORK_QUALITY);
        assertThat(testVendorAssessment.getWorkQualityComment()).isEqualTo(UPDATED_WORK_QUALITY_COMMENT);
        assertThat(testVendorAssessment.getAttendancePunctuality()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY);
        assertThat(testVendorAssessment.getAttendancePunctualityComment()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT);
        assertThat(testVendorAssessment.getInitiative()).isEqualTo(UPDATED_INITIATIVE);
        assertThat(testVendorAssessment.getInitiativeComment()).isEqualTo(DEFAULT_INITIATIVE_COMMENT);
        assertThat(testVendorAssessment.getCommunicationListeningSkills()).isEqualTo(DEFAULT_COMMUNICATION_LISTENING_SKILLS);
        assertThat(testVendorAssessment.getCommunicationListeningSkillsComment()).isEqualTo(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT);
        assertThat(testVendorAssessment.getDependability()).isEqualTo(DEFAULT_DEPENDABILITY);
        assertThat(testVendorAssessment.getDependabilityComment()).isEqualTo(DEFAULT_DEPENDABILITY_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateVendorAssessmentWithPatch() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();

        // Update the vendorAssessment using partial update
        VendorAssessment partialUpdatedVendorAssessment = new VendorAssessment();
        partialUpdatedVendorAssessment.setId(vendorAssessment.getId());

        partialUpdatedVendorAssessment
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .assessedBY(UPDATED_ASSESSED_BY)
            .jobKnowledge(UPDATED_JOB_KNOWLEDGE)
            .jobKnowledgeComment(UPDATED_JOB_KNOWLEDGE_COMMENT)
            .workQuality(UPDATED_WORK_QUALITY)
            .workQualityComment(UPDATED_WORK_QUALITY_COMMENT)
            .attendancePunctuality(UPDATED_ATTENDANCE_PUNCTUALITY)
            .attendancePunctualityComment(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT)
            .initiative(UPDATED_INITIATIVE)
            .initiativeComment(UPDATED_INITIATIVE_COMMENT)
            .communicationListeningSkills(UPDATED_COMMUNICATION_LISTENING_SKILLS)
            .communicationListeningSkillsComment(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT)
            .dependability(UPDATED_DEPENDABILITY)
            .dependabilityComment(UPDATED_DEPENDABILITY_COMMENT);

        restVendorAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendorAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendorAssessment))
            )
            .andExpect(status().isOk());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
        VendorAssessment testVendorAssessment = vendorAssessmentList.get(vendorAssessmentList.size() - 1);
        assertThat(testVendorAssessment.getAssessmentDate()).isEqualTo(UPDATED_ASSESSMENT_DATE);
        assertThat(testVendorAssessment.getAssessedBY()).isEqualTo(UPDATED_ASSESSED_BY);
        assertThat(testVendorAssessment.getJobKnowledge()).isEqualTo(UPDATED_JOB_KNOWLEDGE);
        assertThat(testVendorAssessment.getJobKnowledgeComment()).isEqualTo(UPDATED_JOB_KNOWLEDGE_COMMENT);
        assertThat(testVendorAssessment.getWorkQuality()).isEqualTo(UPDATED_WORK_QUALITY);
        assertThat(testVendorAssessment.getWorkQualityComment()).isEqualTo(UPDATED_WORK_QUALITY_COMMENT);
        assertThat(testVendorAssessment.getAttendancePunctuality()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY);
        assertThat(testVendorAssessment.getAttendancePunctualityComment()).isEqualTo(UPDATED_ATTENDANCE_PUNCTUALITY_COMMENT);
        assertThat(testVendorAssessment.getInitiative()).isEqualTo(UPDATED_INITIATIVE);
        assertThat(testVendorAssessment.getInitiativeComment()).isEqualTo(UPDATED_INITIATIVE_COMMENT);
        assertThat(testVendorAssessment.getCommunicationListeningSkills()).isEqualTo(UPDATED_COMMUNICATION_LISTENING_SKILLS);
        assertThat(testVendorAssessment.getCommunicationListeningSkillsComment()).isEqualTo(UPDATED_COMMUNICATION_LISTENING_SKILLS_COMMENT);
        assertThat(testVendorAssessment.getDependability()).isEqualTo(UPDATED_DEPENDABILITY);
        assertThat(testVendorAssessment.getDependabilityComment()).isEqualTo(UPDATED_DEPENDABILITY_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vendorAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVendorAssessment() throws Exception {
        int databaseSizeBeforeUpdate = vendorAssessmentRepository.findAll().size();
        vendorAssessment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendorAssessment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VendorAssessment in the database
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVendorAssessment() throws Exception {
        // Initialize the database
        vendorAssessmentRepository.saveAndFlush(vendorAssessment);

        int databaseSizeBeforeDelete = vendorAssessmentRepository.findAll().size();

        // Delete the vendorAssessment
        restVendorAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, vendorAssessment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VendorAssessment> vendorAssessmentList = vendorAssessmentRepository.findAll();
        assertThat(vendorAssessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
