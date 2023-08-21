package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.FeedBackFromEmployee;
import com.yam.ecompany.domain.Product;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.domain.enumeration.FeedBackCategory;
import com.yam.ecompany.repository.FeedBackFromEmployeeRepository;
import com.yam.ecompany.service.FeedBackFromEmployeeService;
import com.yam.ecompany.service.criteria.FeedBackFromEmployeeCriteria;
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

/**
 * Integration tests for the {@link FeedBackFromEmployeeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FeedBackFromEmployeeResourceIT {

    private static final String DEFAULT_REF_CONTRACT_PO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REF_CONTRACT_PO_NUMBER = "BBBBBBBBBB";

    private static final FeedBackCategory DEFAULT_FEED_BACK_CATEGORY = FeedBackCategory.ADVICE;
    private static final FeedBackCategory UPDATED_FEED_BACK_CATEGORY = FeedBackCategory.COMPLAIN;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/feed-back-from-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedBackFromEmployeeRepository feedBackFromEmployeeRepository;

    @Mock
    private FeedBackFromEmployeeRepository feedBackFromEmployeeRepositoryMock;

    @Mock
    private FeedBackFromEmployeeService feedBackFromEmployeeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedBackFromEmployeeMockMvc;

    private FeedBackFromEmployee feedBackFromEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackFromEmployee createEntity(EntityManager em) {
        FeedBackFromEmployee feedBackFromEmployee = new FeedBackFromEmployee()
            .refContractPONumber(DEFAULT_REF_CONTRACT_PO_NUMBER)
            .feedBackCategory(DEFAULT_FEED_BACK_CATEGORY)
            .comment(DEFAULT_COMMENT);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        feedBackFromEmployee.setVendorsName(vendor);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        feedBackFromEmployee.setProductName(product);
        return feedBackFromEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBackFromEmployee createUpdatedEntity(EntityManager em) {
        FeedBackFromEmployee feedBackFromEmployee = new FeedBackFromEmployee()
            .refContractPONumber(UPDATED_REF_CONTRACT_PO_NUMBER)
            .feedBackCategory(UPDATED_FEED_BACK_CATEGORY)
            .comment(UPDATED_COMMENT);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createUpdatedEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        feedBackFromEmployee.setVendorsName(vendor);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        feedBackFromEmployee.setProductName(product);
        return feedBackFromEmployee;
    }

    @BeforeEach
    public void initTest() {
        feedBackFromEmployee = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeCreate = feedBackFromEmployeeRepository.findAll().size();
        // Create the FeedBackFromEmployee
        restFeedBackFromEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isCreated());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        FeedBackFromEmployee testFeedBackFromEmployee = feedBackFromEmployeeList.get(feedBackFromEmployeeList.size() - 1);
        assertThat(testFeedBackFromEmployee.getRefContractPONumber()).isEqualTo(DEFAULT_REF_CONTRACT_PO_NUMBER);
        assertThat(testFeedBackFromEmployee.getFeedBackCategory()).isEqualTo(DEFAULT_FEED_BACK_CATEGORY);
        assertThat(testFeedBackFromEmployee.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createFeedBackFromEmployeeWithExistingId() throws Exception {
        // Create the FeedBackFromEmployee with an existing ID
        feedBackFromEmployee.setId(1L);

        int databaseSizeBeforeCreate = feedBackFromEmployeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackFromEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployees() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBackFromEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].refContractPONumber").value(hasItem(DEFAULT_REF_CONTRACT_PO_NUMBER)))
            .andExpect(jsonPath("$.[*].feedBackCategory").value(hasItem(DEFAULT_FEED_BACK_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackFromEmployeesWithEagerRelationshipsIsEnabled() throws Exception {
        when(feedBackFromEmployeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackFromEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(feedBackFromEmployeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeedBackFromEmployeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(feedBackFromEmployeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeedBackFromEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(feedBackFromEmployeeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFeedBackFromEmployee() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get the feedBackFromEmployee
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, feedBackFromEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedBackFromEmployee.getId().intValue()))
            .andExpect(jsonPath("$.refContractPONumber").value(DEFAULT_REF_CONTRACT_PO_NUMBER))
            .andExpect(jsonPath("$.feedBackCategory").value(DEFAULT_FEED_BACK_CATEGORY.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    void getFeedBackFromEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        Long id = feedBackFromEmployee.getId();

        defaultFeedBackFromEmployeeShouldBeFound("id.equals=" + id);
        defaultFeedBackFromEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultFeedBackFromEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedBackFromEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedBackFromEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedBackFromEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByRefContractPONumberIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where refContractPONumber equals to DEFAULT_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldBeFound("refContractPONumber.equals=" + DEFAULT_REF_CONTRACT_PO_NUMBER);

        // Get all the feedBackFromEmployeeList where refContractPONumber equals to UPDATED_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldNotBeFound("refContractPONumber.equals=" + UPDATED_REF_CONTRACT_PO_NUMBER);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByRefContractPONumberIsInShouldWork() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where refContractPONumber in DEFAULT_REF_CONTRACT_PO_NUMBER or UPDATED_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldBeFound(
            "refContractPONumber.in=" + DEFAULT_REF_CONTRACT_PO_NUMBER + "," + UPDATED_REF_CONTRACT_PO_NUMBER
        );

        // Get all the feedBackFromEmployeeList where refContractPONumber equals to UPDATED_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldNotBeFound("refContractPONumber.in=" + UPDATED_REF_CONTRACT_PO_NUMBER);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByRefContractPONumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where refContractPONumber is not null
        defaultFeedBackFromEmployeeShouldBeFound("refContractPONumber.specified=true");

        // Get all the feedBackFromEmployeeList where refContractPONumber is null
        defaultFeedBackFromEmployeeShouldNotBeFound("refContractPONumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByRefContractPONumberContainsSomething() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where refContractPONumber contains DEFAULT_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldBeFound("refContractPONumber.contains=" + DEFAULT_REF_CONTRACT_PO_NUMBER);

        // Get all the feedBackFromEmployeeList where refContractPONumber contains UPDATED_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldNotBeFound("refContractPONumber.contains=" + UPDATED_REF_CONTRACT_PO_NUMBER);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByRefContractPONumberNotContainsSomething() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where refContractPONumber does not contain DEFAULT_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldNotBeFound("refContractPONumber.doesNotContain=" + DEFAULT_REF_CONTRACT_PO_NUMBER);

        // Get all the feedBackFromEmployeeList where refContractPONumber does not contain UPDATED_REF_CONTRACT_PO_NUMBER
        defaultFeedBackFromEmployeeShouldBeFound("refContractPONumber.doesNotContain=" + UPDATED_REF_CONTRACT_PO_NUMBER);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByFeedBackCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where feedBackCategory equals to DEFAULT_FEED_BACK_CATEGORY
        defaultFeedBackFromEmployeeShouldBeFound("feedBackCategory.equals=" + DEFAULT_FEED_BACK_CATEGORY);

        // Get all the feedBackFromEmployeeList where feedBackCategory equals to UPDATED_FEED_BACK_CATEGORY
        defaultFeedBackFromEmployeeShouldNotBeFound("feedBackCategory.equals=" + UPDATED_FEED_BACK_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByFeedBackCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where feedBackCategory in DEFAULT_FEED_BACK_CATEGORY or UPDATED_FEED_BACK_CATEGORY
        defaultFeedBackFromEmployeeShouldBeFound("feedBackCategory.in=" + DEFAULT_FEED_BACK_CATEGORY + "," + UPDATED_FEED_BACK_CATEGORY);

        // Get all the feedBackFromEmployeeList where feedBackCategory equals to UPDATED_FEED_BACK_CATEGORY
        defaultFeedBackFromEmployeeShouldNotBeFound("feedBackCategory.in=" + UPDATED_FEED_BACK_CATEGORY);
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByFeedBackCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        // Get all the feedBackFromEmployeeList where feedBackCategory is not null
        defaultFeedBackFromEmployeeShouldBeFound("feedBackCategory.specified=true");

        // Get all the feedBackFromEmployeeList where feedBackCategory is null
        defaultFeedBackFromEmployeeShouldNotBeFound("feedBackCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByVendorsNameIsEqualToSomething() throws Exception {
        Vendor vendorsName;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);
            vendorsName = VendorResourceIT.createEntity(em);
        } else {
            vendorsName = TestUtil.findAll(em, Vendor.class).get(0);
        }
        em.persist(vendorsName);
        em.flush();
        feedBackFromEmployee.setVendorsName(vendorsName);
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);
        Long vendorsNameId = vendorsName.getId();
        // Get all the feedBackFromEmployeeList where vendorsName equals to vendorsNameId
        defaultFeedBackFromEmployeeShouldBeFound("vendorsNameId.equals=" + vendorsNameId);

        // Get all the feedBackFromEmployeeList where vendorsName equals to (vendorsNameId + 1)
        defaultFeedBackFromEmployeeShouldNotBeFound("vendorsNameId.equals=" + (vendorsNameId + 1));
    }

    @Test
    @Transactional
    void getAllFeedBackFromEmployeesByProductNameIsEqualToSomething() throws Exception {
        Product productName;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);
            productName = ProductResourceIT.createEntity(em);
        } else {
            productName = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(productName);
        em.flush();
        feedBackFromEmployee.setProductName(productName);
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);
        Long productNameId = productName.getId();
        // Get all the feedBackFromEmployeeList where productName equals to productNameId
        defaultFeedBackFromEmployeeShouldBeFound("productNameId.equals=" + productNameId);

        // Get all the feedBackFromEmployeeList where productName equals to (productNameId + 1)
        defaultFeedBackFromEmployeeShouldNotBeFound("productNameId.equals=" + (productNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedBackFromEmployeeShouldBeFound(String filter) throws Exception {
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBackFromEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].refContractPONumber").value(hasItem(DEFAULT_REF_CONTRACT_PO_NUMBER)))
            .andExpect(jsonPath("$.[*].feedBackCategory").value(hasItem(DEFAULT_FEED_BACK_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));

        // Check, that the count call also returns 1
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedBackFromEmployeeShouldNotBeFound(String filter) throws Exception {
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedBackFromEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedBackFromEmployee() throws Exception {
        // Get the feedBackFromEmployee
        restFeedBackFromEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedBackFromEmployee() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();

        // Update the feedBackFromEmployee
        FeedBackFromEmployee updatedFeedBackFromEmployee = feedBackFromEmployeeRepository
            .findById(feedBackFromEmployee.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFeedBackFromEmployee are not directly saved in db
        em.detach(updatedFeedBackFromEmployee);
        updatedFeedBackFromEmployee
            .refContractPONumber(UPDATED_REF_CONTRACT_PO_NUMBER)
            .feedBackCategory(UPDATED_FEED_BACK_CATEGORY)
            .comment(UPDATED_COMMENT);

        restFeedBackFromEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedBackFromEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedBackFromEmployee))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FeedBackFromEmployee testFeedBackFromEmployee = feedBackFromEmployeeList.get(feedBackFromEmployeeList.size() - 1);
        assertThat(testFeedBackFromEmployee.getRefContractPONumber()).isEqualTo(UPDATED_REF_CONTRACT_PO_NUMBER);
        assertThat(testFeedBackFromEmployee.getFeedBackCategory()).isEqualTo(UPDATED_FEED_BACK_CATEGORY);
        assertThat(testFeedBackFromEmployee.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedBackFromEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedBackFromEmployeeWithPatch() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();

        // Update the feedBackFromEmployee using partial update
        FeedBackFromEmployee partialUpdatedFeedBackFromEmployee = new FeedBackFromEmployee();
        partialUpdatedFeedBackFromEmployee.setId(feedBackFromEmployee.getId());

        partialUpdatedFeedBackFromEmployee
            .refContractPONumber(UPDATED_REF_CONTRACT_PO_NUMBER)
            .feedBackCategory(UPDATED_FEED_BACK_CATEGORY)
            .comment(UPDATED_COMMENT);

        restFeedBackFromEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackFromEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedBackFromEmployee))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FeedBackFromEmployee testFeedBackFromEmployee = feedBackFromEmployeeList.get(feedBackFromEmployeeList.size() - 1);
        assertThat(testFeedBackFromEmployee.getRefContractPONumber()).isEqualTo(UPDATED_REF_CONTRACT_PO_NUMBER);
        assertThat(testFeedBackFromEmployee.getFeedBackCategory()).isEqualTo(UPDATED_FEED_BACK_CATEGORY);
        assertThat(testFeedBackFromEmployee.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateFeedBackFromEmployeeWithPatch() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();

        // Update the feedBackFromEmployee using partial update
        FeedBackFromEmployee partialUpdatedFeedBackFromEmployee = new FeedBackFromEmployee();
        partialUpdatedFeedBackFromEmployee.setId(feedBackFromEmployee.getId());

        partialUpdatedFeedBackFromEmployee
            .refContractPONumber(UPDATED_REF_CONTRACT_PO_NUMBER)
            .feedBackCategory(UPDATED_FEED_BACK_CATEGORY)
            .comment(UPDATED_COMMENT);

        restFeedBackFromEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedBackFromEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedBackFromEmployee))
            )
            .andExpect(status().isOk());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FeedBackFromEmployee testFeedBackFromEmployee = feedBackFromEmployeeList.get(feedBackFromEmployeeList.size() - 1);
        assertThat(testFeedBackFromEmployee.getRefContractPONumber()).isEqualTo(UPDATED_REF_CONTRACT_PO_NUMBER);
        assertThat(testFeedBackFromEmployee.getFeedBackCategory()).isEqualTo(UPDATED_FEED_BACK_CATEGORY);
        assertThat(testFeedBackFromEmployee.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedBackFromEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedBackFromEmployee() throws Exception {
        int databaseSizeBeforeUpdate = feedBackFromEmployeeRepository.findAll().size();
        feedBackFromEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedBackFromEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedBackFromEmployee))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedBackFromEmployee in the database
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedBackFromEmployee() throws Exception {
        // Initialize the database
        feedBackFromEmployeeRepository.saveAndFlush(feedBackFromEmployee);

        int databaseSizeBeforeDelete = feedBackFromEmployeeRepository.findAll().size();

        // Delete the feedBackFromEmployee
        restFeedBackFromEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedBackFromEmployee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedBackFromEmployee> feedBackFromEmployeeList = feedBackFromEmployeeRepository.findAll();
        assertThat(feedBackFromEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
