package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.Document;
import com.yam.ecompany.domain.enumeration.DocumentStatus;
import com.yam.ecompany.domain.enumeration.DocumentType;
import com.yam.ecompany.repository.DocumentRepository;
import com.yam.ecompany.service.criteria.DocumentCriteria;
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
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.QUALITY_CERTIFICATES;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.ACCREDITATIONS;

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_ISSUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISSUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final DocumentStatus DEFAULT_DOCUMENT_STATUS = DocumentStatus.STILL_VALID;
    private static final DocumentStatus UPDATED_DOCUMENT_STATUS = DocumentStatus.EXPIRED;

    private static final byte[] DEFAULT_UPLOAD_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UPLOAD_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UPLOAD_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UPLOAD_FILE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .issueDate(DEFAULT_ISSUE_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .uploadFile(DEFAULT_UPLOAD_FILE)
            .uploadFileContentType(DEFAULT_UPLOAD_FILE_CONTENT_TYPE);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .documentType(UPDATED_DOCUMENT_TYPE)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .issueDate(UPDATED_ISSUE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .uploadFile(UPDATED_UPLOAD_FILE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocument.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testDocument.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testDocument.getUploadFile()).isEqualTo(DEFAULT_UPLOAD_FILE);
        assertThat(testDocument.getUploadFileContentType()).isEqualTo(DEFAULT_UPLOAD_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDocumentWithExistingId() throws Exception {
        // Create the Document with an existing ID
        document.setId(1L);

        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uploadFileContentType").value(hasItem(DEFAULT_UPLOAD_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD_FILE))));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS.toString()))
            .andExpect(jsonPath("$.uploadFileContentType").value(DEFAULT_UPLOAD_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadFile").value(Base64Utils.encodeToString(DEFAULT_UPLOAD_FILE)));
    }

    @Test
    @Transactional
    void getDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        Long id = document.getId();

        defaultDocumentShouldBeFound("id.equals=" + id);
        defaultDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultDocumentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the documentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the documentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType is not null
        defaultDocumentShouldBeFound("documentType.specified=true");

        // Get all the documentList where documentType is null
        defaultDocumentShouldNotBeFound("documentType.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByOrganizationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where organizationName equals to DEFAULT_ORGANIZATION_NAME
        defaultDocumentShouldBeFound("organizationName.equals=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the documentList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultDocumentShouldNotBeFound("organizationName.equals=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByOrganizationNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where organizationName in DEFAULT_ORGANIZATION_NAME or UPDATED_ORGANIZATION_NAME
        defaultDocumentShouldBeFound("organizationName.in=" + DEFAULT_ORGANIZATION_NAME + "," + UPDATED_ORGANIZATION_NAME);

        // Get all the documentList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultDocumentShouldNotBeFound("organizationName.in=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByOrganizationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where organizationName is not null
        defaultDocumentShouldBeFound("organizationName.specified=true");

        // Get all the documentList where organizationName is null
        defaultDocumentShouldNotBeFound("organizationName.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByOrganizationNameContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where organizationName contains DEFAULT_ORGANIZATION_NAME
        defaultDocumentShouldBeFound("organizationName.contains=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the documentList where organizationName contains UPDATED_ORGANIZATION_NAME
        defaultDocumentShouldNotBeFound("organizationName.contains=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByOrganizationNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where organizationName does not contain DEFAULT_ORGANIZATION_NAME
        defaultDocumentShouldNotBeFound("organizationName.doesNotContain=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the documentList where organizationName does not contain UPDATED_ORGANIZATION_NAME
        defaultDocumentShouldBeFound("organizationName.doesNotContain=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the documentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the documentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentName is not null
        defaultDocumentShouldBeFound("documentName.specified=true");

        // Get all the documentList where documentName is null
        defaultDocumentShouldNotBeFound("documentName.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultDocumentShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the documentList where documentName contains UPDATED_DOCUMENT_NAME
        defaultDocumentShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultDocumentShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the documentList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultDocumentShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentNumber equals to DEFAULT_DOCUMENT_NUMBER
        defaultDocumentShouldBeFound("documentNumber.equals=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the documentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultDocumentShouldNotBeFound("documentNumber.equals=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentNumber in DEFAULT_DOCUMENT_NUMBER or UPDATED_DOCUMENT_NUMBER
        defaultDocumentShouldBeFound("documentNumber.in=" + DEFAULT_DOCUMENT_NUMBER + "," + UPDATED_DOCUMENT_NUMBER);

        // Get all the documentList where documentNumber equals to UPDATED_DOCUMENT_NUMBER
        defaultDocumentShouldNotBeFound("documentNumber.in=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentNumber is not null
        defaultDocumentShouldBeFound("documentNumber.specified=true");

        // Get all the documentList where documentNumber is null
        defaultDocumentShouldNotBeFound("documentNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNumberContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentNumber contains DEFAULT_DOCUMENT_NUMBER
        defaultDocumentShouldBeFound("documentNumber.contains=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the documentList where documentNumber contains UPDATED_DOCUMENT_NUMBER
        defaultDocumentShouldNotBeFound("documentNumber.contains=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentNumberNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentNumber does not contain DEFAULT_DOCUMENT_NUMBER
        defaultDocumentShouldNotBeFound("documentNumber.doesNotContain=" + DEFAULT_DOCUMENT_NUMBER);

        // Get all the documentList where documentNumber does not contain UPDATED_DOCUMENT_NUMBER
        defaultDocumentShouldBeFound("documentNumber.doesNotContain=" + UPDATED_DOCUMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentsByIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where issueDate equals to DEFAULT_ISSUE_DATE
        defaultDocumentShouldBeFound("issueDate.equals=" + DEFAULT_ISSUE_DATE);

        // Get all the documentList where issueDate equals to UPDATED_ISSUE_DATE
        defaultDocumentShouldNotBeFound("issueDate.equals=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    void getAllDocumentsByIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where issueDate in DEFAULT_ISSUE_DATE or UPDATED_ISSUE_DATE
        defaultDocumentShouldBeFound("issueDate.in=" + DEFAULT_ISSUE_DATE + "," + UPDATED_ISSUE_DATE);

        // Get all the documentList where issueDate equals to UPDATED_ISSUE_DATE
        defaultDocumentShouldNotBeFound("issueDate.in=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    void getAllDocumentsByIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where issueDate is not null
        defaultDocumentShouldBeFound("issueDate.specified=true");

        // Get all the documentList where issueDate is null
        defaultDocumentShouldNotBeFound("issueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultDocumentShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the documentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultDocumentShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllDocumentsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultDocumentShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the documentList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultDocumentShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllDocumentsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where expiryDate is not null
        defaultDocumentShouldBeFound("expiryDate.specified=true");

        // Get all the documentList where expiryDate is null
        defaultDocumentShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the documentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the documentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentStatus is not null
        defaultDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the documentList where documentStatus is null
        defaultDocumentShouldNotBeFound("documentStatus.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentShouldBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uploadFileContentType").value(hasItem(DEFAULT_UPLOAD_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD_FILE))));

        // Check, that the count call also returns 1
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentShouldNotBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .issueDate(UPDATED_ISSUE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .uploadFile(UPDATED_UPLOAD_FILE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE);

        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testDocument.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testDocument.getUploadFile()).isEqualTo(UPDATED_UPLOAD_FILE);
        assertThat(testDocument.getUploadFileContentType()).isEqualTo(UPDATED_UPLOAD_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, document.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument.documentNumber(UPDATED_DOCUMENT_NUMBER).documentStatus(UPDATED_DOCUMENT_STATUS);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocument.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testDocument.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testDocument.getUploadFile()).isEqualTo(DEFAULT_UPLOAD_FILE);
        assertThat(testDocument.getUploadFileContentType()).isEqualTo(DEFAULT_UPLOAD_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .issueDate(UPDATED_ISSUE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .uploadFile(UPDATED_UPLOAD_FILE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testDocument.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testDocument.getUploadFile()).isEqualTo(UPDATED_UPLOAD_FILE);
        assertThat(testDocument.getUploadFileContentType()).isEqualTo(UPDATED_UPLOAD_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, document.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
