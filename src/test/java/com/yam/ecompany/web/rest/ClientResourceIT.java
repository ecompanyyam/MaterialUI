package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.Client;
import com.yam.ecompany.domain.enumeration.ApprovalStatus;
import com.yam.ecompany.repository.ClientRepository;
import com.yam.ecompany.service.criteria.ClientCriteria;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;
    private static final Integer SMALLER_HEIGHT = 1 - 1;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;
    private static final Integer SMALLER_WIDTH = 1 - 1;

    private static final Instant DEFAULT_TAKEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TAKEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPLOADED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_SUBMITTAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_SUBMITTAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ApprovalStatus DEFAULT_APPROVAL_STATUS = ApprovalStatus.SUBMITTAL_UNDER_PREPARATION;
    private static final ApprovalStatus UPDATED_APPROVAL_STATUS = ApprovalStatus.SUBMITTED;

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_REGISTRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_REGISTRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_EXPIRY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_EXPIRY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_APPROVAL_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_APPROVAL_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .clientName(DEFAULT_CLIENT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .taken(DEFAULT_TAKEN)
            .uploaded(DEFAULT_UPLOADED)
            .dateOfSubmittal(DEFAULT_DATE_OF_SUBMITTAL)
            .approvalStatus(DEFAULT_APPROVAL_STATUS)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .dateOfRegistration(DEFAULT_DATE_OF_REGISTRATION)
            .dateOfExpiry(DEFAULT_DATE_OF_EXPIRY)
            .approvalDocument(DEFAULT_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .clientName(UPDATED_CLIENT_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .dateOfSubmittal(UPDATED_DATE_OF_SUBMITTAL)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .dateOfRegistration(UPDATED_DATE_OF_REGISTRATION)
            .dateOfExpiry(UPDATED_DATE_OF_EXPIRY)
            .approvalDocument(UPDATED_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClient.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testClient.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testClient.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testClient.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testClient.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testClient.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testClient.getDateOfSubmittal()).isEqualTo(DEFAULT_DATE_OF_SUBMITTAL);
        assertThat(testClient.getApprovalStatus()).isEqualTo(DEFAULT_APPROVAL_STATUS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testClient.getDateOfRegistration()).isEqualTo(DEFAULT_DATE_OF_REGISTRATION);
        assertThat(testClient.getDateOfExpiry()).isEqualTo(DEFAULT_DATE_OF_EXPIRY);
        assertThat(testClient.getApprovalDocument()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT);
        assertThat(testClient.getApprovalDocumentContentType()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].dateOfSubmittal").value(hasItem(DEFAULT_DATE_OF_SUBMITTAL.toString())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfRegistration").value(hasItem(DEFAULT_DATE_OF_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].dateOfExpiry").value(hasItem(DEFAULT_DATE_OF_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].approvalDocumentContentType").value(hasItem(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT))));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.taken").value(DEFAULT_TAKEN.toString()))
            .andExpect(jsonPath("$.uploaded").value(DEFAULT_UPLOADED.toString()))
            .andExpect(jsonPath("$.dateOfSubmittal").value(DEFAULT_DATE_OF_SUBMITTAL.toString()))
            .andExpect(jsonPath("$.approvalStatus").value(DEFAULT_APPROVAL_STATUS.toString()))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.dateOfRegistration").value(DEFAULT_DATE_OF_REGISTRATION.toString()))
            .andExpect(jsonPath("$.dateOfExpiry").value(DEFAULT_DATE_OF_EXPIRY.toString()))
            .andExpect(jsonPath("$.approvalDocumentContentType").value(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.approvalDocument").value(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT)));
    }

    @Test
    @Transactional
    void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        Long id = client.getId();

        defaultClientShouldBeFound("id.equals=" + id);
        defaultClientShouldNotBeFound("id.notEquals=" + id);

        defaultClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.greaterThan=" + id);

        defaultClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientsByClientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName equals to DEFAULT_CLIENT_NAME
        defaultClientShouldBeFound("clientName.equals=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.equals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByClientNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName in DEFAULT_CLIENT_NAME or UPDATED_CLIENT_NAME
        defaultClientShouldBeFound("clientName.in=" + DEFAULT_CLIENT_NAME + "," + UPDATED_CLIENT_NAME);

        // Get all the clientList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.in=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByClientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName is not null
        defaultClientShouldBeFound("clientName.specified=true");

        // Get all the clientList where clientName is null
        defaultClientShouldNotBeFound("clientName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByClientNameContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName contains DEFAULT_CLIENT_NAME
        defaultClientShouldBeFound("clientName.contains=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName contains UPDATED_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.contains=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByClientNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where clientName does not contain DEFAULT_CLIENT_NAME
        defaultClientShouldNotBeFound("clientName.doesNotContain=" + DEFAULT_CLIENT_NAME);

        // Get all the clientList where clientName does not contain UPDATED_CLIENT_NAME
        defaultClientShouldBeFound("clientName.doesNotContain=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height equals to DEFAULT_HEIGHT
        defaultClientShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the clientList where height equals to UPDATED_HEIGHT
        defaultClientShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultClientShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the clientList where height equals to UPDATED_HEIGHT
        defaultClientShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height is not null
        defaultClientShouldBeFound("height.specified=true");

        // Get all the clientList where height is null
        defaultClientShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height is greater than or equal to DEFAULT_HEIGHT
        defaultClientShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the clientList where height is greater than or equal to UPDATED_HEIGHT
        defaultClientShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height is less than or equal to DEFAULT_HEIGHT
        defaultClientShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the clientList where height is less than or equal to SMALLER_HEIGHT
        defaultClientShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height is less than DEFAULT_HEIGHT
        defaultClientShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the clientList where height is less than UPDATED_HEIGHT
        defaultClientShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where height is greater than DEFAULT_HEIGHT
        defaultClientShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the clientList where height is greater than SMALLER_HEIGHT
        defaultClientShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width equals to DEFAULT_WIDTH
        defaultClientShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the clientList where width equals to UPDATED_WIDTH
        defaultClientShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultClientShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the clientList where width equals to UPDATED_WIDTH
        defaultClientShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width is not null
        defaultClientShouldBeFound("width.specified=true");

        // Get all the clientList where width is null
        defaultClientShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width is greater than or equal to DEFAULT_WIDTH
        defaultClientShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the clientList where width is greater than or equal to UPDATED_WIDTH
        defaultClientShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width is less than or equal to DEFAULT_WIDTH
        defaultClientShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the clientList where width is less than or equal to SMALLER_WIDTH
        defaultClientShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width is less than DEFAULT_WIDTH
        defaultClientShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the clientList where width is less than UPDATED_WIDTH
        defaultClientShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where width is greater than DEFAULT_WIDTH
        defaultClientShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the clientList where width is greater than SMALLER_WIDTH
        defaultClientShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllClientsByTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where taken equals to DEFAULT_TAKEN
        defaultClientShouldBeFound("taken.equals=" + DEFAULT_TAKEN);

        // Get all the clientList where taken equals to UPDATED_TAKEN
        defaultClientShouldNotBeFound("taken.equals=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllClientsByTakenIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where taken in DEFAULT_TAKEN or UPDATED_TAKEN
        defaultClientShouldBeFound("taken.in=" + DEFAULT_TAKEN + "," + UPDATED_TAKEN);

        // Get all the clientList where taken equals to UPDATED_TAKEN
        defaultClientShouldNotBeFound("taken.in=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllClientsByTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where taken is not null
        defaultClientShouldBeFound("taken.specified=true");

        // Get all the clientList where taken is null
        defaultClientShouldNotBeFound("taken.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByUploadedIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where uploaded equals to DEFAULT_UPLOADED
        defaultClientShouldBeFound("uploaded.equals=" + DEFAULT_UPLOADED);

        // Get all the clientList where uploaded equals to UPDATED_UPLOADED
        defaultClientShouldNotBeFound("uploaded.equals=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllClientsByUploadedIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where uploaded in DEFAULT_UPLOADED or UPDATED_UPLOADED
        defaultClientShouldBeFound("uploaded.in=" + DEFAULT_UPLOADED + "," + UPDATED_UPLOADED);

        // Get all the clientList where uploaded equals to UPDATED_UPLOADED
        defaultClientShouldNotBeFound("uploaded.in=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllClientsByUploadedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where uploaded is not null
        defaultClientShouldBeFound("uploaded.specified=true");

        // Get all the clientList where uploaded is null
        defaultClientShouldNotBeFound("uploaded.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByDateOfSubmittalIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfSubmittal equals to DEFAULT_DATE_OF_SUBMITTAL
        defaultClientShouldBeFound("dateOfSubmittal.equals=" + DEFAULT_DATE_OF_SUBMITTAL);

        // Get all the clientList where dateOfSubmittal equals to UPDATED_DATE_OF_SUBMITTAL
        defaultClientShouldNotBeFound("dateOfSubmittal.equals=" + UPDATED_DATE_OF_SUBMITTAL);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfSubmittalIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfSubmittal in DEFAULT_DATE_OF_SUBMITTAL or UPDATED_DATE_OF_SUBMITTAL
        defaultClientShouldBeFound("dateOfSubmittal.in=" + DEFAULT_DATE_OF_SUBMITTAL + "," + UPDATED_DATE_OF_SUBMITTAL);

        // Get all the clientList where dateOfSubmittal equals to UPDATED_DATE_OF_SUBMITTAL
        defaultClientShouldNotBeFound("dateOfSubmittal.in=" + UPDATED_DATE_OF_SUBMITTAL);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfSubmittalIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfSubmittal is not null
        defaultClientShouldBeFound("dateOfSubmittal.specified=true");

        // Get all the clientList where dateOfSubmittal is null
        defaultClientShouldNotBeFound("dateOfSubmittal.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByApprovalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where approvalStatus equals to DEFAULT_APPROVAL_STATUS
        defaultClientShouldBeFound("approvalStatus.equals=" + DEFAULT_APPROVAL_STATUS);

        // Get all the clientList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultClientShouldNotBeFound("approvalStatus.equals=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllClientsByApprovalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where approvalStatus in DEFAULT_APPROVAL_STATUS or UPDATED_APPROVAL_STATUS
        defaultClientShouldBeFound("approvalStatus.in=" + DEFAULT_APPROVAL_STATUS + "," + UPDATED_APPROVAL_STATUS);

        // Get all the clientList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultClientShouldNotBeFound("approvalStatus.in=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllClientsByApprovalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where approvalStatus is not null
        defaultClientShouldBeFound("approvalStatus.specified=true");

        // Get all the clientList where approvalStatus is null
        defaultClientShouldNotBeFound("approvalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByRegistrationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where registrationNumber equals to DEFAULT_REGISTRATION_NUMBER
        defaultClientShouldBeFound("registrationNumber.equals=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the clientList where registrationNumber equals to UPDATED_REGISTRATION_NUMBER
        defaultClientShouldNotBeFound("registrationNumber.equals=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByRegistrationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where registrationNumber in DEFAULT_REGISTRATION_NUMBER or UPDATED_REGISTRATION_NUMBER
        defaultClientShouldBeFound("registrationNumber.in=" + DEFAULT_REGISTRATION_NUMBER + "," + UPDATED_REGISTRATION_NUMBER);

        // Get all the clientList where registrationNumber equals to UPDATED_REGISTRATION_NUMBER
        defaultClientShouldNotBeFound("registrationNumber.in=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByRegistrationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where registrationNumber is not null
        defaultClientShouldBeFound("registrationNumber.specified=true");

        // Get all the clientList where registrationNumber is null
        defaultClientShouldNotBeFound("registrationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByRegistrationNumberContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where registrationNumber contains DEFAULT_REGISTRATION_NUMBER
        defaultClientShouldBeFound("registrationNumber.contains=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the clientList where registrationNumber contains UPDATED_REGISTRATION_NUMBER
        defaultClientShouldNotBeFound("registrationNumber.contains=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByRegistrationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where registrationNumber does not contain DEFAULT_REGISTRATION_NUMBER
        defaultClientShouldNotBeFound("registrationNumber.doesNotContain=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the clientList where registrationNumber does not contain UPDATED_REGISTRATION_NUMBER
        defaultClientShouldBeFound("registrationNumber.doesNotContain=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfRegistration equals to DEFAULT_DATE_OF_REGISTRATION
        defaultClientShouldBeFound("dateOfRegistration.equals=" + DEFAULT_DATE_OF_REGISTRATION);

        // Get all the clientList where dateOfRegistration equals to UPDATED_DATE_OF_REGISTRATION
        defaultClientShouldNotBeFound("dateOfRegistration.equals=" + UPDATED_DATE_OF_REGISTRATION);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfRegistrationIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfRegistration in DEFAULT_DATE_OF_REGISTRATION or UPDATED_DATE_OF_REGISTRATION
        defaultClientShouldBeFound("dateOfRegistration.in=" + DEFAULT_DATE_OF_REGISTRATION + "," + UPDATED_DATE_OF_REGISTRATION);

        // Get all the clientList where dateOfRegistration equals to UPDATED_DATE_OF_REGISTRATION
        defaultClientShouldNotBeFound("dateOfRegistration.in=" + UPDATED_DATE_OF_REGISTRATION);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfRegistrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfRegistration is not null
        defaultClientShouldBeFound("dateOfRegistration.specified=true");

        // Get all the clientList where dateOfRegistration is null
        defaultClientShouldNotBeFound("dateOfRegistration.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByDateOfExpiryIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfExpiry equals to DEFAULT_DATE_OF_EXPIRY
        defaultClientShouldBeFound("dateOfExpiry.equals=" + DEFAULT_DATE_OF_EXPIRY);

        // Get all the clientList where dateOfExpiry equals to UPDATED_DATE_OF_EXPIRY
        defaultClientShouldNotBeFound("dateOfExpiry.equals=" + UPDATED_DATE_OF_EXPIRY);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfExpiryIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfExpiry in DEFAULT_DATE_OF_EXPIRY or UPDATED_DATE_OF_EXPIRY
        defaultClientShouldBeFound("dateOfExpiry.in=" + DEFAULT_DATE_OF_EXPIRY + "," + UPDATED_DATE_OF_EXPIRY);

        // Get all the clientList where dateOfExpiry equals to UPDATED_DATE_OF_EXPIRY
        defaultClientShouldNotBeFound("dateOfExpiry.in=" + UPDATED_DATE_OF_EXPIRY);
    }

    @Test
    @Transactional
    void getAllClientsByDateOfExpiryIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where dateOfExpiry is not null
        defaultClientShouldBeFound("dateOfExpiry.specified=true");

        // Get all the clientList where dateOfExpiry is null
        defaultClientShouldNotBeFound("dateOfExpiry.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].dateOfSubmittal").value(hasItem(DEFAULT_DATE_OF_SUBMITTAL.toString())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfRegistration").value(hasItem(DEFAULT_DATE_OF_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].dateOfExpiry").value(hasItem(DEFAULT_DATE_OF_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].approvalDocumentContentType").value(hasItem(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT))));

        // Check, that the count call also returns 1
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .clientName(UPDATED_CLIENT_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .dateOfSubmittal(UPDATED_DATE_OF_SUBMITTAL)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .dateOfRegistration(UPDATED_DATE_OF_REGISTRATION)
            .dateOfExpiry(UPDATED_DATE_OF_EXPIRY)
            .approvalDocument(UPDATED_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testClient.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testClient.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testClient.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testClient.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testClient.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testClient.getDateOfSubmittal()).isEqualTo(UPDATED_DATE_OF_SUBMITTAL);
        assertThat(testClient.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getDateOfRegistration()).isEqualTo(UPDATED_DATE_OF_REGISTRATION);
        assertThat(testClient.getDateOfExpiry()).isEqualTo(UPDATED_DATE_OF_EXPIRY);
        assertThat(testClient.getApprovalDocument()).isEqualTo(UPDATED_APPROVAL_DOCUMENT);
        assertThat(testClient.getApprovalDocumentContentType()).isEqualTo(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .dateOfSubmittal(UPDATED_DATE_OF_SUBMITTAL)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .dateOfRegistration(UPDATED_DATE_OF_REGISTRATION);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClient.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testClient.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testClient.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testClient.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testClient.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testClient.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testClient.getDateOfSubmittal()).isEqualTo(UPDATED_DATE_OF_SUBMITTAL);
        assertThat(testClient.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getDateOfRegistration()).isEqualTo(UPDATED_DATE_OF_REGISTRATION);
        assertThat(testClient.getDateOfExpiry()).isEqualTo(DEFAULT_DATE_OF_EXPIRY);
        assertThat(testClient.getApprovalDocument()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT);
        assertThat(testClient.getApprovalDocumentContentType()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .clientName(UPDATED_CLIENT_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .dateOfSubmittal(UPDATED_DATE_OF_SUBMITTAL)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .dateOfRegistration(UPDATED_DATE_OF_REGISTRATION)
            .dateOfExpiry(UPDATED_DATE_OF_EXPIRY)
            .approvalDocument(UPDATED_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testClient.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testClient.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testClient.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testClient.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testClient.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testClient.getDateOfSubmittal()).isEqualTo(UPDATED_DATE_OF_SUBMITTAL);
        assertThat(testClient.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getDateOfRegistration()).isEqualTo(UPDATED_DATE_OF_REGISTRATION);
        assertThat(testClient.getDateOfExpiry()).isEqualTo(UPDATED_DATE_OF_EXPIRY);
        assertThat(testClient.getApprovalDocument()).isEqualTo(UPDATED_APPROVAL_DOCUMENT);
        assertThat(testClient.getApprovalDocumentContentType()).isEqualTo(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
