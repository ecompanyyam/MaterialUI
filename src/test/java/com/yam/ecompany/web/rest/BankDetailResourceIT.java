package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.BankDetail;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.repository.BankDetailRepository;
import com.yam.ecompany.service.BankDetailService;
import com.yam.ecompany.service.criteria.BankDetailCriteria;
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
 * Integration tests for the {@link BankDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BankDetailResourceIT {

    private static final Boolean DEFAULT_BANK_ACCOUNT = false;
    private static final Boolean UPDATED_BANK_ACCOUNT = true;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_IBAN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankDetailRepository bankDetailRepository;

    @Mock
    private BankDetailRepository bankDetailRepositoryMock;

    @Mock
    private BankDetailService bankDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankDetailMockMvc;

    private BankDetail bankDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetail createEntity(EntityManager em) {
        BankDetail bankDetail = new BankDetail()
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .branchSwiftCode(DEFAULT_BRANCH_SWIFT_CODE)
            .ibanNo(DEFAULT_IBAN_NO)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        bankDetail.setVendorsName(vendor);
        return bankDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetail createUpdatedEntity(EntityManager em) {
        BankDetail bankDetail = new BankDetail()
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .branchSwiftCode(UPDATED_BRANCH_SWIFT_CODE)
            .ibanNo(UPDATED_IBAN_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createUpdatedEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        bankDetail.setVendorsName(vendor);
        return bankDetail;
    }

    @BeforeEach
    public void initTest() {
        bankDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createBankDetail() throws Exception {
        int databaseSizeBeforeCreate = bankDetailRepository.findAll().size();
        // Create the BankDetail
        restBankDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetail)))
            .andExpect(status().isCreated());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeCreate + 1);
        BankDetail testBankDetail = bankDetailList.get(bankDetailList.size() - 1);
        assertThat(testBankDetail.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testBankDetail.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankDetail.getBranchSwiftCode()).isEqualTo(DEFAULT_BRANCH_SWIFT_CODE);
        assertThat(testBankDetail.getIbanNo()).isEqualTo(DEFAULT_IBAN_NO);
        assertThat(testBankDetail.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void createBankDetailWithExistingId() throws Exception {
        // Create the BankDetail with an existing ID
        bankDetail.setId(1L);

        int databaseSizeBeforeCreate = bankDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetail)))
            .andExpect(status().isBadRequest());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankDetails() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchSwiftCode").value(hasItem(DEFAULT_BRANCH_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanNo").value(hasItem(DEFAULT_IBAN_NO)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBankDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(bankDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBankDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bankDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBankDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bankDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBankDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bankDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBankDetail() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get the bankDetail
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, bankDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankDetail.getId().intValue()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchSwiftCode").value(DEFAULT_BRANCH_SWIFT_CODE))
            .andExpect(jsonPath("$.ibanNo").value(DEFAULT_IBAN_NO))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER));
    }

    @Test
    @Transactional
    void getBankDetailsByIdFiltering() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        Long id = bankDetail.getId();

        defaultBankDetailShouldBeFound("id.equals=" + id);
        defaultBankDetailShouldNotBeFound("id.notEquals=" + id);

        defaultBankDetailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankDetailShouldNotBeFound("id.greaterThan=" + id);

        defaultBankDetailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankDetailShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultBankDetailShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the bankDetailList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultBankDetailShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultBankDetailShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the bankDetailList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultBankDetailShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankAccount is not null
        defaultBankDetailShouldBeFound("bankAccount.specified=true");

        // Get all the bankDetailList where bankAccount is null
        defaultBankDetailShouldNotBeFound("bankAccount.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankName equals to DEFAULT_BANK_NAME
        defaultBankDetailShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the bankDetailList where bankName equals to UPDATED_BANK_NAME
        defaultBankDetailShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultBankDetailShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the bankDetailList where bankName equals to UPDATED_BANK_NAME
        defaultBankDetailShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankName is not null
        defaultBankDetailShouldBeFound("bankName.specified=true");

        // Get all the bankDetailList where bankName is null
        defaultBankDetailShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankName contains DEFAULT_BANK_NAME
        defaultBankDetailShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the bankDetailList where bankName contains UPDATED_BANK_NAME
        defaultBankDetailShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where bankName does not contain DEFAULT_BANK_NAME
        defaultBankDetailShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the bankDetailList where bankName does not contain UPDATED_BANK_NAME
        defaultBankDetailShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchSwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where branchSwiftCode equals to DEFAULT_BRANCH_SWIFT_CODE
        defaultBankDetailShouldBeFound("branchSwiftCode.equals=" + DEFAULT_BRANCH_SWIFT_CODE);

        // Get all the bankDetailList where branchSwiftCode equals to UPDATED_BRANCH_SWIFT_CODE
        defaultBankDetailShouldNotBeFound("branchSwiftCode.equals=" + UPDATED_BRANCH_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchSwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where branchSwiftCode in DEFAULT_BRANCH_SWIFT_CODE or UPDATED_BRANCH_SWIFT_CODE
        defaultBankDetailShouldBeFound("branchSwiftCode.in=" + DEFAULT_BRANCH_SWIFT_CODE + "," + UPDATED_BRANCH_SWIFT_CODE);

        // Get all the bankDetailList where branchSwiftCode equals to UPDATED_BRANCH_SWIFT_CODE
        defaultBankDetailShouldNotBeFound("branchSwiftCode.in=" + UPDATED_BRANCH_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchSwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where branchSwiftCode is not null
        defaultBankDetailShouldBeFound("branchSwiftCode.specified=true");

        // Get all the bankDetailList where branchSwiftCode is null
        defaultBankDetailShouldNotBeFound("branchSwiftCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchSwiftCodeContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where branchSwiftCode contains DEFAULT_BRANCH_SWIFT_CODE
        defaultBankDetailShouldBeFound("branchSwiftCode.contains=" + DEFAULT_BRANCH_SWIFT_CODE);

        // Get all the bankDetailList where branchSwiftCode contains UPDATED_BRANCH_SWIFT_CODE
        defaultBankDetailShouldNotBeFound("branchSwiftCode.contains=" + UPDATED_BRANCH_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchSwiftCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where branchSwiftCode does not contain DEFAULT_BRANCH_SWIFT_CODE
        defaultBankDetailShouldNotBeFound("branchSwiftCode.doesNotContain=" + DEFAULT_BRANCH_SWIFT_CODE);

        // Get all the bankDetailList where branchSwiftCode does not contain UPDATED_BRANCH_SWIFT_CODE
        defaultBankDetailShouldBeFound("branchSwiftCode.doesNotContain=" + UPDATED_BRANCH_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIbanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where ibanNo equals to DEFAULT_IBAN_NO
        defaultBankDetailShouldBeFound("ibanNo.equals=" + DEFAULT_IBAN_NO);

        // Get all the bankDetailList where ibanNo equals to UPDATED_IBAN_NO
        defaultBankDetailShouldNotBeFound("ibanNo.equals=" + UPDATED_IBAN_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIbanNoIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where ibanNo in DEFAULT_IBAN_NO or UPDATED_IBAN_NO
        defaultBankDetailShouldBeFound("ibanNo.in=" + DEFAULT_IBAN_NO + "," + UPDATED_IBAN_NO);

        // Get all the bankDetailList where ibanNo equals to UPDATED_IBAN_NO
        defaultBankDetailShouldNotBeFound("ibanNo.in=" + UPDATED_IBAN_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIbanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where ibanNo is not null
        defaultBankDetailShouldBeFound("ibanNo.specified=true");

        // Get all the bankDetailList where ibanNo is null
        defaultBankDetailShouldNotBeFound("ibanNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByIbanNoContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where ibanNo contains DEFAULT_IBAN_NO
        defaultBankDetailShouldBeFound("ibanNo.contains=" + DEFAULT_IBAN_NO);

        // Get all the bankDetailList where ibanNo contains UPDATED_IBAN_NO
        defaultBankDetailShouldNotBeFound("ibanNo.contains=" + UPDATED_IBAN_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIbanNoNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where ibanNo does not contain DEFAULT_IBAN_NO
        defaultBankDetailShouldNotBeFound("ibanNo.doesNotContain=" + DEFAULT_IBAN_NO);

        // Get all the bankDetailList where ibanNo does not contain UPDATED_IBAN_NO
        defaultBankDetailShouldBeFound("ibanNo.doesNotContain=" + UPDATED_IBAN_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultBankDetailShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the bankDetailList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBankDetailShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultBankDetailShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the bankDetailList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBankDetailShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where accountNumber is not null
        defaultBankDetailShouldBeFound("accountNumber.specified=true");

        // Get all the bankDetailList where accountNumber is null
        defaultBankDetailShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultBankDetailShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the bankDetailList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultBankDetailShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        // Get all the bankDetailList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultBankDetailShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the bankDetailList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultBankDetailShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankDetailsByVendorsNameIsEqualToSomething() throws Exception {
        Vendor vendorsName;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            bankDetailRepository.saveAndFlush(bankDetail);
            vendorsName = VendorResourceIT.createEntity(em);
        } else {
            vendorsName = TestUtil.findAll(em, Vendor.class).get(0);
        }
        em.persist(vendorsName);
        em.flush();
        bankDetail.setVendorsName(vendorsName);
        bankDetailRepository.saveAndFlush(bankDetail);
        Long vendorsNameId = vendorsName.getId();
        // Get all the bankDetailList where vendorsName equals to vendorsNameId
        defaultBankDetailShouldBeFound("vendorsNameId.equals=" + vendorsNameId);

        // Get all the bankDetailList where vendorsName equals to (vendorsNameId + 1)
        defaultBankDetailShouldNotBeFound("vendorsNameId.equals=" + (vendorsNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankDetailShouldBeFound(String filter) throws Exception {
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchSwiftCode").value(hasItem(DEFAULT_BRANCH_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanNo").value(hasItem(DEFAULT_IBAN_NO)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)));

        // Check, that the count call also returns 1
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankDetailShouldNotBeFound(String filter) throws Exception {
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBankDetail() throws Exception {
        // Get the bankDetail
        restBankDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankDetail() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();

        // Update the bankDetail
        BankDetail updatedBankDetail = bankDetailRepository.findById(bankDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankDetail are not directly saved in db
        em.detach(updatedBankDetail);
        updatedBankDetail
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .branchSwiftCode(UPDATED_BRANCH_SWIFT_CODE)
            .ibanNo(UPDATED_IBAN_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER);

        restBankDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankDetail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankDetail))
            )
            .andExpect(status().isOk());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
        BankDetail testBankDetail = bankDetailList.get(bankDetailList.size() - 1);
        assertThat(testBankDetail.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testBankDetail.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankDetail.getBranchSwiftCode()).isEqualTo(UPDATED_BRANCH_SWIFT_CODE);
        assertThat(testBankDetail.getIbanNo()).isEqualTo(UPDATED_IBAN_NO);
        assertThat(testBankDetail.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDetail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetail)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankDetailWithPatch() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();

        // Update the bankDetail using partial update
        BankDetail partialUpdatedBankDetail = new BankDetail();
        partialUpdatedBankDetail.setId(bankDetail.getId());

        partialUpdatedBankDetail.accountNumber(UPDATED_ACCOUNT_NUMBER);

        restBankDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDetail))
            )
            .andExpect(status().isOk());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
        BankDetail testBankDetail = bankDetailList.get(bankDetailList.size() - 1);
        assertThat(testBankDetail.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testBankDetail.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankDetail.getBranchSwiftCode()).isEqualTo(DEFAULT_BRANCH_SWIFT_CODE);
        assertThat(testBankDetail.getIbanNo()).isEqualTo(DEFAULT_IBAN_NO);
        assertThat(testBankDetail.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateBankDetailWithPatch() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();

        // Update the bankDetail using partial update
        BankDetail partialUpdatedBankDetail = new BankDetail();
        partialUpdatedBankDetail.setId(bankDetail.getId());

        partialUpdatedBankDetail
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .branchSwiftCode(UPDATED_BRANCH_SWIFT_CODE)
            .ibanNo(UPDATED_IBAN_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER);

        restBankDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDetail))
            )
            .andExpect(status().isOk());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
        BankDetail testBankDetail = bankDetailList.get(bankDetailList.size() - 1);
        assertThat(testBankDetail.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testBankDetail.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankDetail.getBranchSwiftCode()).isEqualTo(UPDATED_BRANCH_SWIFT_CODE);
        assertThat(testBankDetail.getIbanNo()).isEqualTo(UPDATED_IBAN_NO);
        assertThat(testBankDetail.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankDetail() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailRepository.findAll().size();
        bankDetail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankDetail))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetail in the database
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankDetail() throws Exception {
        // Initialize the database
        bankDetailRepository.saveAndFlush(bankDetail);

        int databaseSizeBeforeDelete = bankDetailRepository.findAll().size();

        // Delete the bankDetail
        restBankDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankDetail> bankDetailList = bankDetailRepository.findAll();
        assertThat(bankDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
