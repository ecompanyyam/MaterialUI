package com.yam.ecompany.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yam.ecompany.IntegrationTest;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.OfficeFunctionality;
import com.yam.ecompany.domain.enumeration.VendorCategory;
import com.yam.ecompany.domain.enumeration.VendorType;
import com.yam.ecompany.repository.VendorRepository;
import com.yam.ecompany.service.criteria.VendorCriteria;
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
 * Integration tests for the {@link VendorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VendorResourceIT {

    private static final String DEFAULT_VENDOR_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_VAT_NUMBER = "BBBBBBBBBB";

    private static final VendorType DEFAULT_VENDOR_TYPE = VendorType.LOCAL;
    private static final VendorType UPDATED_VENDOR_TYPE = VendorType.FOREIGN;

    private static final VendorCategory DEFAULT_VENDOR_CATEGORY = VendorCategory.MANUFACTURER;
    private static final VendorCategory UPDATED_VENDOR_CATEGORY = VendorCategory.SUPPLIER;

    private static final String DEFAULT_VENDOR_ESTABLISHMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ESTABLISHMENT_DATE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VENDOR_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VENDOR_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VENDOR_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VENDOR_LOGO_CONTENT_TYPE = "image/png";

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

    private static final String DEFAULT_CONTACT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL_ADDRESS = "tTOs6t@a~mz.R";
    private static final String UPDATED_CONTACT_EMAIL_ADDRESS = ")@T(Rdj.qYjq";

    private static final String DEFAULT_CONTACT_PRIMARY_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PRIMARY_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PRIMARY_FAX_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PRIMARY_FAX_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_SECONDARY_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_SECONDARY_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_SECONDARY_FAX_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_SECONDARY_FAX_NO = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_LOCATION = "BBBBBBBBBB";

    private static final OfficeFunctionality DEFAULT_OFFICE_FUNCTIONALITY = OfficeFunctionality.MAIN;
    private static final OfficeFunctionality UPDATED_OFFICE_FUNCTIONALITY = OfficeFunctionality.BRANCH;

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING_NO = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_STREET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NO = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NO = "BBBBBBBBBB";

    private static final Country DEFAULT_COUNTRY = Country.INDIA;
    private static final Country UPDATED_COUNTRY = Country.SA;

    private static final String DEFAULT_GOOGLE_MAP = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_MAP = "BBBBBBBBBB";

    private static final String DEFAULT_COMBINED_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMBINED_ADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_C_R_CERTIFICATE_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_C_R_CERTIFICATE_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_V_AT_CERTIFICATE_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_V_AT_CERTIFICATE_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_NATIONAL_ADDRESS_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NATIONAL_ADDRESS_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COMPANY_PROFILE_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPANY_PROFILE_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_OTHER_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTHER_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTHER_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTHER_UPLOAD_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CASH = "AAAAAAAAAA";
    private static final String UPDATED_CASH = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT = "BBBBBBBBBB";

    private static final String DEFAULT_LETTER_OF_CREDIT = "AAAAAAAAAA";
    private static final String UPDATED_LETTER_OF_CREDIT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vendors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendor createEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .vendorNameEnglish(DEFAULT_VENDOR_NAME_ENGLISH)
            .vendorNameArabic(DEFAULT_VENDOR_NAME_ARABIC)
            .vendorId(DEFAULT_VENDOR_ID)
            .vendorAccountNumber(DEFAULT_VENDOR_ACCOUNT_NUMBER)
            .vendorCRNumber(DEFAULT_VENDOR_CR_NUMBER)
            .vendorVATNumber(DEFAULT_VENDOR_VAT_NUMBER)
            .vendorType(DEFAULT_VENDOR_TYPE)
            .vendorCategory(DEFAULT_VENDOR_CATEGORY)
            .vendorEstablishmentDate(DEFAULT_VENDOR_ESTABLISHMENT_DATE)
            .vendorLogo(DEFAULT_VENDOR_LOGO)
            .vendorLogoContentType(DEFAULT_VENDOR_LOGO_CONTENT_TYPE)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .taken(DEFAULT_TAKEN)
            .uploaded(DEFAULT_UPLOADED)
            .contactFullName(DEFAULT_CONTACT_FULL_NAME)
            .contactEmailAddress(DEFAULT_CONTACT_EMAIL_ADDRESS)
            .contactPrimaryPhoneNo(DEFAULT_CONTACT_PRIMARY_PHONE_NO)
            .contactPrimaryFaxNo(DEFAULT_CONTACT_PRIMARY_FAX_NO)
            .contactSecondaryPhoneNo(DEFAULT_CONTACT_SECONDARY_PHONE_NO)
            .contactSecondaryFaxNo(DEFAULT_CONTACT_SECONDARY_FAX_NO)
            .officeLocation(DEFAULT_OFFICE_LOCATION)
            .officeFunctionality(DEFAULT_OFFICE_FUNCTIONALITY)
            .websiteURL(DEFAULT_WEBSITE_URL)
            .buildingNo(DEFAULT_BUILDING_NO)
            .vendorStreetName(DEFAULT_VENDOR_STREET_NAME)
            .zipCode(DEFAULT_ZIP_CODE)
            .districtName(DEFAULT_DISTRICT_NAME)
            .additionalNo(DEFAULT_ADDITIONAL_NO)
            .cityName(DEFAULT_CITY_NAME)
            .unitNo(DEFAULT_UNIT_NO)
            .country(DEFAULT_COUNTRY)
            .googleMap(DEFAULT_GOOGLE_MAP)
            .combinedAddress(DEFAULT_COMBINED_ADDRESS)
            .cRCertificateUpload(DEFAULT_C_R_CERTIFICATE_UPLOAD)
            .cRCertificateUploadContentType(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .vATCertificateUpload(DEFAULT_V_AT_CERTIFICATE_UPLOAD)
            .vATCertificateUploadContentType(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .nationalAddressUpload(DEFAULT_NATIONAL_ADDRESS_UPLOAD)
            .nationalAddressUploadContentType(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)
            .companyProfileUpload(DEFAULT_COMPANY_PROFILE_UPLOAD)
            .companyProfileUploadContentType(DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)
            .otherUpload(DEFAULT_OTHER_UPLOAD)
            .otherUploadContentType(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE)
            .cash(DEFAULT_CASH)
            .credit(DEFAULT_CREDIT)
            .letterOfCredit(DEFAULT_LETTER_OF_CREDIT)
            .others(DEFAULT_OTHERS);
        return vendor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendor createUpdatedEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .vendorNameEnglish(UPDATED_VENDOR_NAME_ENGLISH)
            .vendorNameArabic(UPDATED_VENDOR_NAME_ARABIC)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorAccountNumber(UPDATED_VENDOR_ACCOUNT_NUMBER)
            .vendorCRNumber(UPDATED_VENDOR_CR_NUMBER)
            .vendorVATNumber(UPDATED_VENDOR_VAT_NUMBER)
            .vendorType(UPDATED_VENDOR_TYPE)
            .vendorCategory(UPDATED_VENDOR_CATEGORY)
            .vendorEstablishmentDate(UPDATED_VENDOR_ESTABLISHMENT_DATE)
            .vendorLogo(UPDATED_VENDOR_LOGO)
            .vendorLogoContentType(UPDATED_VENDOR_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .contactFullName(UPDATED_CONTACT_FULL_NAME)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .contactPrimaryPhoneNo(UPDATED_CONTACT_PRIMARY_PHONE_NO)
            .contactPrimaryFaxNo(UPDATED_CONTACT_PRIMARY_FAX_NO)
            .contactSecondaryPhoneNo(UPDATED_CONTACT_SECONDARY_PHONE_NO)
            .contactSecondaryFaxNo(UPDATED_CONTACT_SECONDARY_FAX_NO)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .officeFunctionality(UPDATED_OFFICE_FUNCTIONALITY)
            .websiteURL(UPDATED_WEBSITE_URL)
            .buildingNo(UPDATED_BUILDING_NO)
            .vendorStreetName(UPDATED_VENDOR_STREET_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .additionalNo(UPDATED_ADDITIONAL_NO)
            .cityName(UPDATED_CITY_NAME)
            .unitNo(UPDATED_UNIT_NO)
            .country(UPDATED_COUNTRY)
            .googleMap(UPDATED_GOOGLE_MAP)
            .combinedAddress(UPDATED_COMBINED_ADDRESS)
            .cRCertificateUpload(UPDATED_C_R_CERTIFICATE_UPLOAD)
            .cRCertificateUploadContentType(UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .vATCertificateUpload(UPDATED_V_AT_CERTIFICATE_UPLOAD)
            .vATCertificateUploadContentType(UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .nationalAddressUpload(UPDATED_NATIONAL_ADDRESS_UPLOAD)
            .nationalAddressUploadContentType(UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)
            .companyProfileUpload(UPDATED_COMPANY_PROFILE_UPLOAD)
            .companyProfileUploadContentType(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)
            .otherUpload(UPDATED_OTHER_UPLOAD)
            .otherUploadContentType(UPDATED_OTHER_UPLOAD_CONTENT_TYPE)
            .cash(UPDATED_CASH)
            .credit(UPDATED_CREDIT)
            .letterOfCredit(UPDATED_LETTER_OF_CREDIT)
            .others(UPDATED_OTHERS);
        return vendor;
    }

    @BeforeEach
    public void initTest() {
        vendor = createEntity(em);
    }

    @Test
    @Transactional
    void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();
        // Create the Vendor
        restVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getVendorNameEnglish()).isEqualTo(DEFAULT_VENDOR_NAME_ENGLISH);
        assertThat(testVendor.getVendorNameArabic()).isEqualTo(DEFAULT_VENDOR_NAME_ARABIC);
        assertThat(testVendor.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testVendor.getVendorAccountNumber()).isEqualTo(DEFAULT_VENDOR_ACCOUNT_NUMBER);
        assertThat(testVendor.getVendorCRNumber()).isEqualTo(DEFAULT_VENDOR_CR_NUMBER);
        assertThat(testVendor.getVendorVATNumber()).isEqualTo(DEFAULT_VENDOR_VAT_NUMBER);
        assertThat(testVendor.getVendorType()).isEqualTo(DEFAULT_VENDOR_TYPE);
        assertThat(testVendor.getVendorCategory()).isEqualTo(DEFAULT_VENDOR_CATEGORY);
        assertThat(testVendor.getVendorEstablishmentDate()).isEqualTo(DEFAULT_VENDOR_ESTABLISHMENT_DATE);
        assertThat(testVendor.getVendorLogo()).isEqualTo(DEFAULT_VENDOR_LOGO);
        assertThat(testVendor.getVendorLogoContentType()).isEqualTo(DEFAULT_VENDOR_LOGO_CONTENT_TYPE);
        assertThat(testVendor.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testVendor.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testVendor.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testVendor.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testVendor.getContactFullName()).isEqualTo(DEFAULT_CONTACT_FULL_NAME);
        assertThat(testVendor.getContactEmailAddress()).isEqualTo(DEFAULT_CONTACT_EMAIL_ADDRESS);
        assertThat(testVendor.getContactPrimaryPhoneNo()).isEqualTo(DEFAULT_CONTACT_PRIMARY_PHONE_NO);
        assertThat(testVendor.getContactPrimaryFaxNo()).isEqualTo(DEFAULT_CONTACT_PRIMARY_FAX_NO);
        assertThat(testVendor.getContactSecondaryPhoneNo()).isEqualTo(DEFAULT_CONTACT_SECONDARY_PHONE_NO);
        assertThat(testVendor.getContactSecondaryFaxNo()).isEqualTo(DEFAULT_CONTACT_SECONDARY_FAX_NO);
        assertThat(testVendor.getOfficeLocation()).isEqualTo(DEFAULT_OFFICE_LOCATION);
        assertThat(testVendor.getOfficeFunctionality()).isEqualTo(DEFAULT_OFFICE_FUNCTIONALITY);
        assertThat(testVendor.getWebsiteURL()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testVendor.getBuildingNo()).isEqualTo(DEFAULT_BUILDING_NO);
        assertThat(testVendor.getVendorStreetName()).isEqualTo(DEFAULT_VENDOR_STREET_NAME);
        assertThat(testVendor.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testVendor.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testVendor.getAdditionalNo()).isEqualTo(DEFAULT_ADDITIONAL_NO);
        assertThat(testVendor.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testVendor.getUnitNo()).isEqualTo(DEFAULT_UNIT_NO);
        assertThat(testVendor.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testVendor.getGoogleMap()).isEqualTo(DEFAULT_GOOGLE_MAP);
        assertThat(testVendor.getCombinedAddress()).isEqualTo(DEFAULT_COMBINED_ADDRESS);
        assertThat(testVendor.getcRCertificateUpload()).isEqualTo(DEFAULT_C_R_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getcRCertificateUploadContentType()).isEqualTo(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getvATCertificateUpload()).isEqualTo(DEFAULT_V_AT_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getvATCertificateUploadContentType()).isEqualTo(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getNationalAddressUpload()).isEqualTo(DEFAULT_NATIONAL_ADDRESS_UPLOAD);
        assertThat(testVendor.getNationalAddressUploadContentType()).isEqualTo(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCompanyProfileUpload()).isEqualTo(DEFAULT_COMPANY_PROFILE_UPLOAD);
        assertThat(testVendor.getCompanyProfileUploadContentType()).isEqualTo(DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getOtherUpload()).isEqualTo(DEFAULT_OTHER_UPLOAD);
        assertThat(testVendor.getOtherUploadContentType()).isEqualTo(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCash()).isEqualTo(DEFAULT_CASH);
        assertThat(testVendor.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testVendor.getLetterOfCredit()).isEqualTo(DEFAULT_LETTER_OF_CREDIT);
        assertThat(testVendor.getOthers()).isEqualTo(DEFAULT_OTHERS);
    }

    @Test
    @Transactional
    void createVendorWithExistingId() throws Exception {
        // Create the Vendor with an existing ID
        vendor.setId(1L);

        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVendorNameEnglishIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setVendorNameEnglish(null);

        // Create the Vendor, which fails.

        restVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setVendorId(null);

        // Create the Vendor, which fails.

        restVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setContactEmailAddress(null);

        // Create the Vendor, which fails.

        restVendorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList
        restVendorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendorNameEnglish").value(hasItem(DEFAULT_VENDOR_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].vendorNameArabic").value(hasItem(DEFAULT_VENDOR_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].vendorAccountNumber").value(hasItem(DEFAULT_VENDOR_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorCRNumber").value(hasItem(DEFAULT_VENDOR_CR_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorVATNumber").value(hasItem(DEFAULT_VENDOR_VAT_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorType").value(hasItem(DEFAULT_VENDOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorCategory").value(hasItem(DEFAULT_VENDOR_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].vendorEstablishmentDate").value(hasItem(DEFAULT_VENDOR_ESTABLISHMENT_DATE)))
            .andExpect(jsonPath("$.[*].vendorLogoContentType").value(hasItem(DEFAULT_VENDOR_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].vendorLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_VENDOR_LOGO))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].contactFullName").value(hasItem(DEFAULT_CONTACT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactPrimaryPhoneNo").value(hasItem(DEFAULT_CONTACT_PRIMARY_PHONE_NO)))
            .andExpect(jsonPath("$.[*].contactPrimaryFaxNo").value(hasItem(DEFAULT_CONTACT_PRIMARY_FAX_NO)))
            .andExpect(jsonPath("$.[*].contactSecondaryPhoneNo").value(hasItem(DEFAULT_CONTACT_SECONDARY_PHONE_NO)))
            .andExpect(jsonPath("$.[*].contactSecondaryFaxNo").value(hasItem(DEFAULT_CONTACT_SECONDARY_FAX_NO)))
            .andExpect(jsonPath("$.[*].officeLocation").value(hasItem(DEFAULT_OFFICE_LOCATION)))
            .andExpect(jsonPath("$.[*].officeFunctionality").value(hasItem(DEFAULT_OFFICE_FUNCTIONALITY.toString())))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].buildingNo").value(hasItem(DEFAULT_BUILDING_NO)))
            .andExpect(jsonPath("$.[*].vendorStreetName").value(hasItem(DEFAULT_VENDOR_STREET_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].additionalNo").value(hasItem(DEFAULT_ADDITIONAL_NO)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].unitNo").value(hasItem(DEFAULT_UNIT_NO)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].googleMap").value(hasItem(DEFAULT_GOOGLE_MAP.toString())))
            .andExpect(jsonPath("$.[*].combinedAddress").value(hasItem(DEFAULT_COMBINED_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].cRCertificateUploadContentType").value(hasItem(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cRCertificateUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_C_R_CERTIFICATE_UPLOAD))))
            .andExpect(jsonPath("$.[*].vATCertificateUploadContentType").value(hasItem(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].vATCertificateUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_V_AT_CERTIFICATE_UPLOAD))))
            .andExpect(jsonPath("$.[*].nationalAddressUploadContentType").value(hasItem(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalAddressUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONAL_ADDRESS_UPLOAD))))
            .andExpect(jsonPath("$.[*].companyProfileUploadContentType").value(hasItem(DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].companyProfileUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_PROFILE_UPLOAD))))
            .andExpect(jsonPath("$.[*].otherUploadContentType").value(hasItem(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_UPLOAD))))
            .andExpect(jsonPath("$.[*].cash").value(hasItem(DEFAULT_CASH)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].letterOfCredit").value(hasItem(DEFAULT_LETTER_OF_CREDIT)))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS)));
    }

    @Test
    @Transactional
    void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc
            .perform(get(ENTITY_API_URL_ID, vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.vendorNameEnglish").value(DEFAULT_VENDOR_NAME_ENGLISH))
            .andExpect(jsonPath("$.vendorNameArabic").value(DEFAULT_VENDOR_NAME_ARABIC))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID))
            .andExpect(jsonPath("$.vendorAccountNumber").value(DEFAULT_VENDOR_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.vendorCRNumber").value(DEFAULT_VENDOR_CR_NUMBER))
            .andExpect(jsonPath("$.vendorVATNumber").value(DEFAULT_VENDOR_VAT_NUMBER))
            .andExpect(jsonPath("$.vendorType").value(DEFAULT_VENDOR_TYPE.toString()))
            .andExpect(jsonPath("$.vendorCategory").value(DEFAULT_VENDOR_CATEGORY.toString()))
            .andExpect(jsonPath("$.vendorEstablishmentDate").value(DEFAULT_VENDOR_ESTABLISHMENT_DATE))
            .andExpect(jsonPath("$.vendorLogoContentType").value(DEFAULT_VENDOR_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.vendorLogo").value(Base64Utils.encodeToString(DEFAULT_VENDOR_LOGO)))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.taken").value(DEFAULT_TAKEN.toString()))
            .andExpect(jsonPath("$.uploaded").value(DEFAULT_UPLOADED.toString()))
            .andExpect(jsonPath("$.contactFullName").value(DEFAULT_CONTACT_FULL_NAME))
            .andExpect(jsonPath("$.contactEmailAddress").value(DEFAULT_CONTACT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.contactPrimaryPhoneNo").value(DEFAULT_CONTACT_PRIMARY_PHONE_NO))
            .andExpect(jsonPath("$.contactPrimaryFaxNo").value(DEFAULT_CONTACT_PRIMARY_FAX_NO))
            .andExpect(jsonPath("$.contactSecondaryPhoneNo").value(DEFAULT_CONTACT_SECONDARY_PHONE_NO))
            .andExpect(jsonPath("$.contactSecondaryFaxNo").value(DEFAULT_CONTACT_SECONDARY_FAX_NO))
            .andExpect(jsonPath("$.officeLocation").value(DEFAULT_OFFICE_LOCATION))
            .andExpect(jsonPath("$.officeFunctionality").value(DEFAULT_OFFICE_FUNCTIONALITY.toString()))
            .andExpect(jsonPath("$.websiteURL").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.buildingNo").value(DEFAULT_BUILDING_NO))
            .andExpect(jsonPath("$.vendorStreetName").value(DEFAULT_VENDOR_STREET_NAME))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME))
            .andExpect(jsonPath("$.additionalNo").value(DEFAULT_ADDITIONAL_NO))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME))
            .andExpect(jsonPath("$.unitNo").value(DEFAULT_UNIT_NO))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.googleMap").value(DEFAULT_GOOGLE_MAP.toString()))
            .andExpect(jsonPath("$.combinedAddress").value(DEFAULT_COMBINED_ADDRESS.toString()))
            .andExpect(jsonPath("$.cRCertificateUploadContentType").value(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.cRCertificateUpload").value(Base64Utils.encodeToString(DEFAULT_C_R_CERTIFICATE_UPLOAD)))
            .andExpect(jsonPath("$.vATCertificateUploadContentType").value(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.vATCertificateUpload").value(Base64Utils.encodeToString(DEFAULT_V_AT_CERTIFICATE_UPLOAD)))
            .andExpect(jsonPath("$.nationalAddressUploadContentType").value(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.nationalAddressUpload").value(Base64Utils.encodeToString(DEFAULT_NATIONAL_ADDRESS_UPLOAD)))
            .andExpect(jsonPath("$.companyProfileUploadContentType").value(DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.companyProfileUpload").value(Base64Utils.encodeToString(DEFAULT_COMPANY_PROFILE_UPLOAD)))
            .andExpect(jsonPath("$.otherUploadContentType").value(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.otherUpload").value(Base64Utils.encodeToString(DEFAULT_OTHER_UPLOAD)))
            .andExpect(jsonPath("$.cash").value(DEFAULT_CASH))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT))
            .andExpect(jsonPath("$.letterOfCredit").value(DEFAULT_LETTER_OF_CREDIT))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS));
    }

    @Test
    @Transactional
    void getVendorsByIdFiltering() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        Long id = vendor.getId();

        defaultVendorShouldBeFound("id.equals=" + id);
        defaultVendorShouldNotBeFound("id.notEquals=" + id);

        defaultVendorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendorShouldNotBeFound("id.greaterThan=" + id);

        defaultVendorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameEnglishIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameEnglish equals to DEFAULT_VENDOR_NAME_ENGLISH
        defaultVendorShouldBeFound("vendorNameEnglish.equals=" + DEFAULT_VENDOR_NAME_ENGLISH);

        // Get all the vendorList where vendorNameEnglish equals to UPDATED_VENDOR_NAME_ENGLISH
        defaultVendorShouldNotBeFound("vendorNameEnglish.equals=" + UPDATED_VENDOR_NAME_ENGLISH);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameEnglishIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameEnglish in DEFAULT_VENDOR_NAME_ENGLISH or UPDATED_VENDOR_NAME_ENGLISH
        defaultVendorShouldBeFound("vendorNameEnglish.in=" + DEFAULT_VENDOR_NAME_ENGLISH + "," + UPDATED_VENDOR_NAME_ENGLISH);

        // Get all the vendorList where vendorNameEnglish equals to UPDATED_VENDOR_NAME_ENGLISH
        defaultVendorShouldNotBeFound("vendorNameEnglish.in=" + UPDATED_VENDOR_NAME_ENGLISH);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameEnglishIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameEnglish is not null
        defaultVendorShouldBeFound("vendorNameEnglish.specified=true");

        // Get all the vendorList where vendorNameEnglish is null
        defaultVendorShouldNotBeFound("vendorNameEnglish.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameEnglishContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameEnglish contains DEFAULT_VENDOR_NAME_ENGLISH
        defaultVendorShouldBeFound("vendorNameEnglish.contains=" + DEFAULT_VENDOR_NAME_ENGLISH);

        // Get all the vendorList where vendorNameEnglish contains UPDATED_VENDOR_NAME_ENGLISH
        defaultVendorShouldNotBeFound("vendorNameEnglish.contains=" + UPDATED_VENDOR_NAME_ENGLISH);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameEnglishNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameEnglish does not contain DEFAULT_VENDOR_NAME_ENGLISH
        defaultVendorShouldNotBeFound("vendorNameEnglish.doesNotContain=" + DEFAULT_VENDOR_NAME_ENGLISH);

        // Get all the vendorList where vendorNameEnglish does not contain UPDATED_VENDOR_NAME_ENGLISH
        defaultVendorShouldBeFound("vendorNameEnglish.doesNotContain=" + UPDATED_VENDOR_NAME_ENGLISH);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameArabicIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameArabic equals to DEFAULT_VENDOR_NAME_ARABIC
        defaultVendorShouldBeFound("vendorNameArabic.equals=" + DEFAULT_VENDOR_NAME_ARABIC);

        // Get all the vendorList where vendorNameArabic equals to UPDATED_VENDOR_NAME_ARABIC
        defaultVendorShouldNotBeFound("vendorNameArabic.equals=" + UPDATED_VENDOR_NAME_ARABIC);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameArabicIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameArabic in DEFAULT_VENDOR_NAME_ARABIC or UPDATED_VENDOR_NAME_ARABIC
        defaultVendorShouldBeFound("vendorNameArabic.in=" + DEFAULT_VENDOR_NAME_ARABIC + "," + UPDATED_VENDOR_NAME_ARABIC);

        // Get all the vendorList where vendorNameArabic equals to UPDATED_VENDOR_NAME_ARABIC
        defaultVendorShouldNotBeFound("vendorNameArabic.in=" + UPDATED_VENDOR_NAME_ARABIC);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameArabicIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameArabic is not null
        defaultVendorShouldBeFound("vendorNameArabic.specified=true");

        // Get all the vendorList where vendorNameArabic is null
        defaultVendorShouldNotBeFound("vendorNameArabic.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameArabicContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameArabic contains DEFAULT_VENDOR_NAME_ARABIC
        defaultVendorShouldBeFound("vendorNameArabic.contains=" + DEFAULT_VENDOR_NAME_ARABIC);

        // Get all the vendorList where vendorNameArabic contains UPDATED_VENDOR_NAME_ARABIC
        defaultVendorShouldNotBeFound("vendorNameArabic.contains=" + UPDATED_VENDOR_NAME_ARABIC);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorNameArabicNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorNameArabic does not contain DEFAULT_VENDOR_NAME_ARABIC
        defaultVendorShouldNotBeFound("vendorNameArabic.doesNotContain=" + DEFAULT_VENDOR_NAME_ARABIC);

        // Get all the vendorList where vendorNameArabic does not contain UPDATED_VENDOR_NAME_ARABIC
        defaultVendorShouldBeFound("vendorNameArabic.doesNotContain=" + UPDATED_VENDOR_NAME_ARABIC);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorId equals to DEFAULT_VENDOR_ID
        defaultVendorShouldBeFound("vendorId.equals=" + DEFAULT_VENDOR_ID);

        // Get all the vendorList where vendorId equals to UPDATED_VENDOR_ID
        defaultVendorShouldNotBeFound("vendorId.equals=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorIdIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorId in DEFAULT_VENDOR_ID or UPDATED_VENDOR_ID
        defaultVendorShouldBeFound("vendorId.in=" + DEFAULT_VENDOR_ID + "," + UPDATED_VENDOR_ID);

        // Get all the vendorList where vendorId equals to UPDATED_VENDOR_ID
        defaultVendorShouldNotBeFound("vendorId.in=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorId is not null
        defaultVendorShouldBeFound("vendorId.specified=true");

        // Get all the vendorList where vendorId is null
        defaultVendorShouldNotBeFound("vendorId.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorIdContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorId contains DEFAULT_VENDOR_ID
        defaultVendorShouldBeFound("vendorId.contains=" + DEFAULT_VENDOR_ID);

        // Get all the vendorList where vendorId contains UPDATED_VENDOR_ID
        defaultVendorShouldNotBeFound("vendorId.contains=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorIdNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorId does not contain DEFAULT_VENDOR_ID
        defaultVendorShouldNotBeFound("vendorId.doesNotContain=" + DEFAULT_VENDOR_ID);

        // Get all the vendorList where vendorId does not contain UPDATED_VENDOR_ID
        defaultVendorShouldBeFound("vendorId.doesNotContain=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorAccountNumber equals to DEFAULT_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldBeFound("vendorAccountNumber.equals=" + DEFAULT_VENDOR_ACCOUNT_NUMBER);

        // Get all the vendorList where vendorAccountNumber equals to UPDATED_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldNotBeFound("vendorAccountNumber.equals=" + UPDATED_VENDOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorAccountNumber in DEFAULT_VENDOR_ACCOUNT_NUMBER or UPDATED_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldBeFound("vendorAccountNumber.in=" + DEFAULT_VENDOR_ACCOUNT_NUMBER + "," + UPDATED_VENDOR_ACCOUNT_NUMBER);

        // Get all the vendorList where vendorAccountNumber equals to UPDATED_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldNotBeFound("vendorAccountNumber.in=" + UPDATED_VENDOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorAccountNumber is not null
        defaultVendorShouldBeFound("vendorAccountNumber.specified=true");

        // Get all the vendorList where vendorAccountNumber is null
        defaultVendorShouldNotBeFound("vendorAccountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorAccountNumber contains DEFAULT_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldBeFound("vendorAccountNumber.contains=" + DEFAULT_VENDOR_ACCOUNT_NUMBER);

        // Get all the vendorList where vendorAccountNumber contains UPDATED_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldNotBeFound("vendorAccountNumber.contains=" + UPDATED_VENDOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorAccountNumber does not contain DEFAULT_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldNotBeFound("vendorAccountNumber.doesNotContain=" + DEFAULT_VENDOR_ACCOUNT_NUMBER);

        // Get all the vendorList where vendorAccountNumber does not contain UPDATED_VENDOR_ACCOUNT_NUMBER
        defaultVendorShouldBeFound("vendorAccountNumber.doesNotContain=" + UPDATED_VENDOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCRNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCRNumber equals to DEFAULT_VENDOR_CR_NUMBER
        defaultVendorShouldBeFound("vendorCRNumber.equals=" + DEFAULT_VENDOR_CR_NUMBER);

        // Get all the vendorList where vendorCRNumber equals to UPDATED_VENDOR_CR_NUMBER
        defaultVendorShouldNotBeFound("vendorCRNumber.equals=" + UPDATED_VENDOR_CR_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCRNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCRNumber in DEFAULT_VENDOR_CR_NUMBER or UPDATED_VENDOR_CR_NUMBER
        defaultVendorShouldBeFound("vendorCRNumber.in=" + DEFAULT_VENDOR_CR_NUMBER + "," + UPDATED_VENDOR_CR_NUMBER);

        // Get all the vendorList where vendorCRNumber equals to UPDATED_VENDOR_CR_NUMBER
        defaultVendorShouldNotBeFound("vendorCRNumber.in=" + UPDATED_VENDOR_CR_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCRNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCRNumber is not null
        defaultVendorShouldBeFound("vendorCRNumber.specified=true");

        // Get all the vendorList where vendorCRNumber is null
        defaultVendorShouldNotBeFound("vendorCRNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCRNumberContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCRNumber contains DEFAULT_VENDOR_CR_NUMBER
        defaultVendorShouldBeFound("vendorCRNumber.contains=" + DEFAULT_VENDOR_CR_NUMBER);

        // Get all the vendorList where vendorCRNumber contains UPDATED_VENDOR_CR_NUMBER
        defaultVendorShouldNotBeFound("vendorCRNumber.contains=" + UPDATED_VENDOR_CR_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCRNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCRNumber does not contain DEFAULT_VENDOR_CR_NUMBER
        defaultVendorShouldNotBeFound("vendorCRNumber.doesNotContain=" + DEFAULT_VENDOR_CR_NUMBER);

        // Get all the vendorList where vendorCRNumber does not contain UPDATED_VENDOR_CR_NUMBER
        defaultVendorShouldBeFound("vendorCRNumber.doesNotContain=" + UPDATED_VENDOR_CR_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorVATNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorVATNumber equals to DEFAULT_VENDOR_VAT_NUMBER
        defaultVendorShouldBeFound("vendorVATNumber.equals=" + DEFAULT_VENDOR_VAT_NUMBER);

        // Get all the vendorList where vendorVATNumber equals to UPDATED_VENDOR_VAT_NUMBER
        defaultVendorShouldNotBeFound("vendorVATNumber.equals=" + UPDATED_VENDOR_VAT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorVATNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorVATNumber in DEFAULT_VENDOR_VAT_NUMBER or UPDATED_VENDOR_VAT_NUMBER
        defaultVendorShouldBeFound("vendorVATNumber.in=" + DEFAULT_VENDOR_VAT_NUMBER + "," + UPDATED_VENDOR_VAT_NUMBER);

        // Get all the vendorList where vendorVATNumber equals to UPDATED_VENDOR_VAT_NUMBER
        defaultVendorShouldNotBeFound("vendorVATNumber.in=" + UPDATED_VENDOR_VAT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorVATNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorVATNumber is not null
        defaultVendorShouldBeFound("vendorVATNumber.specified=true");

        // Get all the vendorList where vendorVATNumber is null
        defaultVendorShouldNotBeFound("vendorVATNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorVATNumberContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorVATNumber contains DEFAULT_VENDOR_VAT_NUMBER
        defaultVendorShouldBeFound("vendorVATNumber.contains=" + DEFAULT_VENDOR_VAT_NUMBER);

        // Get all the vendorList where vendorVATNumber contains UPDATED_VENDOR_VAT_NUMBER
        defaultVendorShouldNotBeFound("vendorVATNumber.contains=" + UPDATED_VENDOR_VAT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorVATNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorVATNumber does not contain DEFAULT_VENDOR_VAT_NUMBER
        defaultVendorShouldNotBeFound("vendorVATNumber.doesNotContain=" + DEFAULT_VENDOR_VAT_NUMBER);

        // Get all the vendorList where vendorVATNumber does not contain UPDATED_VENDOR_VAT_NUMBER
        defaultVendorShouldBeFound("vendorVATNumber.doesNotContain=" + UPDATED_VENDOR_VAT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorType equals to DEFAULT_VENDOR_TYPE
        defaultVendorShouldBeFound("vendorType.equals=" + DEFAULT_VENDOR_TYPE);

        // Get all the vendorList where vendorType equals to UPDATED_VENDOR_TYPE
        defaultVendorShouldNotBeFound("vendorType.equals=" + UPDATED_VENDOR_TYPE);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorType in DEFAULT_VENDOR_TYPE or UPDATED_VENDOR_TYPE
        defaultVendorShouldBeFound("vendorType.in=" + DEFAULT_VENDOR_TYPE + "," + UPDATED_VENDOR_TYPE);

        // Get all the vendorList where vendorType equals to UPDATED_VENDOR_TYPE
        defaultVendorShouldNotBeFound("vendorType.in=" + UPDATED_VENDOR_TYPE);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorType is not null
        defaultVendorShouldBeFound("vendorType.specified=true");

        // Get all the vendorList where vendorType is null
        defaultVendorShouldNotBeFound("vendorType.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCategory equals to DEFAULT_VENDOR_CATEGORY
        defaultVendorShouldBeFound("vendorCategory.equals=" + DEFAULT_VENDOR_CATEGORY);

        // Get all the vendorList where vendorCategory equals to UPDATED_VENDOR_CATEGORY
        defaultVendorShouldNotBeFound("vendorCategory.equals=" + UPDATED_VENDOR_CATEGORY);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCategory in DEFAULT_VENDOR_CATEGORY or UPDATED_VENDOR_CATEGORY
        defaultVendorShouldBeFound("vendorCategory.in=" + DEFAULT_VENDOR_CATEGORY + "," + UPDATED_VENDOR_CATEGORY);

        // Get all the vendorList where vendorCategory equals to UPDATED_VENDOR_CATEGORY
        defaultVendorShouldNotBeFound("vendorCategory.in=" + UPDATED_VENDOR_CATEGORY);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorCategory is not null
        defaultVendorShouldBeFound("vendorCategory.specified=true");

        // Get all the vendorList where vendorCategory is null
        defaultVendorShouldNotBeFound("vendorCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorEstablishmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorEstablishmentDate equals to DEFAULT_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldBeFound("vendorEstablishmentDate.equals=" + DEFAULT_VENDOR_ESTABLISHMENT_DATE);

        // Get all the vendorList where vendorEstablishmentDate equals to UPDATED_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldNotBeFound("vendorEstablishmentDate.equals=" + UPDATED_VENDOR_ESTABLISHMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorEstablishmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorEstablishmentDate in DEFAULT_VENDOR_ESTABLISHMENT_DATE or UPDATED_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldBeFound(
            "vendorEstablishmentDate.in=" + DEFAULT_VENDOR_ESTABLISHMENT_DATE + "," + UPDATED_VENDOR_ESTABLISHMENT_DATE
        );

        // Get all the vendorList where vendorEstablishmentDate equals to UPDATED_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldNotBeFound("vendorEstablishmentDate.in=" + UPDATED_VENDOR_ESTABLISHMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorEstablishmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorEstablishmentDate is not null
        defaultVendorShouldBeFound("vendorEstablishmentDate.specified=true");

        // Get all the vendorList where vendorEstablishmentDate is null
        defaultVendorShouldNotBeFound("vendorEstablishmentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorEstablishmentDateContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorEstablishmentDate contains DEFAULT_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldBeFound("vendorEstablishmentDate.contains=" + DEFAULT_VENDOR_ESTABLISHMENT_DATE);

        // Get all the vendorList where vendorEstablishmentDate contains UPDATED_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldNotBeFound("vendorEstablishmentDate.contains=" + UPDATED_VENDOR_ESTABLISHMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorEstablishmentDateNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorEstablishmentDate does not contain DEFAULT_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldNotBeFound("vendorEstablishmentDate.doesNotContain=" + DEFAULT_VENDOR_ESTABLISHMENT_DATE);

        // Get all the vendorList where vendorEstablishmentDate does not contain UPDATED_VENDOR_ESTABLISHMENT_DATE
        defaultVendorShouldBeFound("vendorEstablishmentDate.doesNotContain=" + UPDATED_VENDOR_ESTABLISHMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height equals to DEFAULT_HEIGHT
        defaultVendorShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the vendorList where height equals to UPDATED_HEIGHT
        defaultVendorShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultVendorShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the vendorList where height equals to UPDATED_HEIGHT
        defaultVendorShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height is not null
        defaultVendorShouldBeFound("height.specified=true");

        // Get all the vendorList where height is null
        defaultVendorShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height is greater than or equal to DEFAULT_HEIGHT
        defaultVendorShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the vendorList where height is greater than or equal to UPDATED_HEIGHT
        defaultVendorShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height is less than or equal to DEFAULT_HEIGHT
        defaultVendorShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the vendorList where height is less than or equal to SMALLER_HEIGHT
        defaultVendorShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height is less than DEFAULT_HEIGHT
        defaultVendorShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the vendorList where height is less than UPDATED_HEIGHT
        defaultVendorShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where height is greater than DEFAULT_HEIGHT
        defaultVendorShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the vendorList where height is greater than SMALLER_HEIGHT
        defaultVendorShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width equals to DEFAULT_WIDTH
        defaultVendorShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the vendorList where width equals to UPDATED_WIDTH
        defaultVendorShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultVendorShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the vendorList where width equals to UPDATED_WIDTH
        defaultVendorShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width is not null
        defaultVendorShouldBeFound("width.specified=true");

        // Get all the vendorList where width is null
        defaultVendorShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width is greater than or equal to DEFAULT_WIDTH
        defaultVendorShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the vendorList where width is greater than or equal to UPDATED_WIDTH
        defaultVendorShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width is less than or equal to DEFAULT_WIDTH
        defaultVendorShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the vendorList where width is less than or equal to SMALLER_WIDTH
        defaultVendorShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width is less than DEFAULT_WIDTH
        defaultVendorShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the vendorList where width is less than UPDATED_WIDTH
        defaultVendorShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where width is greater than DEFAULT_WIDTH
        defaultVendorShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the vendorList where width is greater than SMALLER_WIDTH
        defaultVendorShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllVendorsByTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where taken equals to DEFAULT_TAKEN
        defaultVendorShouldBeFound("taken.equals=" + DEFAULT_TAKEN);

        // Get all the vendorList where taken equals to UPDATED_TAKEN
        defaultVendorShouldNotBeFound("taken.equals=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllVendorsByTakenIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where taken in DEFAULT_TAKEN or UPDATED_TAKEN
        defaultVendorShouldBeFound("taken.in=" + DEFAULT_TAKEN + "," + UPDATED_TAKEN);

        // Get all the vendorList where taken equals to UPDATED_TAKEN
        defaultVendorShouldNotBeFound("taken.in=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllVendorsByTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where taken is not null
        defaultVendorShouldBeFound("taken.specified=true");

        // Get all the vendorList where taken is null
        defaultVendorShouldNotBeFound("taken.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByUploadedIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where uploaded equals to DEFAULT_UPLOADED
        defaultVendorShouldBeFound("uploaded.equals=" + DEFAULT_UPLOADED);

        // Get all the vendorList where uploaded equals to UPDATED_UPLOADED
        defaultVendorShouldNotBeFound("uploaded.equals=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllVendorsByUploadedIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where uploaded in DEFAULT_UPLOADED or UPDATED_UPLOADED
        defaultVendorShouldBeFound("uploaded.in=" + DEFAULT_UPLOADED + "," + UPDATED_UPLOADED);

        // Get all the vendorList where uploaded equals to UPDATED_UPLOADED
        defaultVendorShouldNotBeFound("uploaded.in=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllVendorsByUploadedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where uploaded is not null
        defaultVendorShouldBeFound("uploaded.specified=true");

        // Get all the vendorList where uploaded is null
        defaultVendorShouldNotBeFound("uploaded.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactFullName equals to DEFAULT_CONTACT_FULL_NAME
        defaultVendorShouldBeFound("contactFullName.equals=" + DEFAULT_CONTACT_FULL_NAME);

        // Get all the vendorList where contactFullName equals to UPDATED_CONTACT_FULL_NAME
        defaultVendorShouldNotBeFound("contactFullName.equals=" + UPDATED_CONTACT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByContactFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactFullName in DEFAULT_CONTACT_FULL_NAME or UPDATED_CONTACT_FULL_NAME
        defaultVendorShouldBeFound("contactFullName.in=" + DEFAULT_CONTACT_FULL_NAME + "," + UPDATED_CONTACT_FULL_NAME);

        // Get all the vendorList where contactFullName equals to UPDATED_CONTACT_FULL_NAME
        defaultVendorShouldNotBeFound("contactFullName.in=" + UPDATED_CONTACT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByContactFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactFullName is not null
        defaultVendorShouldBeFound("contactFullName.specified=true");

        // Get all the vendorList where contactFullName is null
        defaultVendorShouldNotBeFound("contactFullName.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactFullNameContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactFullName contains DEFAULT_CONTACT_FULL_NAME
        defaultVendorShouldBeFound("contactFullName.contains=" + DEFAULT_CONTACT_FULL_NAME);

        // Get all the vendorList where contactFullName contains UPDATED_CONTACT_FULL_NAME
        defaultVendorShouldNotBeFound("contactFullName.contains=" + UPDATED_CONTACT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByContactFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactFullName does not contain DEFAULT_CONTACT_FULL_NAME
        defaultVendorShouldNotBeFound("contactFullName.doesNotContain=" + DEFAULT_CONTACT_FULL_NAME);

        // Get all the vendorList where contactFullName does not contain UPDATED_CONTACT_FULL_NAME
        defaultVendorShouldBeFound("contactFullName.doesNotContain=" + UPDATED_CONTACT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByContactEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactEmailAddress equals to DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldBeFound("contactEmailAddress.equals=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the vendorList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldNotBeFound("contactEmailAddress.equals=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllVendorsByContactEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactEmailAddress in DEFAULT_CONTACT_EMAIL_ADDRESS or UPDATED_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldBeFound("contactEmailAddress.in=" + DEFAULT_CONTACT_EMAIL_ADDRESS + "," + UPDATED_CONTACT_EMAIL_ADDRESS);

        // Get all the vendorList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldNotBeFound("contactEmailAddress.in=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllVendorsByContactEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactEmailAddress is not null
        defaultVendorShouldBeFound("contactEmailAddress.specified=true");

        // Get all the vendorList where contactEmailAddress is null
        defaultVendorShouldNotBeFound("contactEmailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactEmailAddress contains DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldBeFound("contactEmailAddress.contains=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the vendorList where contactEmailAddress contains UPDATED_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldNotBeFound("contactEmailAddress.contains=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllVendorsByContactEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactEmailAddress does not contain DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldNotBeFound("contactEmailAddress.doesNotContain=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the vendorList where contactEmailAddress does not contain UPDATED_CONTACT_EMAIL_ADDRESS
        defaultVendorShouldBeFound("contactEmailAddress.doesNotContain=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryPhoneNo equals to DEFAULT_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldBeFound("contactPrimaryPhoneNo.equals=" + DEFAULT_CONTACT_PRIMARY_PHONE_NO);

        // Get all the vendorList where contactPrimaryPhoneNo equals to UPDATED_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactPrimaryPhoneNo.equals=" + UPDATED_CONTACT_PRIMARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryPhoneNo in DEFAULT_CONTACT_PRIMARY_PHONE_NO or UPDATED_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldBeFound("contactPrimaryPhoneNo.in=" + DEFAULT_CONTACT_PRIMARY_PHONE_NO + "," + UPDATED_CONTACT_PRIMARY_PHONE_NO);

        // Get all the vendorList where contactPrimaryPhoneNo equals to UPDATED_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactPrimaryPhoneNo.in=" + UPDATED_CONTACT_PRIMARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryPhoneNo is not null
        defaultVendorShouldBeFound("contactPrimaryPhoneNo.specified=true");

        // Get all the vendorList where contactPrimaryPhoneNo is null
        defaultVendorShouldNotBeFound("contactPrimaryPhoneNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryPhoneNo contains DEFAULT_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldBeFound("contactPrimaryPhoneNo.contains=" + DEFAULT_CONTACT_PRIMARY_PHONE_NO);

        // Get all the vendorList where contactPrimaryPhoneNo contains UPDATED_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactPrimaryPhoneNo.contains=" + UPDATED_CONTACT_PRIMARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryPhoneNo does not contain DEFAULT_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactPrimaryPhoneNo.doesNotContain=" + DEFAULT_CONTACT_PRIMARY_PHONE_NO);

        // Get all the vendorList where contactPrimaryPhoneNo does not contain UPDATED_CONTACT_PRIMARY_PHONE_NO
        defaultVendorShouldBeFound("contactPrimaryPhoneNo.doesNotContain=" + UPDATED_CONTACT_PRIMARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryFaxNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryFaxNo equals to DEFAULT_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldBeFound("contactPrimaryFaxNo.equals=" + DEFAULT_CONTACT_PRIMARY_FAX_NO);

        // Get all the vendorList where contactPrimaryFaxNo equals to UPDATED_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldNotBeFound("contactPrimaryFaxNo.equals=" + UPDATED_CONTACT_PRIMARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryFaxNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryFaxNo in DEFAULT_CONTACT_PRIMARY_FAX_NO or UPDATED_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldBeFound("contactPrimaryFaxNo.in=" + DEFAULT_CONTACT_PRIMARY_FAX_NO + "," + UPDATED_CONTACT_PRIMARY_FAX_NO);

        // Get all the vendorList where contactPrimaryFaxNo equals to UPDATED_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldNotBeFound("contactPrimaryFaxNo.in=" + UPDATED_CONTACT_PRIMARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryFaxNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryFaxNo is not null
        defaultVendorShouldBeFound("contactPrimaryFaxNo.specified=true");

        // Get all the vendorList where contactPrimaryFaxNo is null
        defaultVendorShouldNotBeFound("contactPrimaryFaxNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryFaxNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryFaxNo contains DEFAULT_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldBeFound("contactPrimaryFaxNo.contains=" + DEFAULT_CONTACT_PRIMARY_FAX_NO);

        // Get all the vendorList where contactPrimaryFaxNo contains UPDATED_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldNotBeFound("contactPrimaryFaxNo.contains=" + UPDATED_CONTACT_PRIMARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactPrimaryFaxNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactPrimaryFaxNo does not contain DEFAULT_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldNotBeFound("contactPrimaryFaxNo.doesNotContain=" + DEFAULT_CONTACT_PRIMARY_FAX_NO);

        // Get all the vendorList where contactPrimaryFaxNo does not contain UPDATED_CONTACT_PRIMARY_FAX_NO
        defaultVendorShouldBeFound("contactPrimaryFaxNo.doesNotContain=" + UPDATED_CONTACT_PRIMARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryPhoneNo equals to DEFAULT_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldBeFound("contactSecondaryPhoneNo.equals=" + DEFAULT_CONTACT_SECONDARY_PHONE_NO);

        // Get all the vendorList where contactSecondaryPhoneNo equals to UPDATED_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactSecondaryPhoneNo.equals=" + UPDATED_CONTACT_SECONDARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryPhoneNo in DEFAULT_CONTACT_SECONDARY_PHONE_NO or UPDATED_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldBeFound(
            "contactSecondaryPhoneNo.in=" + DEFAULT_CONTACT_SECONDARY_PHONE_NO + "," + UPDATED_CONTACT_SECONDARY_PHONE_NO
        );

        // Get all the vendorList where contactSecondaryPhoneNo equals to UPDATED_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactSecondaryPhoneNo.in=" + UPDATED_CONTACT_SECONDARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryPhoneNo is not null
        defaultVendorShouldBeFound("contactSecondaryPhoneNo.specified=true");

        // Get all the vendorList where contactSecondaryPhoneNo is null
        defaultVendorShouldNotBeFound("contactSecondaryPhoneNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryPhoneNo contains DEFAULT_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldBeFound("contactSecondaryPhoneNo.contains=" + DEFAULT_CONTACT_SECONDARY_PHONE_NO);

        // Get all the vendorList where contactSecondaryPhoneNo contains UPDATED_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactSecondaryPhoneNo.contains=" + UPDATED_CONTACT_SECONDARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryPhoneNo does not contain DEFAULT_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldNotBeFound("contactSecondaryPhoneNo.doesNotContain=" + DEFAULT_CONTACT_SECONDARY_PHONE_NO);

        // Get all the vendorList where contactSecondaryPhoneNo does not contain UPDATED_CONTACT_SECONDARY_PHONE_NO
        defaultVendorShouldBeFound("contactSecondaryPhoneNo.doesNotContain=" + UPDATED_CONTACT_SECONDARY_PHONE_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryFaxNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryFaxNo equals to DEFAULT_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldBeFound("contactSecondaryFaxNo.equals=" + DEFAULT_CONTACT_SECONDARY_FAX_NO);

        // Get all the vendorList where contactSecondaryFaxNo equals to UPDATED_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldNotBeFound("contactSecondaryFaxNo.equals=" + UPDATED_CONTACT_SECONDARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryFaxNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryFaxNo in DEFAULT_CONTACT_SECONDARY_FAX_NO or UPDATED_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldBeFound("contactSecondaryFaxNo.in=" + DEFAULT_CONTACT_SECONDARY_FAX_NO + "," + UPDATED_CONTACT_SECONDARY_FAX_NO);

        // Get all the vendorList where contactSecondaryFaxNo equals to UPDATED_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldNotBeFound("contactSecondaryFaxNo.in=" + UPDATED_CONTACT_SECONDARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryFaxNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryFaxNo is not null
        defaultVendorShouldBeFound("contactSecondaryFaxNo.specified=true");

        // Get all the vendorList where contactSecondaryFaxNo is null
        defaultVendorShouldNotBeFound("contactSecondaryFaxNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryFaxNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryFaxNo contains DEFAULT_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldBeFound("contactSecondaryFaxNo.contains=" + DEFAULT_CONTACT_SECONDARY_FAX_NO);

        // Get all the vendorList where contactSecondaryFaxNo contains UPDATED_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldNotBeFound("contactSecondaryFaxNo.contains=" + UPDATED_CONTACT_SECONDARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByContactSecondaryFaxNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactSecondaryFaxNo does not contain DEFAULT_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldNotBeFound("contactSecondaryFaxNo.doesNotContain=" + DEFAULT_CONTACT_SECONDARY_FAX_NO);

        // Get all the vendorList where contactSecondaryFaxNo does not contain UPDATED_CONTACT_SECONDARY_FAX_NO
        defaultVendorShouldBeFound("contactSecondaryFaxNo.doesNotContain=" + UPDATED_CONTACT_SECONDARY_FAX_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeLocation equals to DEFAULT_OFFICE_LOCATION
        defaultVendorShouldBeFound("officeLocation.equals=" + DEFAULT_OFFICE_LOCATION);

        // Get all the vendorList where officeLocation equals to UPDATED_OFFICE_LOCATION
        defaultVendorShouldNotBeFound("officeLocation.equals=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeLocationIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeLocation in DEFAULT_OFFICE_LOCATION or UPDATED_OFFICE_LOCATION
        defaultVendorShouldBeFound("officeLocation.in=" + DEFAULT_OFFICE_LOCATION + "," + UPDATED_OFFICE_LOCATION);

        // Get all the vendorList where officeLocation equals to UPDATED_OFFICE_LOCATION
        defaultVendorShouldNotBeFound("officeLocation.in=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeLocation is not null
        defaultVendorShouldBeFound("officeLocation.specified=true");

        // Get all the vendorList where officeLocation is null
        defaultVendorShouldNotBeFound("officeLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeLocationContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeLocation contains DEFAULT_OFFICE_LOCATION
        defaultVendorShouldBeFound("officeLocation.contains=" + DEFAULT_OFFICE_LOCATION);

        // Get all the vendorList where officeLocation contains UPDATED_OFFICE_LOCATION
        defaultVendorShouldNotBeFound("officeLocation.contains=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeLocationNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeLocation does not contain DEFAULT_OFFICE_LOCATION
        defaultVendorShouldNotBeFound("officeLocation.doesNotContain=" + DEFAULT_OFFICE_LOCATION);

        // Get all the vendorList where officeLocation does not contain UPDATED_OFFICE_LOCATION
        defaultVendorShouldBeFound("officeLocation.doesNotContain=" + UPDATED_OFFICE_LOCATION);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeFunctionalityIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeFunctionality equals to DEFAULT_OFFICE_FUNCTIONALITY
        defaultVendorShouldBeFound("officeFunctionality.equals=" + DEFAULT_OFFICE_FUNCTIONALITY);

        // Get all the vendorList where officeFunctionality equals to UPDATED_OFFICE_FUNCTIONALITY
        defaultVendorShouldNotBeFound("officeFunctionality.equals=" + UPDATED_OFFICE_FUNCTIONALITY);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeFunctionalityIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeFunctionality in DEFAULT_OFFICE_FUNCTIONALITY or UPDATED_OFFICE_FUNCTIONALITY
        defaultVendorShouldBeFound("officeFunctionality.in=" + DEFAULT_OFFICE_FUNCTIONALITY + "," + UPDATED_OFFICE_FUNCTIONALITY);

        // Get all the vendorList where officeFunctionality equals to UPDATED_OFFICE_FUNCTIONALITY
        defaultVendorShouldNotBeFound("officeFunctionality.in=" + UPDATED_OFFICE_FUNCTIONALITY);
    }

    @Test
    @Transactional
    void getAllVendorsByOfficeFunctionalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where officeFunctionality is not null
        defaultVendorShouldBeFound("officeFunctionality.specified=true");

        // Get all the vendorList where officeFunctionality is null
        defaultVendorShouldNotBeFound("officeFunctionality.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByWebsiteURLIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where websiteURL equals to DEFAULT_WEBSITE_URL
        defaultVendorShouldBeFound("websiteURL.equals=" + DEFAULT_WEBSITE_URL);

        // Get all the vendorList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultVendorShouldNotBeFound("websiteURL.equals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllVendorsByWebsiteURLIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where websiteURL in DEFAULT_WEBSITE_URL or UPDATED_WEBSITE_URL
        defaultVendorShouldBeFound("websiteURL.in=" + DEFAULT_WEBSITE_URL + "," + UPDATED_WEBSITE_URL);

        // Get all the vendorList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultVendorShouldNotBeFound("websiteURL.in=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllVendorsByWebsiteURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where websiteURL is not null
        defaultVendorShouldBeFound("websiteURL.specified=true");

        // Get all the vendorList where websiteURL is null
        defaultVendorShouldNotBeFound("websiteURL.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByWebsiteURLContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where websiteURL contains DEFAULT_WEBSITE_URL
        defaultVendorShouldBeFound("websiteURL.contains=" + DEFAULT_WEBSITE_URL);

        // Get all the vendorList where websiteURL contains UPDATED_WEBSITE_URL
        defaultVendorShouldNotBeFound("websiteURL.contains=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllVendorsByWebsiteURLNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where websiteURL does not contain DEFAULT_WEBSITE_URL
        defaultVendorShouldNotBeFound("websiteURL.doesNotContain=" + DEFAULT_WEBSITE_URL);

        // Get all the vendorList where websiteURL does not contain UPDATED_WEBSITE_URL
        defaultVendorShouldBeFound("websiteURL.doesNotContain=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllVendorsByBuildingNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where buildingNo equals to DEFAULT_BUILDING_NO
        defaultVendorShouldBeFound("buildingNo.equals=" + DEFAULT_BUILDING_NO);

        // Get all the vendorList where buildingNo equals to UPDATED_BUILDING_NO
        defaultVendorShouldNotBeFound("buildingNo.equals=" + UPDATED_BUILDING_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByBuildingNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where buildingNo in DEFAULT_BUILDING_NO or UPDATED_BUILDING_NO
        defaultVendorShouldBeFound("buildingNo.in=" + DEFAULT_BUILDING_NO + "," + UPDATED_BUILDING_NO);

        // Get all the vendorList where buildingNo equals to UPDATED_BUILDING_NO
        defaultVendorShouldNotBeFound("buildingNo.in=" + UPDATED_BUILDING_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByBuildingNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where buildingNo is not null
        defaultVendorShouldBeFound("buildingNo.specified=true");

        // Get all the vendorList where buildingNo is null
        defaultVendorShouldNotBeFound("buildingNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByBuildingNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where buildingNo contains DEFAULT_BUILDING_NO
        defaultVendorShouldBeFound("buildingNo.contains=" + DEFAULT_BUILDING_NO);

        // Get all the vendorList where buildingNo contains UPDATED_BUILDING_NO
        defaultVendorShouldNotBeFound("buildingNo.contains=" + UPDATED_BUILDING_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByBuildingNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where buildingNo does not contain DEFAULT_BUILDING_NO
        defaultVendorShouldNotBeFound("buildingNo.doesNotContain=" + DEFAULT_BUILDING_NO);

        // Get all the vendorList where buildingNo does not contain UPDATED_BUILDING_NO
        defaultVendorShouldBeFound("buildingNo.doesNotContain=" + UPDATED_BUILDING_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorStreetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorStreetName equals to DEFAULT_VENDOR_STREET_NAME
        defaultVendorShouldBeFound("vendorStreetName.equals=" + DEFAULT_VENDOR_STREET_NAME);

        // Get all the vendorList where vendorStreetName equals to UPDATED_VENDOR_STREET_NAME
        defaultVendorShouldNotBeFound("vendorStreetName.equals=" + UPDATED_VENDOR_STREET_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorStreetNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorStreetName in DEFAULT_VENDOR_STREET_NAME or UPDATED_VENDOR_STREET_NAME
        defaultVendorShouldBeFound("vendorStreetName.in=" + DEFAULT_VENDOR_STREET_NAME + "," + UPDATED_VENDOR_STREET_NAME);

        // Get all the vendorList where vendorStreetName equals to UPDATED_VENDOR_STREET_NAME
        defaultVendorShouldNotBeFound("vendorStreetName.in=" + UPDATED_VENDOR_STREET_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorStreetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorStreetName is not null
        defaultVendorShouldBeFound("vendorStreetName.specified=true");

        // Get all the vendorList where vendorStreetName is null
        defaultVendorShouldNotBeFound("vendorStreetName.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByVendorStreetNameContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorStreetName contains DEFAULT_VENDOR_STREET_NAME
        defaultVendorShouldBeFound("vendorStreetName.contains=" + DEFAULT_VENDOR_STREET_NAME);

        // Get all the vendorList where vendorStreetName contains UPDATED_VENDOR_STREET_NAME
        defaultVendorShouldNotBeFound("vendorStreetName.contains=" + UPDATED_VENDOR_STREET_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByVendorStreetNameNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where vendorStreetName does not contain DEFAULT_VENDOR_STREET_NAME
        defaultVendorShouldNotBeFound("vendorStreetName.doesNotContain=" + DEFAULT_VENDOR_STREET_NAME);

        // Get all the vendorList where vendorStreetName does not contain UPDATED_VENDOR_STREET_NAME
        defaultVendorShouldBeFound("vendorStreetName.doesNotContain=" + UPDATED_VENDOR_STREET_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where zipCode equals to DEFAULT_ZIP_CODE
        defaultVendorShouldBeFound("zipCode.equals=" + DEFAULT_ZIP_CODE);

        // Get all the vendorList where zipCode equals to UPDATED_ZIP_CODE
        defaultVendorShouldNotBeFound("zipCode.equals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllVendorsByZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where zipCode in DEFAULT_ZIP_CODE or UPDATED_ZIP_CODE
        defaultVendorShouldBeFound("zipCode.in=" + DEFAULT_ZIP_CODE + "," + UPDATED_ZIP_CODE);

        // Get all the vendorList where zipCode equals to UPDATED_ZIP_CODE
        defaultVendorShouldNotBeFound("zipCode.in=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllVendorsByZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where zipCode is not null
        defaultVendorShouldBeFound("zipCode.specified=true");

        // Get all the vendorList where zipCode is null
        defaultVendorShouldNotBeFound("zipCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByZipCodeContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where zipCode contains DEFAULT_ZIP_CODE
        defaultVendorShouldBeFound("zipCode.contains=" + DEFAULT_ZIP_CODE);

        // Get all the vendorList where zipCode contains UPDATED_ZIP_CODE
        defaultVendorShouldNotBeFound("zipCode.contains=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllVendorsByZipCodeNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where zipCode does not contain DEFAULT_ZIP_CODE
        defaultVendorShouldNotBeFound("zipCode.doesNotContain=" + DEFAULT_ZIP_CODE);

        // Get all the vendorList where zipCode does not contain UPDATED_ZIP_CODE
        defaultVendorShouldBeFound("zipCode.doesNotContain=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void getAllVendorsByDistrictNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where districtName equals to DEFAULT_DISTRICT_NAME
        defaultVendorShouldBeFound("districtName.equals=" + DEFAULT_DISTRICT_NAME);

        // Get all the vendorList where districtName equals to UPDATED_DISTRICT_NAME
        defaultVendorShouldNotBeFound("districtName.equals=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByDistrictNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where districtName in DEFAULT_DISTRICT_NAME or UPDATED_DISTRICT_NAME
        defaultVendorShouldBeFound("districtName.in=" + DEFAULT_DISTRICT_NAME + "," + UPDATED_DISTRICT_NAME);

        // Get all the vendorList where districtName equals to UPDATED_DISTRICT_NAME
        defaultVendorShouldNotBeFound("districtName.in=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByDistrictNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where districtName is not null
        defaultVendorShouldBeFound("districtName.specified=true");

        // Get all the vendorList where districtName is null
        defaultVendorShouldNotBeFound("districtName.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByDistrictNameContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where districtName contains DEFAULT_DISTRICT_NAME
        defaultVendorShouldBeFound("districtName.contains=" + DEFAULT_DISTRICT_NAME);

        // Get all the vendorList where districtName contains UPDATED_DISTRICT_NAME
        defaultVendorShouldNotBeFound("districtName.contains=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByDistrictNameNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where districtName does not contain DEFAULT_DISTRICT_NAME
        defaultVendorShouldNotBeFound("districtName.doesNotContain=" + DEFAULT_DISTRICT_NAME);

        // Get all the vendorList where districtName does not contain UPDATED_DISTRICT_NAME
        defaultVendorShouldBeFound("districtName.doesNotContain=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByAdditionalNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where additionalNo equals to DEFAULT_ADDITIONAL_NO
        defaultVendorShouldBeFound("additionalNo.equals=" + DEFAULT_ADDITIONAL_NO);

        // Get all the vendorList where additionalNo equals to UPDATED_ADDITIONAL_NO
        defaultVendorShouldNotBeFound("additionalNo.equals=" + UPDATED_ADDITIONAL_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByAdditionalNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where additionalNo in DEFAULT_ADDITIONAL_NO or UPDATED_ADDITIONAL_NO
        defaultVendorShouldBeFound("additionalNo.in=" + DEFAULT_ADDITIONAL_NO + "," + UPDATED_ADDITIONAL_NO);

        // Get all the vendorList where additionalNo equals to UPDATED_ADDITIONAL_NO
        defaultVendorShouldNotBeFound("additionalNo.in=" + UPDATED_ADDITIONAL_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByAdditionalNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where additionalNo is not null
        defaultVendorShouldBeFound("additionalNo.specified=true");

        // Get all the vendorList where additionalNo is null
        defaultVendorShouldNotBeFound("additionalNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByAdditionalNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where additionalNo contains DEFAULT_ADDITIONAL_NO
        defaultVendorShouldBeFound("additionalNo.contains=" + DEFAULT_ADDITIONAL_NO);

        // Get all the vendorList where additionalNo contains UPDATED_ADDITIONAL_NO
        defaultVendorShouldNotBeFound("additionalNo.contains=" + UPDATED_ADDITIONAL_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByAdditionalNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where additionalNo does not contain DEFAULT_ADDITIONAL_NO
        defaultVendorShouldNotBeFound("additionalNo.doesNotContain=" + DEFAULT_ADDITIONAL_NO);

        // Get all the vendorList where additionalNo does not contain UPDATED_ADDITIONAL_NO
        defaultVendorShouldBeFound("additionalNo.doesNotContain=" + UPDATED_ADDITIONAL_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByCityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cityName equals to DEFAULT_CITY_NAME
        defaultVendorShouldBeFound("cityName.equals=" + DEFAULT_CITY_NAME);

        // Get all the vendorList where cityName equals to UPDATED_CITY_NAME
        defaultVendorShouldNotBeFound("cityName.equals=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByCityNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cityName in DEFAULT_CITY_NAME or UPDATED_CITY_NAME
        defaultVendorShouldBeFound("cityName.in=" + DEFAULT_CITY_NAME + "," + UPDATED_CITY_NAME);

        // Get all the vendorList where cityName equals to UPDATED_CITY_NAME
        defaultVendorShouldNotBeFound("cityName.in=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByCityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cityName is not null
        defaultVendorShouldBeFound("cityName.specified=true");

        // Get all the vendorList where cityName is null
        defaultVendorShouldNotBeFound("cityName.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByCityNameContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cityName contains DEFAULT_CITY_NAME
        defaultVendorShouldBeFound("cityName.contains=" + DEFAULT_CITY_NAME);

        // Get all the vendorList where cityName contains UPDATED_CITY_NAME
        defaultVendorShouldNotBeFound("cityName.contains=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByCityNameNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cityName does not contain DEFAULT_CITY_NAME
        defaultVendorShouldNotBeFound("cityName.doesNotContain=" + DEFAULT_CITY_NAME);

        // Get all the vendorList where cityName does not contain UPDATED_CITY_NAME
        defaultVendorShouldBeFound("cityName.doesNotContain=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllVendorsByUnitNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where unitNo equals to DEFAULT_UNIT_NO
        defaultVendorShouldBeFound("unitNo.equals=" + DEFAULT_UNIT_NO);

        // Get all the vendorList where unitNo equals to UPDATED_UNIT_NO
        defaultVendorShouldNotBeFound("unitNo.equals=" + UPDATED_UNIT_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByUnitNoIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where unitNo in DEFAULT_UNIT_NO or UPDATED_UNIT_NO
        defaultVendorShouldBeFound("unitNo.in=" + DEFAULT_UNIT_NO + "," + UPDATED_UNIT_NO);

        // Get all the vendorList where unitNo equals to UPDATED_UNIT_NO
        defaultVendorShouldNotBeFound("unitNo.in=" + UPDATED_UNIT_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByUnitNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where unitNo is not null
        defaultVendorShouldBeFound("unitNo.specified=true");

        // Get all the vendorList where unitNo is null
        defaultVendorShouldNotBeFound("unitNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByUnitNoContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where unitNo contains DEFAULT_UNIT_NO
        defaultVendorShouldBeFound("unitNo.contains=" + DEFAULT_UNIT_NO);

        // Get all the vendorList where unitNo contains UPDATED_UNIT_NO
        defaultVendorShouldNotBeFound("unitNo.contains=" + UPDATED_UNIT_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByUnitNoNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where unitNo does not contain DEFAULT_UNIT_NO
        defaultVendorShouldNotBeFound("unitNo.doesNotContain=" + DEFAULT_UNIT_NO);

        // Get all the vendorList where unitNo does not contain UPDATED_UNIT_NO
        defaultVendorShouldBeFound("unitNo.doesNotContain=" + UPDATED_UNIT_NO);
    }

    @Test
    @Transactional
    void getAllVendorsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where country equals to DEFAULT_COUNTRY
        defaultVendorShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the vendorList where country equals to UPDATED_COUNTRY
        defaultVendorShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultVendorShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the vendorList where country equals to UPDATED_COUNTRY
        defaultVendorShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where country is not null
        defaultVendorShouldBeFound("country.specified=true");

        // Get all the vendorList where country is null
        defaultVendorShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByCashIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cash equals to DEFAULT_CASH
        defaultVendorShouldBeFound("cash.equals=" + DEFAULT_CASH);

        // Get all the vendorList where cash equals to UPDATED_CASH
        defaultVendorShouldNotBeFound("cash.equals=" + UPDATED_CASH);
    }

    @Test
    @Transactional
    void getAllVendorsByCashIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cash in DEFAULT_CASH or UPDATED_CASH
        defaultVendorShouldBeFound("cash.in=" + DEFAULT_CASH + "," + UPDATED_CASH);

        // Get all the vendorList where cash equals to UPDATED_CASH
        defaultVendorShouldNotBeFound("cash.in=" + UPDATED_CASH);
    }

    @Test
    @Transactional
    void getAllVendorsByCashIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cash is not null
        defaultVendorShouldBeFound("cash.specified=true");

        // Get all the vendorList where cash is null
        defaultVendorShouldNotBeFound("cash.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByCashContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cash contains DEFAULT_CASH
        defaultVendorShouldBeFound("cash.contains=" + DEFAULT_CASH);

        // Get all the vendorList where cash contains UPDATED_CASH
        defaultVendorShouldNotBeFound("cash.contains=" + UPDATED_CASH);
    }

    @Test
    @Transactional
    void getAllVendorsByCashNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where cash does not contain DEFAULT_CASH
        defaultVendorShouldNotBeFound("cash.doesNotContain=" + DEFAULT_CASH);

        // Get all the vendorList where cash does not contain UPDATED_CASH
        defaultVendorShouldBeFound("cash.doesNotContain=" + UPDATED_CASH);
    }

    @Test
    @Transactional
    void getAllVendorsByCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where credit equals to DEFAULT_CREDIT
        defaultVendorShouldBeFound("credit.equals=" + DEFAULT_CREDIT);

        // Get all the vendorList where credit equals to UPDATED_CREDIT
        defaultVendorShouldNotBeFound("credit.equals=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByCreditIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where credit in DEFAULT_CREDIT or UPDATED_CREDIT
        defaultVendorShouldBeFound("credit.in=" + DEFAULT_CREDIT + "," + UPDATED_CREDIT);

        // Get all the vendorList where credit equals to UPDATED_CREDIT
        defaultVendorShouldNotBeFound("credit.in=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where credit is not null
        defaultVendorShouldBeFound("credit.specified=true");

        // Get all the vendorList where credit is null
        defaultVendorShouldNotBeFound("credit.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByCreditContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where credit contains DEFAULT_CREDIT
        defaultVendorShouldBeFound("credit.contains=" + DEFAULT_CREDIT);

        // Get all the vendorList where credit contains UPDATED_CREDIT
        defaultVendorShouldNotBeFound("credit.contains=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByCreditNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where credit does not contain DEFAULT_CREDIT
        defaultVendorShouldNotBeFound("credit.doesNotContain=" + DEFAULT_CREDIT);

        // Get all the vendorList where credit does not contain UPDATED_CREDIT
        defaultVendorShouldBeFound("credit.doesNotContain=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByLetterOfCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where letterOfCredit equals to DEFAULT_LETTER_OF_CREDIT
        defaultVendorShouldBeFound("letterOfCredit.equals=" + DEFAULT_LETTER_OF_CREDIT);

        // Get all the vendorList where letterOfCredit equals to UPDATED_LETTER_OF_CREDIT
        defaultVendorShouldNotBeFound("letterOfCredit.equals=" + UPDATED_LETTER_OF_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByLetterOfCreditIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where letterOfCredit in DEFAULT_LETTER_OF_CREDIT or UPDATED_LETTER_OF_CREDIT
        defaultVendorShouldBeFound("letterOfCredit.in=" + DEFAULT_LETTER_OF_CREDIT + "," + UPDATED_LETTER_OF_CREDIT);

        // Get all the vendorList where letterOfCredit equals to UPDATED_LETTER_OF_CREDIT
        defaultVendorShouldNotBeFound("letterOfCredit.in=" + UPDATED_LETTER_OF_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByLetterOfCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where letterOfCredit is not null
        defaultVendorShouldBeFound("letterOfCredit.specified=true");

        // Get all the vendorList where letterOfCredit is null
        defaultVendorShouldNotBeFound("letterOfCredit.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByLetterOfCreditContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where letterOfCredit contains DEFAULT_LETTER_OF_CREDIT
        defaultVendorShouldBeFound("letterOfCredit.contains=" + DEFAULT_LETTER_OF_CREDIT);

        // Get all the vendorList where letterOfCredit contains UPDATED_LETTER_OF_CREDIT
        defaultVendorShouldNotBeFound("letterOfCredit.contains=" + UPDATED_LETTER_OF_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByLetterOfCreditNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where letterOfCredit does not contain DEFAULT_LETTER_OF_CREDIT
        defaultVendorShouldNotBeFound("letterOfCredit.doesNotContain=" + DEFAULT_LETTER_OF_CREDIT);

        // Get all the vendorList where letterOfCredit does not contain UPDATED_LETTER_OF_CREDIT
        defaultVendorShouldBeFound("letterOfCredit.doesNotContain=" + UPDATED_LETTER_OF_CREDIT);
    }

    @Test
    @Transactional
    void getAllVendorsByOthersIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where others equals to DEFAULT_OTHERS
        defaultVendorShouldBeFound("others.equals=" + DEFAULT_OTHERS);

        // Get all the vendorList where others equals to UPDATED_OTHERS
        defaultVendorShouldNotBeFound("others.equals=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void getAllVendorsByOthersIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where others in DEFAULT_OTHERS or UPDATED_OTHERS
        defaultVendorShouldBeFound("others.in=" + DEFAULT_OTHERS + "," + UPDATED_OTHERS);

        // Get all the vendorList where others equals to UPDATED_OTHERS
        defaultVendorShouldNotBeFound("others.in=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void getAllVendorsByOthersIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where others is not null
        defaultVendorShouldBeFound("others.specified=true");

        // Get all the vendorList where others is null
        defaultVendorShouldNotBeFound("others.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorsByOthersContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where others contains DEFAULT_OTHERS
        defaultVendorShouldBeFound("others.contains=" + DEFAULT_OTHERS);

        // Get all the vendorList where others contains UPDATED_OTHERS
        defaultVendorShouldNotBeFound("others.contains=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void getAllVendorsByOthersNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where others does not contain DEFAULT_OTHERS
        defaultVendorShouldNotBeFound("others.doesNotContain=" + DEFAULT_OTHERS);

        // Get all the vendorList where others does not contain UPDATED_OTHERS
        defaultVendorShouldBeFound("others.doesNotContain=" + UPDATED_OTHERS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendorShouldBeFound(String filter) throws Exception {
        restVendorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendorNameEnglish").value(hasItem(DEFAULT_VENDOR_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].vendorNameArabic").value(hasItem(DEFAULT_VENDOR_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].vendorAccountNumber").value(hasItem(DEFAULT_VENDOR_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorCRNumber").value(hasItem(DEFAULT_VENDOR_CR_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorVATNumber").value(hasItem(DEFAULT_VENDOR_VAT_NUMBER)))
            .andExpect(jsonPath("$.[*].vendorType").value(hasItem(DEFAULT_VENDOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorCategory").value(hasItem(DEFAULT_VENDOR_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].vendorEstablishmentDate").value(hasItem(DEFAULT_VENDOR_ESTABLISHMENT_DATE)))
            .andExpect(jsonPath("$.[*].vendorLogoContentType").value(hasItem(DEFAULT_VENDOR_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].vendorLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_VENDOR_LOGO))))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].contactFullName").value(hasItem(DEFAULT_CONTACT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactPrimaryPhoneNo").value(hasItem(DEFAULT_CONTACT_PRIMARY_PHONE_NO)))
            .andExpect(jsonPath("$.[*].contactPrimaryFaxNo").value(hasItem(DEFAULT_CONTACT_PRIMARY_FAX_NO)))
            .andExpect(jsonPath("$.[*].contactSecondaryPhoneNo").value(hasItem(DEFAULT_CONTACT_SECONDARY_PHONE_NO)))
            .andExpect(jsonPath("$.[*].contactSecondaryFaxNo").value(hasItem(DEFAULT_CONTACT_SECONDARY_FAX_NO)))
            .andExpect(jsonPath("$.[*].officeLocation").value(hasItem(DEFAULT_OFFICE_LOCATION)))
            .andExpect(jsonPath("$.[*].officeFunctionality").value(hasItem(DEFAULT_OFFICE_FUNCTIONALITY.toString())))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].buildingNo").value(hasItem(DEFAULT_BUILDING_NO)))
            .andExpect(jsonPath("$.[*].vendorStreetName").value(hasItem(DEFAULT_VENDOR_STREET_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].additionalNo").value(hasItem(DEFAULT_ADDITIONAL_NO)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].unitNo").value(hasItem(DEFAULT_UNIT_NO)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].googleMap").value(hasItem(DEFAULT_GOOGLE_MAP.toString())))
            .andExpect(jsonPath("$.[*].combinedAddress").value(hasItem(DEFAULT_COMBINED_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].cRCertificateUploadContentType").value(hasItem(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cRCertificateUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_C_R_CERTIFICATE_UPLOAD))))
            .andExpect(jsonPath("$.[*].vATCertificateUploadContentType").value(hasItem(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].vATCertificateUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_V_AT_CERTIFICATE_UPLOAD))))
            .andExpect(jsonPath("$.[*].nationalAddressUploadContentType").value(hasItem(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalAddressUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONAL_ADDRESS_UPLOAD))))
            .andExpect(jsonPath("$.[*].companyProfileUploadContentType").value(hasItem(DEFAULT_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].companyProfileUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_PROFILE_UPLOAD))))
            .andExpect(jsonPath("$.[*].otherUploadContentType").value(hasItem(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_UPLOAD))))
            .andExpect(jsonPath("$.[*].cash").value(hasItem(DEFAULT_CASH)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].letterOfCredit").value(hasItem(DEFAULT_LETTER_OF_CREDIT)))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS)));

        // Check, that the count call also returns 1
        restVendorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendorShouldNotBeFound(String filter) throws Exception {
        restVendorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = vendorRepository.findById(vendor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVendor are not directly saved in db
        em.detach(updatedVendor);
        updatedVendor
            .vendorNameEnglish(UPDATED_VENDOR_NAME_ENGLISH)
            .vendorNameArabic(UPDATED_VENDOR_NAME_ARABIC)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorAccountNumber(UPDATED_VENDOR_ACCOUNT_NUMBER)
            .vendorCRNumber(UPDATED_VENDOR_CR_NUMBER)
            .vendorVATNumber(UPDATED_VENDOR_VAT_NUMBER)
            .vendorType(UPDATED_VENDOR_TYPE)
            .vendorCategory(UPDATED_VENDOR_CATEGORY)
            .vendorEstablishmentDate(UPDATED_VENDOR_ESTABLISHMENT_DATE)
            .vendorLogo(UPDATED_VENDOR_LOGO)
            .vendorLogoContentType(UPDATED_VENDOR_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .contactFullName(UPDATED_CONTACT_FULL_NAME)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .contactPrimaryPhoneNo(UPDATED_CONTACT_PRIMARY_PHONE_NO)
            .contactPrimaryFaxNo(UPDATED_CONTACT_PRIMARY_FAX_NO)
            .contactSecondaryPhoneNo(UPDATED_CONTACT_SECONDARY_PHONE_NO)
            .contactSecondaryFaxNo(UPDATED_CONTACT_SECONDARY_FAX_NO)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .officeFunctionality(UPDATED_OFFICE_FUNCTIONALITY)
            .websiteURL(UPDATED_WEBSITE_URL)
            .buildingNo(UPDATED_BUILDING_NO)
            .vendorStreetName(UPDATED_VENDOR_STREET_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .additionalNo(UPDATED_ADDITIONAL_NO)
            .cityName(UPDATED_CITY_NAME)
            .unitNo(UPDATED_UNIT_NO)
            .country(UPDATED_COUNTRY)
            .googleMap(UPDATED_GOOGLE_MAP)
            .combinedAddress(UPDATED_COMBINED_ADDRESS)
            .cRCertificateUpload(UPDATED_C_R_CERTIFICATE_UPLOAD)
            .cRCertificateUploadContentType(UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .vATCertificateUpload(UPDATED_V_AT_CERTIFICATE_UPLOAD)
            .vATCertificateUploadContentType(UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .nationalAddressUpload(UPDATED_NATIONAL_ADDRESS_UPLOAD)
            .nationalAddressUploadContentType(UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)
            .companyProfileUpload(UPDATED_COMPANY_PROFILE_UPLOAD)
            .companyProfileUploadContentType(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)
            .otherUpload(UPDATED_OTHER_UPLOAD)
            .otherUploadContentType(UPDATED_OTHER_UPLOAD_CONTENT_TYPE)
            .cash(UPDATED_CASH)
            .credit(UPDATED_CREDIT)
            .letterOfCredit(UPDATED_LETTER_OF_CREDIT)
            .others(UPDATED_OTHERS);

        restVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVendor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVendor))
            )
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getVendorNameEnglish()).isEqualTo(UPDATED_VENDOR_NAME_ENGLISH);
        assertThat(testVendor.getVendorNameArabic()).isEqualTo(UPDATED_VENDOR_NAME_ARABIC);
        assertThat(testVendor.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testVendor.getVendorAccountNumber()).isEqualTo(UPDATED_VENDOR_ACCOUNT_NUMBER);
        assertThat(testVendor.getVendorCRNumber()).isEqualTo(UPDATED_VENDOR_CR_NUMBER);
        assertThat(testVendor.getVendorVATNumber()).isEqualTo(UPDATED_VENDOR_VAT_NUMBER);
        assertThat(testVendor.getVendorType()).isEqualTo(UPDATED_VENDOR_TYPE);
        assertThat(testVendor.getVendorCategory()).isEqualTo(UPDATED_VENDOR_CATEGORY);
        assertThat(testVendor.getVendorEstablishmentDate()).isEqualTo(UPDATED_VENDOR_ESTABLISHMENT_DATE);
        assertThat(testVendor.getVendorLogo()).isEqualTo(UPDATED_VENDOR_LOGO);
        assertThat(testVendor.getVendorLogoContentType()).isEqualTo(UPDATED_VENDOR_LOGO_CONTENT_TYPE);
        assertThat(testVendor.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testVendor.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testVendor.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testVendor.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testVendor.getContactFullName()).isEqualTo(UPDATED_CONTACT_FULL_NAME);
        assertThat(testVendor.getContactEmailAddress()).isEqualTo(UPDATED_CONTACT_EMAIL_ADDRESS);
        assertThat(testVendor.getContactPrimaryPhoneNo()).isEqualTo(UPDATED_CONTACT_PRIMARY_PHONE_NO);
        assertThat(testVendor.getContactPrimaryFaxNo()).isEqualTo(UPDATED_CONTACT_PRIMARY_FAX_NO);
        assertThat(testVendor.getContactSecondaryPhoneNo()).isEqualTo(UPDATED_CONTACT_SECONDARY_PHONE_NO);
        assertThat(testVendor.getContactSecondaryFaxNo()).isEqualTo(UPDATED_CONTACT_SECONDARY_FAX_NO);
        assertThat(testVendor.getOfficeLocation()).isEqualTo(UPDATED_OFFICE_LOCATION);
        assertThat(testVendor.getOfficeFunctionality()).isEqualTo(UPDATED_OFFICE_FUNCTIONALITY);
        assertThat(testVendor.getWebsiteURL()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testVendor.getBuildingNo()).isEqualTo(UPDATED_BUILDING_NO);
        assertThat(testVendor.getVendorStreetName()).isEqualTo(UPDATED_VENDOR_STREET_NAME);
        assertThat(testVendor.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testVendor.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testVendor.getAdditionalNo()).isEqualTo(UPDATED_ADDITIONAL_NO);
        assertThat(testVendor.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testVendor.getUnitNo()).isEqualTo(UPDATED_UNIT_NO);
        assertThat(testVendor.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testVendor.getGoogleMap()).isEqualTo(UPDATED_GOOGLE_MAP);
        assertThat(testVendor.getCombinedAddress()).isEqualTo(UPDATED_COMBINED_ADDRESS);
        assertThat(testVendor.getcRCertificateUpload()).isEqualTo(UPDATED_C_R_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getcRCertificateUploadContentType()).isEqualTo(UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getvATCertificateUpload()).isEqualTo(UPDATED_V_AT_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getvATCertificateUploadContentType()).isEqualTo(UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getNationalAddressUpload()).isEqualTo(UPDATED_NATIONAL_ADDRESS_UPLOAD);
        assertThat(testVendor.getNationalAddressUploadContentType()).isEqualTo(UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCompanyProfileUpload()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD);
        assertThat(testVendor.getCompanyProfileUploadContentType()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getOtherUpload()).isEqualTo(UPDATED_OTHER_UPLOAD);
        assertThat(testVendor.getOtherUploadContentType()).isEqualTo(UPDATED_OTHER_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCash()).isEqualTo(UPDATED_CASH);
        assertThat(testVendor.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testVendor.getLetterOfCredit()).isEqualTo(UPDATED_LETTER_OF_CREDIT);
        assertThat(testVendor.getOthers()).isEqualTo(UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void putNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVendorWithPatch() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor using partial update
        Vendor partialUpdatedVendor = new Vendor();
        partialUpdatedVendor.setId(vendor.getId());

        partialUpdatedVendor
            .vendorNameEnglish(UPDATED_VENDOR_NAME_ENGLISH)
            .vendorNameArabic(UPDATED_VENDOR_NAME_ARABIC)
            .vendorAccountNumber(UPDATED_VENDOR_ACCOUNT_NUMBER)
            .vendorCRNumber(UPDATED_VENDOR_CR_NUMBER)
            .vendorType(UPDATED_VENDOR_TYPE)
            .vendorCategory(UPDATED_VENDOR_CATEGORY)
            .vendorEstablishmentDate(UPDATED_VENDOR_ESTABLISHMENT_DATE)
            .height(UPDATED_HEIGHT)
            .uploaded(UPDATED_UPLOADED)
            .contactPrimaryPhoneNo(UPDATED_CONTACT_PRIMARY_PHONE_NO)
            .contactSecondaryPhoneNo(UPDATED_CONTACT_SECONDARY_PHONE_NO)
            .officeFunctionality(UPDATED_OFFICE_FUNCTIONALITY)
            .buildingNo(UPDATED_BUILDING_NO)
            .districtName(UPDATED_DISTRICT_NAME)
            .cityName(UPDATED_CITY_NAME)
            .country(UPDATED_COUNTRY)
            .combinedAddress(UPDATED_COMBINED_ADDRESS)
            .companyProfileUpload(UPDATED_COMPANY_PROFILE_UPLOAD)
            .companyProfileUploadContentType(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)
            .others(UPDATED_OTHERS);

        restVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendor))
            )
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getVendorNameEnglish()).isEqualTo(UPDATED_VENDOR_NAME_ENGLISH);
        assertThat(testVendor.getVendorNameArabic()).isEqualTo(UPDATED_VENDOR_NAME_ARABIC);
        assertThat(testVendor.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testVendor.getVendorAccountNumber()).isEqualTo(UPDATED_VENDOR_ACCOUNT_NUMBER);
        assertThat(testVendor.getVendorCRNumber()).isEqualTo(UPDATED_VENDOR_CR_NUMBER);
        assertThat(testVendor.getVendorVATNumber()).isEqualTo(DEFAULT_VENDOR_VAT_NUMBER);
        assertThat(testVendor.getVendorType()).isEqualTo(UPDATED_VENDOR_TYPE);
        assertThat(testVendor.getVendorCategory()).isEqualTo(UPDATED_VENDOR_CATEGORY);
        assertThat(testVendor.getVendorEstablishmentDate()).isEqualTo(UPDATED_VENDOR_ESTABLISHMENT_DATE);
        assertThat(testVendor.getVendorLogo()).isEqualTo(DEFAULT_VENDOR_LOGO);
        assertThat(testVendor.getVendorLogoContentType()).isEqualTo(DEFAULT_VENDOR_LOGO_CONTENT_TYPE);
        assertThat(testVendor.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testVendor.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testVendor.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testVendor.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testVendor.getContactFullName()).isEqualTo(DEFAULT_CONTACT_FULL_NAME);
        assertThat(testVendor.getContactEmailAddress()).isEqualTo(DEFAULT_CONTACT_EMAIL_ADDRESS);
        assertThat(testVendor.getContactPrimaryPhoneNo()).isEqualTo(UPDATED_CONTACT_PRIMARY_PHONE_NO);
        assertThat(testVendor.getContactPrimaryFaxNo()).isEqualTo(DEFAULT_CONTACT_PRIMARY_FAX_NO);
        assertThat(testVendor.getContactSecondaryPhoneNo()).isEqualTo(UPDATED_CONTACT_SECONDARY_PHONE_NO);
        assertThat(testVendor.getContactSecondaryFaxNo()).isEqualTo(DEFAULT_CONTACT_SECONDARY_FAX_NO);
        assertThat(testVendor.getOfficeLocation()).isEqualTo(DEFAULT_OFFICE_LOCATION);
        assertThat(testVendor.getOfficeFunctionality()).isEqualTo(UPDATED_OFFICE_FUNCTIONALITY);
        assertThat(testVendor.getWebsiteURL()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testVendor.getBuildingNo()).isEqualTo(UPDATED_BUILDING_NO);
        assertThat(testVendor.getVendorStreetName()).isEqualTo(DEFAULT_VENDOR_STREET_NAME);
        assertThat(testVendor.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testVendor.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testVendor.getAdditionalNo()).isEqualTo(DEFAULT_ADDITIONAL_NO);
        assertThat(testVendor.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testVendor.getUnitNo()).isEqualTo(DEFAULT_UNIT_NO);
        assertThat(testVendor.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testVendor.getGoogleMap()).isEqualTo(DEFAULT_GOOGLE_MAP);
        assertThat(testVendor.getCombinedAddress()).isEqualTo(UPDATED_COMBINED_ADDRESS);
        assertThat(testVendor.getcRCertificateUpload()).isEqualTo(DEFAULT_C_R_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getcRCertificateUploadContentType()).isEqualTo(DEFAULT_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getvATCertificateUpload()).isEqualTo(DEFAULT_V_AT_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getvATCertificateUploadContentType()).isEqualTo(DEFAULT_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getNationalAddressUpload()).isEqualTo(DEFAULT_NATIONAL_ADDRESS_UPLOAD);
        assertThat(testVendor.getNationalAddressUploadContentType()).isEqualTo(DEFAULT_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCompanyProfileUpload()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD);
        assertThat(testVendor.getCompanyProfileUploadContentType()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getOtherUpload()).isEqualTo(DEFAULT_OTHER_UPLOAD);
        assertThat(testVendor.getOtherUploadContentType()).isEqualTo(DEFAULT_OTHER_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCash()).isEqualTo(DEFAULT_CASH);
        assertThat(testVendor.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testVendor.getLetterOfCredit()).isEqualTo(DEFAULT_LETTER_OF_CREDIT);
        assertThat(testVendor.getOthers()).isEqualTo(UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void fullUpdateVendorWithPatch() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor using partial update
        Vendor partialUpdatedVendor = new Vendor();
        partialUpdatedVendor.setId(vendor.getId());

        partialUpdatedVendor
            .vendorNameEnglish(UPDATED_VENDOR_NAME_ENGLISH)
            .vendorNameArabic(UPDATED_VENDOR_NAME_ARABIC)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorAccountNumber(UPDATED_VENDOR_ACCOUNT_NUMBER)
            .vendorCRNumber(UPDATED_VENDOR_CR_NUMBER)
            .vendorVATNumber(UPDATED_VENDOR_VAT_NUMBER)
            .vendorType(UPDATED_VENDOR_TYPE)
            .vendorCategory(UPDATED_VENDOR_CATEGORY)
            .vendorEstablishmentDate(UPDATED_VENDOR_ESTABLISHMENT_DATE)
            .vendorLogo(UPDATED_VENDOR_LOGO)
            .vendorLogoContentType(UPDATED_VENDOR_LOGO_CONTENT_TYPE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .contactFullName(UPDATED_CONTACT_FULL_NAME)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .contactPrimaryPhoneNo(UPDATED_CONTACT_PRIMARY_PHONE_NO)
            .contactPrimaryFaxNo(UPDATED_CONTACT_PRIMARY_FAX_NO)
            .contactSecondaryPhoneNo(UPDATED_CONTACT_SECONDARY_PHONE_NO)
            .contactSecondaryFaxNo(UPDATED_CONTACT_SECONDARY_FAX_NO)
            .officeLocation(UPDATED_OFFICE_LOCATION)
            .officeFunctionality(UPDATED_OFFICE_FUNCTIONALITY)
            .websiteURL(UPDATED_WEBSITE_URL)
            .buildingNo(UPDATED_BUILDING_NO)
            .vendorStreetName(UPDATED_VENDOR_STREET_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .additionalNo(UPDATED_ADDITIONAL_NO)
            .cityName(UPDATED_CITY_NAME)
            .unitNo(UPDATED_UNIT_NO)
            .country(UPDATED_COUNTRY)
            .googleMap(UPDATED_GOOGLE_MAP)
            .combinedAddress(UPDATED_COMBINED_ADDRESS)
            .cRCertificateUpload(UPDATED_C_R_CERTIFICATE_UPLOAD)
            .cRCertificateUploadContentType(UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .vATCertificateUpload(UPDATED_V_AT_CERTIFICATE_UPLOAD)
            .vATCertificateUploadContentType(UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE)
            .nationalAddressUpload(UPDATED_NATIONAL_ADDRESS_UPLOAD)
            .nationalAddressUploadContentType(UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE)
            .companyProfileUpload(UPDATED_COMPANY_PROFILE_UPLOAD)
            .companyProfileUploadContentType(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE)
            .otherUpload(UPDATED_OTHER_UPLOAD)
            .otherUploadContentType(UPDATED_OTHER_UPLOAD_CONTENT_TYPE)
            .cash(UPDATED_CASH)
            .credit(UPDATED_CREDIT)
            .letterOfCredit(UPDATED_LETTER_OF_CREDIT)
            .others(UPDATED_OTHERS);

        restVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendor))
            )
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getVendorNameEnglish()).isEqualTo(UPDATED_VENDOR_NAME_ENGLISH);
        assertThat(testVendor.getVendorNameArabic()).isEqualTo(UPDATED_VENDOR_NAME_ARABIC);
        assertThat(testVendor.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testVendor.getVendorAccountNumber()).isEqualTo(UPDATED_VENDOR_ACCOUNT_NUMBER);
        assertThat(testVendor.getVendorCRNumber()).isEqualTo(UPDATED_VENDOR_CR_NUMBER);
        assertThat(testVendor.getVendorVATNumber()).isEqualTo(UPDATED_VENDOR_VAT_NUMBER);
        assertThat(testVendor.getVendorType()).isEqualTo(UPDATED_VENDOR_TYPE);
        assertThat(testVendor.getVendorCategory()).isEqualTo(UPDATED_VENDOR_CATEGORY);
        assertThat(testVendor.getVendorEstablishmentDate()).isEqualTo(UPDATED_VENDOR_ESTABLISHMENT_DATE);
        assertThat(testVendor.getVendorLogo()).isEqualTo(UPDATED_VENDOR_LOGO);
        assertThat(testVendor.getVendorLogoContentType()).isEqualTo(UPDATED_VENDOR_LOGO_CONTENT_TYPE);
        assertThat(testVendor.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testVendor.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testVendor.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testVendor.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testVendor.getContactFullName()).isEqualTo(UPDATED_CONTACT_FULL_NAME);
        assertThat(testVendor.getContactEmailAddress()).isEqualTo(UPDATED_CONTACT_EMAIL_ADDRESS);
        assertThat(testVendor.getContactPrimaryPhoneNo()).isEqualTo(UPDATED_CONTACT_PRIMARY_PHONE_NO);
        assertThat(testVendor.getContactPrimaryFaxNo()).isEqualTo(UPDATED_CONTACT_PRIMARY_FAX_NO);
        assertThat(testVendor.getContactSecondaryPhoneNo()).isEqualTo(UPDATED_CONTACT_SECONDARY_PHONE_NO);
        assertThat(testVendor.getContactSecondaryFaxNo()).isEqualTo(UPDATED_CONTACT_SECONDARY_FAX_NO);
        assertThat(testVendor.getOfficeLocation()).isEqualTo(UPDATED_OFFICE_LOCATION);
        assertThat(testVendor.getOfficeFunctionality()).isEqualTo(UPDATED_OFFICE_FUNCTIONALITY);
        assertThat(testVendor.getWebsiteURL()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testVendor.getBuildingNo()).isEqualTo(UPDATED_BUILDING_NO);
        assertThat(testVendor.getVendorStreetName()).isEqualTo(UPDATED_VENDOR_STREET_NAME);
        assertThat(testVendor.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testVendor.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testVendor.getAdditionalNo()).isEqualTo(UPDATED_ADDITIONAL_NO);
        assertThat(testVendor.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testVendor.getUnitNo()).isEqualTo(UPDATED_UNIT_NO);
        assertThat(testVendor.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testVendor.getGoogleMap()).isEqualTo(UPDATED_GOOGLE_MAP);
        assertThat(testVendor.getCombinedAddress()).isEqualTo(UPDATED_COMBINED_ADDRESS);
        assertThat(testVendor.getcRCertificateUpload()).isEqualTo(UPDATED_C_R_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getcRCertificateUploadContentType()).isEqualTo(UPDATED_C_R_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getvATCertificateUpload()).isEqualTo(UPDATED_V_AT_CERTIFICATE_UPLOAD);
        assertThat(testVendor.getvATCertificateUploadContentType()).isEqualTo(UPDATED_V_AT_CERTIFICATE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getNationalAddressUpload()).isEqualTo(UPDATED_NATIONAL_ADDRESS_UPLOAD);
        assertThat(testVendor.getNationalAddressUploadContentType()).isEqualTo(UPDATED_NATIONAL_ADDRESS_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCompanyProfileUpload()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD);
        assertThat(testVendor.getCompanyProfileUploadContentType()).isEqualTo(UPDATED_COMPANY_PROFILE_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getOtherUpload()).isEqualTo(UPDATED_OTHER_UPLOAD);
        assertThat(testVendor.getOtherUploadContentType()).isEqualTo(UPDATED_OTHER_UPLOAD_CONTENT_TYPE);
        assertThat(testVendor.getCash()).isEqualTo(UPDATED_CASH);
        assertThat(testVendor.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testVendor.getLetterOfCredit()).isEqualTo(UPDATED_LETTER_OF_CREDIT);
        assertThat(testVendor.getOthers()).isEqualTo(UPDATED_OTHERS);
    }

    @Test
    @Transactional
    void patchNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vendor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();
        vendor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Delete the vendor
        restVendorMockMvc
            .perform(delete(ENTITY_API_URL_ID, vendor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
