package com.yam.ecompany.domain;

import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.OfficeFunctionality;
import com.yam.ecompany.domain.enumeration.VendorCategory;
import com.yam.ecompany.domain.enumeration.VendorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "vendor_name_english", nullable = false)
    private String vendorNameEnglish;

    @Column(name = "vendor_name_arabic")
    private String vendorNameArabic;

    @NotNull
    @Column(name = "vendor_id", nullable = false)
    private String vendorId;

    @Column(name = "vendor_account_number")
    private String vendorAccountNumber;

    @Column(name = "vendor_cr_number")
    private String vendorCRNumber;

    @Column(name = "vendor_vat_number")
    private String vendorVATNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor_type")
    private VendorType vendorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "vendor_category")
    private VendorCategory vendorCategory;

    @Column(name = "vendor_establishment_date")
    private String vendorEstablishmentDate;

    @Lob
    @Column(name = "vendor_logo")
    private byte[] vendorLogo;

    @Column(name = "vendor_logo_content_type")
    private String vendorLogoContentType;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "taken")
    private Instant taken;

    @Column(name = "uploaded")
    private Instant uploaded;

    @Column(name = "contact_full_name")
    private String contactFullName;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "contact_email_address", nullable = false)
    private String contactEmailAddress;

    @Column(name = "contact_primary_phone_no")
    private String contactPrimaryPhoneNo;

    @Column(name = "contact_primary_fax_no")
    private String contactPrimaryFaxNo;

    @Column(name = "contact_secondary_phone_no")
    private String contactSecondaryPhoneNo;

    @Column(name = "contact_secondary_fax_no")
    private String contactSecondaryFaxNo;

    @Column(name = "office_location")
    private String officeLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "office_functionality")
    private OfficeFunctionality officeFunctionality;

    @Column(name = "website_url")
    private String websiteURL;

    @Column(name = "building_no")
    private String buildingNo;

    @Column(name = "vendor_street_name")
    private String vendorStreetName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "additional_no")
    private String additionalNo;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "unit_no")
    private String unitNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Lob
    @Column(name = "google_map")
    private String googleMap;

    @Lob
    @Column(name = "combined_address")
    private String combinedAddress;

    @Lob
    @Column(name = "c_r_certificate_upload")
    private byte[] cRCertificateUpload;

    @Column(name = "c_r_certificate_upload_content_type")
    private String cRCertificateUploadContentType;

    @Lob
    @Column(name = "v_at_certificate_upload")
    private byte[] vATCertificateUpload;

    @Column(name = "v_at_certificate_upload_content_type")
    private String vATCertificateUploadContentType;

    @Lob
    @Column(name = "national_address_upload")
    private byte[] nationalAddressUpload;

    @Column(name = "national_address_upload_content_type")
    private String nationalAddressUploadContentType;

    @Lob
    @Column(name = "company_profile_upload")
    private byte[] companyProfileUpload;

    @Column(name = "company_profile_upload_content_type")
    private String companyProfileUploadContentType;

    @Lob
    @Column(name = "other_upload")
    private byte[] otherUpload;

    @Column(name = "other_upload_content_type")
    private String otherUploadContentType;

    @Column(name = "cash")
    private String cash;

    @Column(name = "credit")
    private String credit;

    @Column(name = "letter_of_credit")
    private String letterOfCredit;

    @Column(name = "others")
    private String others;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vendor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorNameEnglish() {
        return this.vendorNameEnglish;
    }

    public Vendor vendorNameEnglish(String vendorNameEnglish) {
        this.setVendorNameEnglish(vendorNameEnglish);
        return this;
    }

    public void setVendorNameEnglish(String vendorNameEnglish) {
        this.vendorNameEnglish = vendorNameEnglish;
    }

    public String getVendorNameArabic() {
        return this.vendorNameArabic;
    }

    public Vendor vendorNameArabic(String vendorNameArabic) {
        this.setVendorNameArabic(vendorNameArabic);
        return this;
    }

    public void setVendorNameArabic(String vendorNameArabic) {
        this.vendorNameArabic = vendorNameArabic;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public Vendor vendorId(String vendorId) {
        this.setVendorId(vendorId);
        return this;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorAccountNumber() {
        return this.vendorAccountNumber;
    }

    public Vendor vendorAccountNumber(String vendorAccountNumber) {
        this.setVendorAccountNumber(vendorAccountNumber);
        return this;
    }

    public void setVendorAccountNumber(String vendorAccountNumber) {
        this.vendorAccountNumber = vendorAccountNumber;
    }

    public String getVendorCRNumber() {
        return this.vendorCRNumber;
    }

    public Vendor vendorCRNumber(String vendorCRNumber) {
        this.setVendorCRNumber(vendorCRNumber);
        return this;
    }

    public void setVendorCRNumber(String vendorCRNumber) {
        this.vendorCRNumber = vendorCRNumber;
    }

    public String getVendorVATNumber() {
        return this.vendorVATNumber;
    }

    public Vendor vendorVATNumber(String vendorVATNumber) {
        this.setVendorVATNumber(vendorVATNumber);
        return this;
    }

    public void setVendorVATNumber(String vendorVATNumber) {
        this.vendorVATNumber = vendorVATNumber;
    }

    public VendorType getVendorType() {
        return this.vendorType;
    }

    public Vendor vendorType(VendorType vendorType) {
        this.setVendorType(vendorType);
        return this;
    }

    public void setVendorType(VendorType vendorType) {
        this.vendorType = vendorType;
    }

    public VendorCategory getVendorCategory() {
        return this.vendorCategory;
    }

    public Vendor vendorCategory(VendorCategory vendorCategory) {
        this.setVendorCategory(vendorCategory);
        return this;
    }

    public void setVendorCategory(VendorCategory vendorCategory) {
        this.vendorCategory = vendorCategory;
    }

    public String getVendorEstablishmentDate() {
        return this.vendorEstablishmentDate;
    }

    public Vendor vendorEstablishmentDate(String vendorEstablishmentDate) {
        this.setVendorEstablishmentDate(vendorEstablishmentDate);
        return this;
    }

    public void setVendorEstablishmentDate(String vendorEstablishmentDate) {
        this.vendorEstablishmentDate = vendorEstablishmentDate;
    }

    public byte[] getVendorLogo() {
        return this.vendorLogo;
    }

    public Vendor vendorLogo(byte[] vendorLogo) {
        this.setVendorLogo(vendorLogo);
        return this;
    }

    public void setVendorLogo(byte[] vendorLogo) {
        this.vendorLogo = vendorLogo;
    }

    public String getVendorLogoContentType() {
        return this.vendorLogoContentType;
    }

    public Vendor vendorLogoContentType(String vendorLogoContentType) {
        this.vendorLogoContentType = vendorLogoContentType;
        return this;
    }

    public void setVendorLogoContentType(String vendorLogoContentType) {
        this.vendorLogoContentType = vendorLogoContentType;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Vendor height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Vendor width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Instant getTaken() {
        return this.taken;
    }

    public Vendor taken(Instant taken) {
        this.setTaken(taken);
        return this;
    }

    public void setTaken(Instant taken) {
        this.taken = taken;
    }

    public Instant getUploaded() {
        return this.uploaded;
    }

    public Vendor uploaded(Instant uploaded) {
        this.setUploaded(uploaded);
        return this;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }

    public String getContactFullName() {
        return this.contactFullName;
    }

    public Vendor contactFullName(String contactFullName) {
        this.setContactFullName(contactFullName);
        return this;
    }

    public void setContactFullName(String contactFullName) {
        this.contactFullName = contactFullName;
    }

    public String getContactEmailAddress() {
        return this.contactEmailAddress;
    }

    public Vendor contactEmailAddress(String contactEmailAddress) {
        this.setContactEmailAddress(contactEmailAddress);
        return this;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getContactPrimaryPhoneNo() {
        return this.contactPrimaryPhoneNo;
    }

    public Vendor contactPrimaryPhoneNo(String contactPrimaryPhoneNo) {
        this.setContactPrimaryPhoneNo(contactPrimaryPhoneNo);
        return this;
    }

    public void setContactPrimaryPhoneNo(String contactPrimaryPhoneNo) {
        this.contactPrimaryPhoneNo = contactPrimaryPhoneNo;
    }

    public String getContactPrimaryFaxNo() {
        return this.contactPrimaryFaxNo;
    }

    public Vendor contactPrimaryFaxNo(String contactPrimaryFaxNo) {
        this.setContactPrimaryFaxNo(contactPrimaryFaxNo);
        return this;
    }

    public void setContactPrimaryFaxNo(String contactPrimaryFaxNo) {
        this.contactPrimaryFaxNo = contactPrimaryFaxNo;
    }

    public String getContactSecondaryPhoneNo() {
        return this.contactSecondaryPhoneNo;
    }

    public Vendor contactSecondaryPhoneNo(String contactSecondaryPhoneNo) {
        this.setContactSecondaryPhoneNo(contactSecondaryPhoneNo);
        return this;
    }

    public void setContactSecondaryPhoneNo(String contactSecondaryPhoneNo) {
        this.contactSecondaryPhoneNo = contactSecondaryPhoneNo;
    }

    public String getContactSecondaryFaxNo() {
        return this.contactSecondaryFaxNo;
    }

    public Vendor contactSecondaryFaxNo(String contactSecondaryFaxNo) {
        this.setContactSecondaryFaxNo(contactSecondaryFaxNo);
        return this;
    }

    public void setContactSecondaryFaxNo(String contactSecondaryFaxNo) {
        this.contactSecondaryFaxNo = contactSecondaryFaxNo;
    }

    public String getOfficeLocation() {
        return this.officeLocation;
    }

    public Vendor officeLocation(String officeLocation) {
        this.setOfficeLocation(officeLocation);
        return this;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public OfficeFunctionality getOfficeFunctionality() {
        return this.officeFunctionality;
    }

    public Vendor officeFunctionality(OfficeFunctionality officeFunctionality) {
        this.setOfficeFunctionality(officeFunctionality);
        return this;
    }

    public void setOfficeFunctionality(OfficeFunctionality officeFunctionality) {
        this.officeFunctionality = officeFunctionality;
    }

    public String getWebsiteURL() {
        return this.websiteURL;
    }

    public Vendor websiteURL(String websiteURL) {
        this.setWebsiteURL(websiteURL);
        return this;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getBuildingNo() {
        return this.buildingNo;
    }

    public Vendor buildingNo(String buildingNo) {
        this.setBuildingNo(buildingNo);
        return this;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getVendorStreetName() {
        return this.vendorStreetName;
    }

    public Vendor vendorStreetName(String vendorStreetName) {
        this.setVendorStreetName(vendorStreetName);
        return this;
    }

    public void setVendorStreetName(String vendorStreetName) {
        this.vendorStreetName = vendorStreetName;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Vendor zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public Vendor districtName(String districtName) {
        this.setDistrictName(districtName);
        return this;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAdditionalNo() {
        return this.additionalNo;
    }

    public Vendor additionalNo(String additionalNo) {
        this.setAdditionalNo(additionalNo);
        return this;
    }

    public void setAdditionalNo(String additionalNo) {
        this.additionalNo = additionalNo;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Vendor cityName(String cityName) {
        this.setCityName(cityName);
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnitNo() {
        return this.unitNo;
    }

    public Vendor unitNo(String unitNo) {
        this.setUnitNo(unitNo);
        return this;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public Country getCountry() {
        return this.country;
    }

    public Vendor country(Country country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getGoogleMap() {
        return this.googleMap;
    }

    public Vendor googleMap(String googleMap) {
        this.setGoogleMap(googleMap);
        return this;
    }

    public void setGoogleMap(String googleMap) {
        this.googleMap = googleMap;
    }

    public String getCombinedAddress() {
        return this.combinedAddress;
    }

    public Vendor combinedAddress(String combinedAddress) {
        this.setCombinedAddress(combinedAddress);
        return this;
    }

    public void setCombinedAddress(String combinedAddress) {
        this.combinedAddress = combinedAddress;
    }

    public byte[] getcRCertificateUpload() {
        return this.cRCertificateUpload;
    }

    public Vendor cRCertificateUpload(byte[] cRCertificateUpload) {
        this.setcRCertificateUpload(cRCertificateUpload);
        return this;
    }

    public void setcRCertificateUpload(byte[] cRCertificateUpload) {
        this.cRCertificateUpload = cRCertificateUpload;
    }

    public String getcRCertificateUploadContentType() {
        return this.cRCertificateUploadContentType;
    }

    public Vendor cRCertificateUploadContentType(String cRCertificateUploadContentType) {
        this.cRCertificateUploadContentType = cRCertificateUploadContentType;
        return this;
    }

    public void setcRCertificateUploadContentType(String cRCertificateUploadContentType) {
        this.cRCertificateUploadContentType = cRCertificateUploadContentType;
    }

    public byte[] getvATCertificateUpload() {
        return this.vATCertificateUpload;
    }

    public Vendor vATCertificateUpload(byte[] vATCertificateUpload) {
        this.setvATCertificateUpload(vATCertificateUpload);
        return this;
    }

    public void setvATCertificateUpload(byte[] vATCertificateUpload) {
        this.vATCertificateUpload = vATCertificateUpload;
    }

    public String getvATCertificateUploadContentType() {
        return this.vATCertificateUploadContentType;
    }

    public Vendor vATCertificateUploadContentType(String vATCertificateUploadContentType) {
        this.vATCertificateUploadContentType = vATCertificateUploadContentType;
        return this;
    }

    public void setvATCertificateUploadContentType(String vATCertificateUploadContentType) {
        this.vATCertificateUploadContentType = vATCertificateUploadContentType;
    }

    public byte[] getNationalAddressUpload() {
        return this.nationalAddressUpload;
    }

    public Vendor nationalAddressUpload(byte[] nationalAddressUpload) {
        this.setNationalAddressUpload(nationalAddressUpload);
        return this;
    }

    public void setNationalAddressUpload(byte[] nationalAddressUpload) {
        this.nationalAddressUpload = nationalAddressUpload;
    }

    public String getNationalAddressUploadContentType() {
        return this.nationalAddressUploadContentType;
    }

    public Vendor nationalAddressUploadContentType(String nationalAddressUploadContentType) {
        this.nationalAddressUploadContentType = nationalAddressUploadContentType;
        return this;
    }

    public void setNationalAddressUploadContentType(String nationalAddressUploadContentType) {
        this.nationalAddressUploadContentType = nationalAddressUploadContentType;
    }

    public byte[] getCompanyProfileUpload() {
        return this.companyProfileUpload;
    }

    public Vendor companyProfileUpload(byte[] companyProfileUpload) {
        this.setCompanyProfileUpload(companyProfileUpload);
        return this;
    }

    public void setCompanyProfileUpload(byte[] companyProfileUpload) {
        this.companyProfileUpload = companyProfileUpload;
    }

    public String getCompanyProfileUploadContentType() {
        return this.companyProfileUploadContentType;
    }

    public Vendor companyProfileUploadContentType(String companyProfileUploadContentType) {
        this.companyProfileUploadContentType = companyProfileUploadContentType;
        return this;
    }

    public void setCompanyProfileUploadContentType(String companyProfileUploadContentType) {
        this.companyProfileUploadContentType = companyProfileUploadContentType;
    }

    public byte[] getOtherUpload() {
        return this.otherUpload;
    }

    public Vendor otherUpload(byte[] otherUpload) {
        this.setOtherUpload(otherUpload);
        return this;
    }

    public void setOtherUpload(byte[] otherUpload) {
        this.otherUpload = otherUpload;
    }

    public String getOtherUploadContentType() {
        return this.otherUploadContentType;
    }

    public Vendor otherUploadContentType(String otherUploadContentType) {
        this.otherUploadContentType = otherUploadContentType;
        return this;
    }

    public void setOtherUploadContentType(String otherUploadContentType) {
        this.otherUploadContentType = otherUploadContentType;
    }

    public String getCash() {
        return this.cash;
    }

    public Vendor cash(String cash) {
        this.setCash(cash);
        return this;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCredit() {
        return this.credit;
    }

    public Vendor credit(String credit) {
        this.setCredit(credit);
        return this;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLetterOfCredit() {
        return this.letterOfCredit;
    }

    public Vendor letterOfCredit(String letterOfCredit) {
        this.setLetterOfCredit(letterOfCredit);
        return this;
    }

    public void setLetterOfCredit(String letterOfCredit) {
        this.letterOfCredit = letterOfCredit;
    }

    public String getOthers() {
        return this.others;
    }

    public Vendor others(String others) {
        this.setOthers(others);
        return this;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendor)) {
            return false;
        }
        return id != null && id.equals(((Vendor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", vendorNameEnglish='" + getVendorNameEnglish() + "'" +
            ", vendorNameArabic='" + getVendorNameArabic() + "'" +
            ", vendorId='" + getVendorId() + "'" +
            ", vendorAccountNumber='" + getVendorAccountNumber() + "'" +
            ", vendorCRNumber='" + getVendorCRNumber() + "'" +
            ", vendorVATNumber='" + getVendorVATNumber() + "'" +
            ", vendorType='" + getVendorType() + "'" +
            ", vendorCategory='" + getVendorCategory() + "'" +
            ", vendorEstablishmentDate='" + getVendorEstablishmentDate() + "'" +
            ", vendorLogo='" + getVendorLogo() + "'" +
            ", vendorLogoContentType='" + getVendorLogoContentType() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", taken='" + getTaken() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            ", contactFullName='" + getContactFullName() + "'" +
            ", contactEmailAddress='" + getContactEmailAddress() + "'" +
            ", contactPrimaryPhoneNo='" + getContactPrimaryPhoneNo() + "'" +
            ", contactPrimaryFaxNo='" + getContactPrimaryFaxNo() + "'" +
            ", contactSecondaryPhoneNo='" + getContactSecondaryPhoneNo() + "'" +
            ", contactSecondaryFaxNo='" + getContactSecondaryFaxNo() + "'" +
            ", officeLocation='" + getOfficeLocation() + "'" +
            ", officeFunctionality='" + getOfficeFunctionality() + "'" +
            ", websiteURL='" + getWebsiteURL() + "'" +
            ", buildingNo='" + getBuildingNo() + "'" +
            ", vendorStreetName='" + getVendorStreetName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", districtName='" + getDistrictName() + "'" +
            ", additionalNo='" + getAdditionalNo() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", unitNo='" + getUnitNo() + "'" +
            ", country='" + getCountry() + "'" +
            ", googleMap='" + getGoogleMap() + "'" +
            ", combinedAddress='" + getCombinedAddress() + "'" +
            ", cRCertificateUpload='" + getcRCertificateUpload() + "'" +
            ", cRCertificateUploadContentType='" + getcRCertificateUploadContentType() + "'" +
            ", vATCertificateUpload='" + getvATCertificateUpload() + "'" +
            ", vATCertificateUploadContentType='" + getvATCertificateUploadContentType() + "'" +
            ", nationalAddressUpload='" + getNationalAddressUpload() + "'" +
            ", nationalAddressUploadContentType='" + getNationalAddressUploadContentType() + "'" +
            ", companyProfileUpload='" + getCompanyProfileUpload() + "'" +
            ", companyProfileUploadContentType='" + getCompanyProfileUploadContentType() + "'" +
            ", otherUpload='" + getOtherUpload() + "'" +
            ", otherUploadContentType='" + getOtherUploadContentType() + "'" +
            ", cash='" + getCash() + "'" +
            ", credit='" + getCredit() + "'" +
            ", letterOfCredit='" + getLetterOfCredit() + "'" +
            ", others='" + getOthers() + "'" +
            "}";
    }
}
