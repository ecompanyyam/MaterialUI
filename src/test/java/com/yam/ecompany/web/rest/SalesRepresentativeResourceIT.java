package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.SalesRepresentative;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.repository.SalesRepresentativeRepository;
import com.yam.ecompany.service.SalesRepresentativeService;
import com.yam.ecompany.service.criteria.SalesRepresentativeCriteria;
import jakarta.persistence.EntityManager;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SalesRepresentativeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SalesRepresentativeResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "&,Ni@7.ULi($2";
    private static final String UPDATED_EMAIL = "&t2iV@e.BUx.#hGNg^";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sales-representatives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesRepresentativeRepository salesRepresentativeRepository;

    @Mock
    private SalesRepresentativeRepository salesRepresentativeRepositoryMock;

    @Mock
    private SalesRepresentativeService salesRepresentativeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesRepresentativeMockMvc;

    private SalesRepresentative salesRepresentative;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesRepresentative createEntity(EntityManager em) {
        SalesRepresentative salesRepresentative = new SalesRepresentative()
            .fullName(DEFAULT_FULL_NAME)
            .jobTitle(DEFAULT_JOB_TITLE)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .officeLocation(DEFAULT_OFFICE_LOCATION)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .otherDetails(DEFAULT_OTHER_DETAILS);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        salesRepresentative.setVendorsName(vendor);
        return salesRepresentative;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesRepresentative createUpdatedEntity(EntityManager em) {
        SalesRepresentative salesRepresentative = new SalesRepresentative()
            .fullName(UPDATED_FULL_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .otherDetails(UPDATED_OTHER_DETAILS);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createUpdatedEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        salesRepresentative.setVendorsName(vendor);
        return salesRepresentative;
    }

    @BeforeEach
    public void initTest() {
        salesRepresentative = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesRepresentative() throws Exception {
        int databaseSizeBeforeCreate = salesRepresentativeRepository.findAll().size();
        // Create the SalesRepresentative
        restSalesRepresentativeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isCreated());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeCreate + 1);
        SalesRepresentative testSalesRepresentative = salesRepresentativeList.get(salesRepresentativeList.size() - 1);
        assertThat(testSalesRepresentative.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testSalesRepresentative.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testSalesRepresentative.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSalesRepresentative.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSalesRepresentative.getOfficeLocation()).isEqualTo(DEFAULT_OFFICE_LOCATION);
        assertThat(testSalesRepresentative.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testSalesRepresentative.getOtherDetails()).isEqualTo(DEFAULT_OTHER_DETAILS);
    }

    @Test
    @Transactional
    void createSalesRepresentativeWithExistingId() throws Exception {
        // Create the SalesRepresentative with an existing ID
        salesRepresentative.setId(1L);

        int databaseSizeBeforeCreate = salesRepresentativeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesRepresentativeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesRepresentatives() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].officeLocation").value(hasItem(DEFAULT_OFFICE_LOCATION)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesRepresentativesWithEagerRelationshipsIsEnabled() throws Exception {
        when(salesRepresentativeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesRepresentativeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(salesRepresentativeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesRepresentativesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(salesRepresentativeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesRepresentativeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(salesRepresentativeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSalesRepresentative() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get the salesRepresentative
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL_ID, salesRepresentative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesRepresentative.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.officeLocation").value(DEFAULT_OFFICE_LOCATION))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS.toString()));
    }

    @Test
    @Transactional
    void getSalesRepresentativesByIdFiltering() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        Long id = salesRepresentative.getId();

        defaultSalesRepresentativeShouldBeFound("id.equals=" + id);
        defaultSalesRepresentativeShouldNotBeFound("id.notEquals=" + id);

        defaultSalesRepresentativeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSalesRepresentativeShouldNotBeFound("id.greaterThan=" + id);

        defaultSalesRepresentativeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSalesRepresentativeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where fullName equals to DEFAULT_FULL_NAME
        defaultSalesRepresentativeShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the salesRepresentativeList where fullName equals to UPDATED_FULL_NAME
        defaultSalesRepresentativeShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultSalesRepresentativeShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the salesRepresentativeList where fullName equals to UPDATED_FULL_NAME
        defaultSalesRepresentativeShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where fullName is not null
        defaultSalesRepresentativeShouldBeFound("fullName.specified=true");

        // Get all the salesRepresentativeList where fullName is null
        defaultSalesRepresentativeShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where fullName contains DEFAULT_FULL_NAME
        defaultSalesRepresentativeShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the salesRepresentativeList where fullName contains UPDATED_FULL_NAME
        defaultSalesRepresentativeShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where fullName does not contain DEFAULT_FULL_NAME
        defaultSalesRepresentativeShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the salesRepresentativeList where fullName does not contain UPDATED_FULL_NAME
        defaultSalesRepresentativeShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultSalesRepresentativeShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the salesRepresentativeList where jobTitle equals to UPDATED_JOB_TITLE
        defaultSalesRepresentativeShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultSalesRepresentativeShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the salesRepresentativeList where jobTitle equals to UPDATED_JOB_TITLE
        defaultSalesRepresentativeShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where jobTitle is not null
        defaultSalesRepresentativeShouldBeFound("jobTitle.specified=true");

        // Get all the salesRepresentativeList where jobTitle is null
        defaultSalesRepresentativeShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByJobTitleContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where jobTitle contains DEFAULT_JOB_TITLE
        defaultSalesRepresentativeShouldBeFound("jobTitle.contains=" + DEFAULT_JOB_TITLE);

        // Get all the salesRepresentativeList where jobTitle contains UPDATED_JOB_TITLE
        defaultSalesRepresentativeShouldNotBeFound("jobTitle.contains=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByJobTitleNotContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where jobTitle does not contain DEFAULT_JOB_TITLE
        defaultSalesRepresentativeShouldNotBeFound("jobTitle.doesNotContain=" + DEFAULT_JOB_TITLE);

        // Get all the salesRepresentativeList where jobTitle does not contain UPDATED_JOB_TITLE
        defaultSalesRepresentativeShouldBeFound("jobTitle.doesNotContain=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where email equals to DEFAULT_EMAIL
        defaultSalesRepresentativeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the salesRepresentativeList where email equals to UPDATED_EMAIL
        defaultSalesRepresentativeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSalesRepresentativeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the salesRepresentativeList where email equals to UPDATED_EMAIL
        defaultSalesRepresentativeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where email is not null
        defaultSalesRepresentativeShouldBeFound("email.specified=true");

        // Get all the salesRepresentativeList where email is null
        defaultSalesRepresentativeShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByEmailContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where email contains DEFAULT_EMAIL
        defaultSalesRepresentativeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the salesRepresentativeList where email contains UPDATED_EMAIL
        defaultSalesRepresentativeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where email does not contain DEFAULT_EMAIL
        defaultSalesRepresentativeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the salesRepresentativeList where email does not contain UPDATED_EMAIL
        defaultSalesRepresentativeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where phone equals to DEFAULT_PHONE
        defaultSalesRepresentativeShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the salesRepresentativeList where phone equals to UPDATED_PHONE
        defaultSalesRepresentativeShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSalesRepresentativeShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the salesRepresentativeList where phone equals to UPDATED_PHONE
        defaultSalesRepresentativeShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where phone is not null
        defaultSalesRepresentativeShouldBeFound("phone.specified=true");

        // Get all the salesRepresentativeList where phone is null
        defaultSalesRepresentativeShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where phone contains DEFAULT_PHONE
        defaultSalesRepresentativeShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the salesRepresentativeList where phone contains UPDATED_PHONE
        defaultSalesRepresentativeShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where phone does not contain DEFAULT_PHONE
        defaultSalesRepresentativeShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the salesRepresentativeList where phone does not contain UPDATED_PHONE
        defaultSalesRepresentativeShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByOfficeLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where officeLocation equals to DEFAULT_OFFICE_LOCATION
        defaultSalesRepresentativeShouldBeFound("officeLocation.equals=" + DEFAULT_OFFICE_LOCATION);

        // Get all the salesRepresentativeList where officeLocation equals to UPDATED_OFFICE_LOCATION
        defaultSalesRepresentativeShouldNotBeFound("officeLocation.equals=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByOfficeLocationIsInShouldWork() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where officeLocation in DEFAULT_OFFICE_LOCATION or UPDATED_OFFICE_LOCATION
        defaultSalesRepresentativeShouldBeFound("officeLocation.in=" + DEFAULT_OFFICE_LOCATION + "," + UPDATED_OFFICE_LOCATION);

        // Get all the salesRepresentativeList where officeLocation equals to UPDATED_OFFICE_LOCATION
        defaultSalesRepresentativeShouldNotBeFound("officeLocation.in=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByOfficeLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where officeLocation is not null
        defaultSalesRepresentativeShouldBeFound("officeLocation.specified=true");

        // Get all the salesRepresentativeList where officeLocation is null
        defaultSalesRepresentativeShouldNotBeFound("officeLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByOfficeLocationContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where officeLocation contains DEFAULT_OFFICE_LOCATION
        defaultSalesRepresentativeShouldBeFound("officeLocation.contains=" + DEFAULT_OFFICE_LOCATION);

        // Get all the salesRepresentativeList where officeLocation contains UPDATED_OFFICE_LOCATION
        defaultSalesRepresentativeShouldNotBeFound("officeLocation.contains=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByOfficeLocationNotContainsSomething() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        // Get all the salesRepresentativeList where officeLocation does not contain DEFAULT_OFFICE_LOCATION
        defaultSalesRepresentativeShouldNotBeFound("officeLocation.doesNotContain=" + DEFAULT_OFFICE_LOCATION);

        // Get all the salesRepresentativeList where officeLocation does not contain UPDATED_OFFICE_LOCATION
        defaultSalesRepresentativeShouldBeFound("officeLocation.doesNotContain=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllSalesRepresentativesByVendorsNameIsEqualToSomething() throws Exception {
        Vendor vendorsName;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            salesRepresentativeRepository.saveAndFlush(salesRepresentative);
            vendorsName = VendorResourceIT.createEntity(em);
        } else {
            vendorsName = TestUtil.findAll(em, Vendor.class).get(0);
        }
        em.persist(vendorsName);
        em.flush();
        salesRepresentative.setVendorsName(vendorsName);
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);
        Long vendorsNameId = vendorsName.getId();
        // Get all the salesRepresentativeList where vendorsName equals to vendorsNameId
        defaultSalesRepresentativeShouldBeFound("vendorsNameId.equals=" + vendorsNameId);

        // Get all the salesRepresentativeList where vendorsName equals to (vendorsNameId + 1)
        defaultSalesRepresentativeShouldNotBeFound("vendorsNameId.equals=" + (vendorsNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesRepresentativeShouldBeFound(String filter) throws Exception {
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].officeLocation").value(hasItem(DEFAULT_OFFICE_LOCATION)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));

        // Check, that the count call also returns 1
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesRepresentativeShouldNotBeFound(String filter) throws Exception {
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesRepresentativeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesRepresentative() throws Exception {
        // Get the salesRepresentative
        restSalesRepresentativeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesRepresentative() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();

        // Update the salesRepresentative
        SalesRepresentative updatedSalesRepresentative = salesRepresentativeRepository.findById(salesRepresentative.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalesRepresentative are not directly saved in db
        em.detach(updatedSalesRepresentative);
        updatedSalesRepresentative
            .fullName(UPDATED_FULL_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .otherDetails(UPDATED_OTHER_DETAILS);

        restSalesRepresentativeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesRepresentative.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSalesRepresentative))
            )
            .andExpect(status().isOk());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
        SalesRepresentative testSalesRepresentative = salesRepresentativeList.get(salesRepresentativeList.size() - 1);
        assertThat(testSalesRepresentative.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testSalesRepresentative.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSalesRepresentative.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalesRepresentative.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSalesRepresentative.getOfficeLocation()).isEqualTo(UPDATED_OFFICE_LOCATION);
        assertThat(testSalesRepresentative.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testSalesRepresentative.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesRepresentative.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesRepresentativeWithPatch() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();

        // Update the salesRepresentative using partial update
        SalesRepresentative partialUpdatedSalesRepresentative = new SalesRepresentative();
        partialUpdatedSalesRepresentative.setId(salesRepresentative.getId());

        partialUpdatedSalesRepresentative.fullName(UPDATED_FULL_NAME).email(UPDATED_EMAIL).otherDetails(UPDATED_OTHER_DETAILS);

        restSalesRepresentativeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesRepresentative.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesRepresentative))
            )
            .andExpect(status().isOk());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
        SalesRepresentative testSalesRepresentative = salesRepresentativeList.get(salesRepresentativeList.size() - 1);
        assertThat(testSalesRepresentative.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testSalesRepresentative.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testSalesRepresentative.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalesRepresentative.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSalesRepresentative.getOfficeLocation()).isEqualTo(DEFAULT_OFFICE_LOCATION);
        assertThat(testSalesRepresentative.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testSalesRepresentative.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateSalesRepresentativeWithPatch() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();

        // Update the salesRepresentative using partial update
        SalesRepresentative partialUpdatedSalesRepresentative = new SalesRepresentative();
        partialUpdatedSalesRepresentative.setId(salesRepresentative.getId());

        partialUpdatedSalesRepresentative
            .fullName(UPDATED_FULL_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .otherDetails(UPDATED_OTHER_DETAILS);

        restSalesRepresentativeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesRepresentative.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesRepresentative))
            )
            .andExpect(status().isOk());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
        SalesRepresentative testSalesRepresentative = salesRepresentativeList.get(salesRepresentativeList.size() - 1);
        assertThat(testSalesRepresentative.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testSalesRepresentative.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSalesRepresentative.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalesRepresentative.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSalesRepresentative.getOfficeLocation()).isEqualTo(UPDATED_OFFICE_LOCATION);
        assertThat(testSalesRepresentative.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testSalesRepresentative.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesRepresentative.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = salesRepresentativeRepository.findAll().size();
        salesRepresentative.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesRepresentativeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesRepresentative))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesRepresentative in the database
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesRepresentative() throws Exception {
        // Initialize the database
        salesRepresentativeRepository.saveAndFlush(salesRepresentative);

        int databaseSizeBeforeDelete = salesRepresentativeRepository.findAll().size();

        // Delete the salesRepresentative
        restSalesRepresentativeMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesRepresentative.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesRepresentative> salesRepresentativeList = salesRepresentativeRepository.findAll();
        assertThat(salesRepresentativeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
