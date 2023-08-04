package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.Product;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.StockStatus;
import com.yam.ecompany.domain.enumeration.TimeTaken;
import com.yam.ecompany.repository.ProductRepository;
import com.yam.ecompany.service.ProductService;
import com.yam.ecompany.service.criteria.ProductCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_REMARK = "BBBBBBBBBB";

    private static final Country DEFAULT_PRODUCT_ORIGIN = Country.INDIA;
    private static final Country UPDATED_PRODUCT_ORIGIN = Country.SA;

    private static final StockStatus DEFAULT_PRODUCT_STOCK_STATUS = StockStatus.CUSTOM_ORDER;
    private static final StockStatus UPDATED_PRODUCT_STOCK_STATUS = StockStatus.EX_STOCK;

    private static final TimeTaken DEFAULT_PRODUCT_AVG_DELIVERY_TIME = TimeTaken.WEEKLY;
    private static final TimeTaken UPDATED_PRODUCT_AVG_DELIVERY_TIME = TimeTaken.MONTHLY;

    private static final String DEFAULT_PRODUCT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_MANUFACTURER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRODUCT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRODUCT_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRODUCT_IMAGE_CONTENT_TYPE = "image/png";

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

    private static final byte[] DEFAULT_PRODUCT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRODUCT_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private ProductService productServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productName(DEFAULT_PRODUCT_NAME)
            .productRemark(DEFAULT_PRODUCT_REMARK)
            .productOrigin(DEFAULT_PRODUCT_ORIGIN)
            .productStockStatus(DEFAULT_PRODUCT_STOCK_STATUS)
            .productAvgDeliveryTime(DEFAULT_PRODUCT_AVG_DELIVERY_TIME)
            .productManufacturer(DEFAULT_PRODUCT_MANUFACTURER)
            .productImage(DEFAULT_PRODUCT_IMAGE)
            .productImageContentType(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .taken(DEFAULT_TAKEN)
            .uploaded(DEFAULT_UPLOADED)
            .productAttachments(DEFAULT_PRODUCT_ATTACHMENTS)
            .productAttachmentsContentType(DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        product.setVendorsName(vendor);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .productName(UPDATED_PRODUCT_NAME)
            .productRemark(UPDATED_PRODUCT_REMARK)
            .productOrigin(UPDATED_PRODUCT_ORIGIN)
            .productStockStatus(UPDATED_PRODUCT_STOCK_STATUS)
            .productAvgDeliveryTime(UPDATED_PRODUCT_AVG_DELIVERY_TIME)
            .productManufacturer(UPDATED_PRODUCT_MANUFACTURER)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productImageContentType(UPDATED_PRODUCT_IMAGE_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .productAttachments(UPDATED_PRODUCT_ATTACHMENTS)
            .productAttachmentsContentType(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
        // Add required entity
        Vendor vendor;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            vendor = VendorResourceIT.createUpdatedEntity(em);
            em.persist(vendor);
            em.flush();
        } else {
            vendor = TestUtil.findAll(em, Vendor.class).get(0);
        }
        product.setVendorsName(vendor);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductRemark()).isEqualTo(DEFAULT_PRODUCT_REMARK);
        assertThat(testProduct.getProductOrigin()).isEqualTo(DEFAULT_PRODUCT_ORIGIN);
        assertThat(testProduct.getProductStockStatus()).isEqualTo(DEFAULT_PRODUCT_STOCK_STATUS);
        assertThat(testProduct.getProductAvgDeliveryTime()).isEqualTo(DEFAULT_PRODUCT_AVG_DELIVERY_TIME);
        assertThat(testProduct.getProductManufacturer()).isEqualTo(DEFAULT_PRODUCT_MANUFACTURER);
        assertThat(testProduct.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testProduct.getProductImageContentType()).isEqualTo(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProduct.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testProduct.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testProduct.getProductAttachments()).isEqualTo(DEFAULT_PRODUCT_ATTACHMENTS);
        assertThat(testProduct.getProductAttachmentsContentType()).isEqualTo(DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productRemark").value(hasItem(DEFAULT_PRODUCT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].productOrigin").value(hasItem(DEFAULT_PRODUCT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].productStockStatus").value(hasItem(DEFAULT_PRODUCT_STOCK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].productAvgDeliveryTime").value(hasItem(DEFAULT_PRODUCT_AVG_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].productManufacturer").value(hasItem(DEFAULT_PRODUCT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].productImageContentType").value(hasItem(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGE))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].productAttachmentsContentType").value(hasItem(DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productAttachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_ATTACHMENTS))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductsWithEagerRelationshipsIsEnabled() throws Exception {
        when(productServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productRemark").value(DEFAULT_PRODUCT_REMARK.toString()))
            .andExpect(jsonPath("$.productOrigin").value(DEFAULT_PRODUCT_ORIGIN.toString()))
            .andExpect(jsonPath("$.productStockStatus").value(DEFAULT_PRODUCT_STOCK_STATUS.toString()))
            .andExpect(jsonPath("$.productAvgDeliveryTime").value(DEFAULT_PRODUCT_AVG_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.productManufacturer").value(DEFAULT_PRODUCT_MANUFACTURER))
            .andExpect(jsonPath("$.productImageContentType").value(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.productImage").value(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGE)))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.taken").value(DEFAULT_TAKEN.toString()))
            .andExpect(jsonPath("$.uploaded").value(DEFAULT_UPLOADED.toString()))
            .andExpect(jsonPath("$.productAttachmentsContentType").value(DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.productAttachments").value(Base64Utils.encodeToString(DEFAULT_PRODUCT_ATTACHMENTS)));
    }

    @Test
    @Transactional
    void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName is not null
        defaultProductShouldBeFound("productName.specified=true");

        // Get all the productList where productName is null
        defaultProductShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName contains DEFAULT_PRODUCT_NAME
        defaultProductShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName contains UPDATED_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultProductShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the productList where productName does not contain UPDATED_PRODUCT_NAME
        defaultProductShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProductOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productOrigin equals to DEFAULT_PRODUCT_ORIGIN
        defaultProductShouldBeFound("productOrigin.equals=" + DEFAULT_PRODUCT_ORIGIN);

        // Get all the productList where productOrigin equals to UPDATED_PRODUCT_ORIGIN
        defaultProductShouldNotBeFound("productOrigin.equals=" + UPDATED_PRODUCT_ORIGIN);
    }

    @Test
    @Transactional
    void getAllProductsByProductOriginIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productOrigin in DEFAULT_PRODUCT_ORIGIN or UPDATED_PRODUCT_ORIGIN
        defaultProductShouldBeFound("productOrigin.in=" + DEFAULT_PRODUCT_ORIGIN + "," + UPDATED_PRODUCT_ORIGIN);

        // Get all the productList where productOrigin equals to UPDATED_PRODUCT_ORIGIN
        defaultProductShouldNotBeFound("productOrigin.in=" + UPDATED_PRODUCT_ORIGIN);
    }

    @Test
    @Transactional
    void getAllProductsByProductOriginIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productOrigin is not null
        defaultProductShouldBeFound("productOrigin.specified=true");

        // Get all the productList where productOrigin is null
        defaultProductShouldNotBeFound("productOrigin.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductStockStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productStockStatus equals to DEFAULT_PRODUCT_STOCK_STATUS
        defaultProductShouldBeFound("productStockStatus.equals=" + DEFAULT_PRODUCT_STOCK_STATUS);

        // Get all the productList where productStockStatus equals to UPDATED_PRODUCT_STOCK_STATUS
        defaultProductShouldNotBeFound("productStockStatus.equals=" + UPDATED_PRODUCT_STOCK_STATUS);
    }

    @Test
    @Transactional
    void getAllProductsByProductStockStatusIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productStockStatus in DEFAULT_PRODUCT_STOCK_STATUS or UPDATED_PRODUCT_STOCK_STATUS
        defaultProductShouldBeFound("productStockStatus.in=" + DEFAULT_PRODUCT_STOCK_STATUS + "," + UPDATED_PRODUCT_STOCK_STATUS);

        // Get all the productList where productStockStatus equals to UPDATED_PRODUCT_STOCK_STATUS
        defaultProductShouldNotBeFound("productStockStatus.in=" + UPDATED_PRODUCT_STOCK_STATUS);
    }

    @Test
    @Transactional
    void getAllProductsByProductStockStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productStockStatus is not null
        defaultProductShouldBeFound("productStockStatus.specified=true");

        // Get all the productList where productStockStatus is null
        defaultProductShouldNotBeFound("productStockStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductAvgDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productAvgDeliveryTime equals to DEFAULT_PRODUCT_AVG_DELIVERY_TIME
        defaultProductShouldBeFound("productAvgDeliveryTime.equals=" + DEFAULT_PRODUCT_AVG_DELIVERY_TIME);

        // Get all the productList where productAvgDeliveryTime equals to UPDATED_PRODUCT_AVG_DELIVERY_TIME
        defaultProductShouldNotBeFound("productAvgDeliveryTime.equals=" + UPDATED_PRODUCT_AVG_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllProductsByProductAvgDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productAvgDeliveryTime in DEFAULT_PRODUCT_AVG_DELIVERY_TIME or UPDATED_PRODUCT_AVG_DELIVERY_TIME
        defaultProductShouldBeFound(
            "productAvgDeliveryTime.in=" + DEFAULT_PRODUCT_AVG_DELIVERY_TIME + "," + UPDATED_PRODUCT_AVG_DELIVERY_TIME
        );

        // Get all the productList where productAvgDeliveryTime equals to UPDATED_PRODUCT_AVG_DELIVERY_TIME
        defaultProductShouldNotBeFound("productAvgDeliveryTime.in=" + UPDATED_PRODUCT_AVG_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllProductsByProductAvgDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productAvgDeliveryTime is not null
        defaultProductShouldBeFound("productAvgDeliveryTime.specified=true");

        // Get all the productList where productAvgDeliveryTime is null
        defaultProductShouldNotBeFound("productAvgDeliveryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productManufacturer equals to DEFAULT_PRODUCT_MANUFACTURER
        defaultProductShouldBeFound("productManufacturer.equals=" + DEFAULT_PRODUCT_MANUFACTURER);

        // Get all the productList where productManufacturer equals to UPDATED_PRODUCT_MANUFACTURER
        defaultProductShouldNotBeFound("productManufacturer.equals=" + UPDATED_PRODUCT_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllProductsByProductManufacturerIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productManufacturer in DEFAULT_PRODUCT_MANUFACTURER or UPDATED_PRODUCT_MANUFACTURER
        defaultProductShouldBeFound("productManufacturer.in=" + DEFAULT_PRODUCT_MANUFACTURER + "," + UPDATED_PRODUCT_MANUFACTURER);

        // Get all the productList where productManufacturer equals to UPDATED_PRODUCT_MANUFACTURER
        defaultProductShouldNotBeFound("productManufacturer.in=" + UPDATED_PRODUCT_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllProductsByProductManufacturerIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productManufacturer is not null
        defaultProductShouldBeFound("productManufacturer.specified=true");

        // Get all the productList where productManufacturer is null
        defaultProductShouldNotBeFound("productManufacturer.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductManufacturerContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productManufacturer contains DEFAULT_PRODUCT_MANUFACTURER
        defaultProductShouldBeFound("productManufacturer.contains=" + DEFAULT_PRODUCT_MANUFACTURER);

        // Get all the productList where productManufacturer contains UPDATED_PRODUCT_MANUFACTURER
        defaultProductShouldNotBeFound("productManufacturer.contains=" + UPDATED_PRODUCT_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllProductsByProductManufacturerNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productManufacturer does not contain DEFAULT_PRODUCT_MANUFACTURER
        defaultProductShouldNotBeFound("productManufacturer.doesNotContain=" + DEFAULT_PRODUCT_MANUFACTURER);

        // Get all the productList where productManufacturer does not contain UPDATED_PRODUCT_MANUFACTURER
        defaultProductShouldBeFound("productManufacturer.doesNotContain=" + UPDATED_PRODUCT_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height equals to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the productList where height equals to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultProductShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the productList where height equals to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is not null
        defaultProductShouldBeFound("height.specified=true");

        // Get all the productList where height is null
        defaultProductShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is greater than or equal to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the productList where height is greater than or equal to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is less than or equal to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the productList where height is less than or equal to SMALLER_HEIGHT
        defaultProductShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is less than DEFAULT_HEIGHT
        defaultProductShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the productList where height is less than UPDATED_HEIGHT
        defaultProductShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is greater than DEFAULT_HEIGHT
        defaultProductShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the productList where height is greater than SMALLER_HEIGHT
        defaultProductShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width equals to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the productList where width equals to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultProductShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the productList where width equals to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is not null
        defaultProductShouldBeFound("width.specified=true");

        // Get all the productList where width is null
        defaultProductShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is greater than or equal to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the productList where width is greater than or equal to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is less than or equal to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the productList where width is less than or equal to SMALLER_WIDTH
        defaultProductShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is less than DEFAULT_WIDTH
        defaultProductShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the productList where width is less than UPDATED_WIDTH
        defaultProductShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is greater than DEFAULT_WIDTH
        defaultProductShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the productList where width is greater than SMALLER_WIDTH
        defaultProductShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllProductsByTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where taken equals to DEFAULT_TAKEN
        defaultProductShouldBeFound("taken.equals=" + DEFAULT_TAKEN);

        // Get all the productList where taken equals to UPDATED_TAKEN
        defaultProductShouldNotBeFound("taken.equals=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllProductsByTakenIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where taken in DEFAULT_TAKEN or UPDATED_TAKEN
        defaultProductShouldBeFound("taken.in=" + DEFAULT_TAKEN + "," + UPDATED_TAKEN);

        // Get all the productList where taken equals to UPDATED_TAKEN
        defaultProductShouldNotBeFound("taken.in=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllProductsByTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where taken is not null
        defaultProductShouldBeFound("taken.specified=true");

        // Get all the productList where taken is null
        defaultProductShouldNotBeFound("taken.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByUploadedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where uploaded equals to DEFAULT_UPLOADED
        defaultProductShouldBeFound("uploaded.equals=" + DEFAULT_UPLOADED);

        // Get all the productList where uploaded equals to UPDATED_UPLOADED
        defaultProductShouldNotBeFound("uploaded.equals=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllProductsByUploadedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where uploaded in DEFAULT_UPLOADED or UPDATED_UPLOADED
        defaultProductShouldBeFound("uploaded.in=" + DEFAULT_UPLOADED + "," + UPDATED_UPLOADED);

        // Get all the productList where uploaded equals to UPDATED_UPLOADED
        defaultProductShouldNotBeFound("uploaded.in=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllProductsByUploadedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where uploaded is not null
        defaultProductShouldBeFound("uploaded.specified=true");

        // Get all the productList where uploaded is null
        defaultProductShouldNotBeFound("uploaded.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByVendorsNameIsEqualToSomething() throws Exception {
        Vendor vendorsName;
        if (TestUtil.findAll(em, Vendor.class).isEmpty()) {
            productRepository.saveAndFlush(product);
            vendorsName = VendorResourceIT.createEntity(em);
        } else {
            vendorsName = TestUtil.findAll(em, Vendor.class).get(0);
        }
        em.persist(vendorsName);
        em.flush();
        product.setVendorsName(vendorsName);
        productRepository.saveAndFlush(product);
        Long vendorsNameId = vendorsName.getId();
        // Get all the productList where vendorsName equals to vendorsNameId
        defaultProductShouldBeFound("vendorsNameId.equals=" + vendorsNameId);

        // Get all the productList where vendorsName equals to (vendorsNameId + 1)
        defaultProductShouldNotBeFound("vendorsNameId.equals=" + (vendorsNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productRemark").value(hasItem(DEFAULT_PRODUCT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].productOrigin").value(hasItem(DEFAULT_PRODUCT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].productStockStatus").value(hasItem(DEFAULT_PRODUCT_STOCK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].productAvgDeliveryTime").value(hasItem(DEFAULT_PRODUCT_AVG_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].productManufacturer").value(hasItem(DEFAULT_PRODUCT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].productImageContentType").value(hasItem(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGE))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].productAttachmentsContentType").value(hasItem(DEFAULT_PRODUCT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productAttachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_ATTACHMENTS))));

        // Check, that the count call also returns 1
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productName(UPDATED_PRODUCT_NAME)
            .productRemark(UPDATED_PRODUCT_REMARK)
            .productOrigin(UPDATED_PRODUCT_ORIGIN)
            .productStockStatus(UPDATED_PRODUCT_STOCK_STATUS)
            .productAvgDeliveryTime(UPDATED_PRODUCT_AVG_DELIVERY_TIME)
            .productManufacturer(UPDATED_PRODUCT_MANUFACTURER)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productImageContentType(UPDATED_PRODUCT_IMAGE_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .productAttachments(UPDATED_PRODUCT_ATTACHMENTS)
            .productAttachmentsContentType(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductRemark()).isEqualTo(UPDATED_PRODUCT_REMARK);
        assertThat(testProduct.getProductOrigin()).isEqualTo(UPDATED_PRODUCT_ORIGIN);
        assertThat(testProduct.getProductStockStatus()).isEqualTo(UPDATED_PRODUCT_STOCK_STATUS);
        assertThat(testProduct.getProductAvgDeliveryTime()).isEqualTo(UPDATED_PRODUCT_AVG_DELIVERY_TIME);
        assertThat(testProduct.getProductManufacturer()).isEqualTo(UPDATED_PRODUCT_MANUFACTURER);
        assertThat(testProduct.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testProduct.getProductImageContentType()).isEqualTo(UPDATED_PRODUCT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testProduct.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testProduct.getProductAttachments()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS);
        assertThat(testProduct.getProductAttachmentsContentType()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, product.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productName(UPDATED_PRODUCT_NAME)
            .productRemark(UPDATED_PRODUCT_REMARK)
            .productAvgDeliveryTime(UPDATED_PRODUCT_AVG_DELIVERY_TIME)
            .width(UPDATED_WIDTH)
            .productAttachments(UPDATED_PRODUCT_ATTACHMENTS)
            .productAttachmentsContentType(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductRemark()).isEqualTo(UPDATED_PRODUCT_REMARK);
        assertThat(testProduct.getProductOrigin()).isEqualTo(DEFAULT_PRODUCT_ORIGIN);
        assertThat(testProduct.getProductStockStatus()).isEqualTo(DEFAULT_PRODUCT_STOCK_STATUS);
        assertThat(testProduct.getProductAvgDeliveryTime()).isEqualTo(UPDATED_PRODUCT_AVG_DELIVERY_TIME);
        assertThat(testProduct.getProductManufacturer()).isEqualTo(DEFAULT_PRODUCT_MANUFACTURER);
        assertThat(testProduct.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testProduct.getProductImageContentType()).isEqualTo(DEFAULT_PRODUCT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testProduct.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testProduct.getProductAttachments()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS);
        assertThat(testProduct.getProductAttachmentsContentType()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productName(UPDATED_PRODUCT_NAME)
            .productRemark(UPDATED_PRODUCT_REMARK)
            .productOrigin(UPDATED_PRODUCT_ORIGIN)
            .productStockStatus(UPDATED_PRODUCT_STOCK_STATUS)
            .productAvgDeliveryTime(UPDATED_PRODUCT_AVG_DELIVERY_TIME)
            .productManufacturer(UPDATED_PRODUCT_MANUFACTURER)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productImageContentType(UPDATED_PRODUCT_IMAGE_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .productAttachments(UPDATED_PRODUCT_ATTACHMENTS)
            .productAttachmentsContentType(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductRemark()).isEqualTo(UPDATED_PRODUCT_REMARK);
        assertThat(testProduct.getProductOrigin()).isEqualTo(UPDATED_PRODUCT_ORIGIN);
        assertThat(testProduct.getProductStockStatus()).isEqualTo(UPDATED_PRODUCT_STOCK_STATUS);
        assertThat(testProduct.getProductAvgDeliveryTime()).isEqualTo(UPDATED_PRODUCT_AVG_DELIVERY_TIME);
        assertThat(testProduct.getProductManufacturer()).isEqualTo(UPDATED_PRODUCT_MANUFACTURER);
        assertThat(testProduct.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testProduct.getProductImageContentType()).isEqualTo(UPDATED_PRODUCT_IMAGE_CONTENT_TYPE);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testProduct.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testProduct.getProductAttachments()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS);
        assertThat(testProduct.getProductAttachmentsContentType()).isEqualTo(UPDATED_PRODUCT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, product.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
