package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.OfficeFunctionality;
import com.yam.ecompany.domain.enumeration.VendorCategory;
import com.yam.ecompany.domain.enumeration.VendorType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.Vendor} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.VendorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering VendorType
     */
    public static class VendorTypeFilter extends Filter<VendorType> {

        public VendorTypeFilter() {}

        public VendorTypeFilter(VendorTypeFilter filter) {
            super(filter);
        }

        @Override
        public VendorTypeFilter copy() {
            return new VendorTypeFilter(this);
        }
    }

    /**
     * Class for filtering VendorCategory
     */
    public static class VendorCategoryFilter extends Filter<VendorCategory> {

        public VendorCategoryFilter() {}

        public VendorCategoryFilter(VendorCategoryFilter filter) {
            super(filter);
        }

        @Override
        public VendorCategoryFilter copy() {
            return new VendorCategoryFilter(this);
        }
    }

    /**
     * Class for filtering OfficeFunctionality
     */
    public static class OfficeFunctionalityFilter extends Filter<OfficeFunctionality> {

        public OfficeFunctionalityFilter() {}

        public OfficeFunctionalityFilter(OfficeFunctionalityFilter filter) {
            super(filter);
        }

        @Override
        public OfficeFunctionalityFilter copy() {
            return new OfficeFunctionalityFilter(this);
        }
    }

    /**
     * Class for filtering Country
     */
    public static class CountryFilter extends Filter<Country> {

        public CountryFilter() {}

        public CountryFilter(CountryFilter filter) {
            super(filter);
        }

        @Override
        public CountryFilter copy() {
            return new CountryFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vendorNameEnglish;

    private StringFilter vendorNameArabic;

    private StringFilter vendorId;

    private StringFilter vendorAccountNumber;

    private StringFilter vendorCRNumber;

    private StringFilter vendorVATNumber;

    private VendorTypeFilter vendorType;

    private VendorCategoryFilter vendorCategory;

    private StringFilter vendorEstablishmentDate;

    private IntegerFilter height;

    private IntegerFilter width;

    private InstantFilter taken;

    private InstantFilter uploaded;

    private StringFilter contactFullName;

    private StringFilter contactEmailAddress;

    private StringFilter contactPrimaryPhoneNo;

    private StringFilter contactPrimaryFaxNo;

    private StringFilter contactSecondaryPhoneNo;

    private StringFilter contactSecondaryFaxNo;

    private StringFilter officeLocation;

    private OfficeFunctionalityFilter officeFunctionality;

    private StringFilter websiteURL;

    private StringFilter buildingNo;

    private StringFilter vendorStreetName;

    private StringFilter zipCode;

    private StringFilter districtName;

    private StringFilter additionalNo;

    private StringFilter cityName;

    private StringFilter unitNo;

    private CountryFilter country;

    private StringFilter cash;

    private StringFilter credit;

    private StringFilter letterOfCredit;

    private StringFilter others;

    private Boolean distinct;

    public VendorCriteria() {}

    public VendorCriteria(VendorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vendorNameEnglish = other.vendorNameEnglish == null ? null : other.vendorNameEnglish.copy();
        this.vendorNameArabic = other.vendorNameArabic == null ? null : other.vendorNameArabic.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.vendorAccountNumber = other.vendorAccountNumber == null ? null : other.vendorAccountNumber.copy();
        this.vendorCRNumber = other.vendorCRNumber == null ? null : other.vendorCRNumber.copy();
        this.vendorVATNumber = other.vendorVATNumber == null ? null : other.vendorVATNumber.copy();
        this.vendorType = other.vendorType == null ? null : other.vendorType.copy();
        this.vendorCategory = other.vendorCategory == null ? null : other.vendorCategory.copy();
        this.vendorEstablishmentDate = other.vendorEstablishmentDate == null ? null : other.vendorEstablishmentDate.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.taken = other.taken == null ? null : other.taken.copy();
        this.uploaded = other.uploaded == null ? null : other.uploaded.copy();
        this.contactFullName = other.contactFullName == null ? null : other.contactFullName.copy();
        this.contactEmailAddress = other.contactEmailAddress == null ? null : other.contactEmailAddress.copy();
        this.contactPrimaryPhoneNo = other.contactPrimaryPhoneNo == null ? null : other.contactPrimaryPhoneNo.copy();
        this.contactPrimaryFaxNo = other.contactPrimaryFaxNo == null ? null : other.contactPrimaryFaxNo.copy();
        this.contactSecondaryPhoneNo = other.contactSecondaryPhoneNo == null ? null : other.contactSecondaryPhoneNo.copy();
        this.contactSecondaryFaxNo = other.contactSecondaryFaxNo == null ? null : other.contactSecondaryFaxNo.copy();
        this.officeLocation = other.officeLocation == null ? null : other.officeLocation.copy();
        this.officeFunctionality = other.officeFunctionality == null ? null : other.officeFunctionality.copy();
        this.websiteURL = other.websiteURL == null ? null : other.websiteURL.copy();
        this.buildingNo = other.buildingNo == null ? null : other.buildingNo.copy();
        this.vendorStreetName = other.vendorStreetName == null ? null : other.vendorStreetName.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.districtName = other.districtName == null ? null : other.districtName.copy();
        this.additionalNo = other.additionalNo == null ? null : other.additionalNo.copy();
        this.cityName = other.cityName == null ? null : other.cityName.copy();
        this.unitNo = other.unitNo == null ? null : other.unitNo.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.cash = other.cash == null ? null : other.cash.copy();
        this.credit = other.credit == null ? null : other.credit.copy();
        this.letterOfCredit = other.letterOfCredit == null ? null : other.letterOfCredit.copy();
        this.others = other.others == null ? null : other.others.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VendorCriteria copy() {
        return new VendorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVendorNameEnglish() {
        return vendorNameEnglish;
    }

    public StringFilter vendorNameEnglish() {
        if (vendorNameEnglish == null) {
            vendorNameEnglish = new StringFilter();
        }
        return vendorNameEnglish;
    }

    public void setVendorNameEnglish(StringFilter vendorNameEnglish) {
        this.vendorNameEnglish = vendorNameEnglish;
    }

    public StringFilter getVendorNameArabic() {
        return vendorNameArabic;
    }

    public StringFilter vendorNameArabic() {
        if (vendorNameArabic == null) {
            vendorNameArabic = new StringFilter();
        }
        return vendorNameArabic;
    }

    public void setVendorNameArabic(StringFilter vendorNameArabic) {
        this.vendorNameArabic = vendorNameArabic;
    }

    public StringFilter getVendorId() {
        return vendorId;
    }

    public StringFilter vendorId() {
        if (vendorId == null) {
            vendorId = new StringFilter();
        }
        return vendorId;
    }

    public void setVendorId(StringFilter vendorId) {
        this.vendorId = vendorId;
    }

    public StringFilter getVendorAccountNumber() {
        return vendorAccountNumber;
    }

    public StringFilter vendorAccountNumber() {
        if (vendorAccountNumber == null) {
            vendorAccountNumber = new StringFilter();
        }
        return vendorAccountNumber;
    }

    public void setVendorAccountNumber(StringFilter vendorAccountNumber) {
        this.vendorAccountNumber = vendorAccountNumber;
    }

    public StringFilter getVendorCRNumber() {
        return vendorCRNumber;
    }

    public StringFilter vendorCRNumber() {
        if (vendorCRNumber == null) {
            vendorCRNumber = new StringFilter();
        }
        return vendorCRNumber;
    }

    public void setVendorCRNumber(StringFilter vendorCRNumber) {
        this.vendorCRNumber = vendorCRNumber;
    }

    public StringFilter getVendorVATNumber() {
        return vendorVATNumber;
    }

    public StringFilter vendorVATNumber() {
        if (vendorVATNumber == null) {
            vendorVATNumber = new StringFilter();
        }
        return vendorVATNumber;
    }

    public void setVendorVATNumber(StringFilter vendorVATNumber) {
        this.vendorVATNumber = vendorVATNumber;
    }

    public VendorTypeFilter getVendorType() {
        return vendorType;
    }

    public VendorTypeFilter vendorType() {
        if (vendorType == null) {
            vendorType = new VendorTypeFilter();
        }
        return vendorType;
    }

    public void setVendorType(VendorTypeFilter vendorType) {
        this.vendorType = vendorType;
    }

    public VendorCategoryFilter getVendorCategory() {
        return vendorCategory;
    }

    public VendorCategoryFilter vendorCategory() {
        if (vendorCategory == null) {
            vendorCategory = new VendorCategoryFilter();
        }
        return vendorCategory;
    }

    public void setVendorCategory(VendorCategoryFilter vendorCategory) {
        this.vendorCategory = vendorCategory;
    }

    public StringFilter getVendorEstablishmentDate() {
        return vendorEstablishmentDate;
    }

    public StringFilter vendorEstablishmentDate() {
        if (vendorEstablishmentDate == null) {
            vendorEstablishmentDate = new StringFilter();
        }
        return vendorEstablishmentDate;
    }

    public void setVendorEstablishmentDate(StringFilter vendorEstablishmentDate) {
        this.vendorEstablishmentDate = vendorEstablishmentDate;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public IntegerFilter height() {
        if (height == null) {
            height = new IntegerFilter();
        }
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public IntegerFilter getWidth() {
        return width;
    }

    public IntegerFilter width() {
        if (width == null) {
            width = new IntegerFilter();
        }
        return width;
    }

    public void setWidth(IntegerFilter width) {
        this.width = width;
    }

    public InstantFilter getTaken() {
        return taken;
    }

    public InstantFilter taken() {
        if (taken == null) {
            taken = new InstantFilter();
        }
        return taken;
    }

    public void setTaken(InstantFilter taken) {
        this.taken = taken;
    }

    public InstantFilter getUploaded() {
        return uploaded;
    }

    public InstantFilter uploaded() {
        if (uploaded == null) {
            uploaded = new InstantFilter();
        }
        return uploaded;
    }

    public void setUploaded(InstantFilter uploaded) {
        this.uploaded = uploaded;
    }

    public StringFilter getContactFullName() {
        return contactFullName;
    }

    public StringFilter contactFullName() {
        if (contactFullName == null) {
            contactFullName = new StringFilter();
        }
        return contactFullName;
    }

    public void setContactFullName(StringFilter contactFullName) {
        this.contactFullName = contactFullName;
    }

    public StringFilter getContactEmailAddress() {
        return contactEmailAddress;
    }

    public StringFilter contactEmailAddress() {
        if (contactEmailAddress == null) {
            contactEmailAddress = new StringFilter();
        }
        return contactEmailAddress;
    }

    public void setContactEmailAddress(StringFilter contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public StringFilter getContactPrimaryPhoneNo() {
        return contactPrimaryPhoneNo;
    }

    public StringFilter contactPrimaryPhoneNo() {
        if (contactPrimaryPhoneNo == null) {
            contactPrimaryPhoneNo = new StringFilter();
        }
        return contactPrimaryPhoneNo;
    }

    public void setContactPrimaryPhoneNo(StringFilter contactPrimaryPhoneNo) {
        this.contactPrimaryPhoneNo = contactPrimaryPhoneNo;
    }

    public StringFilter getContactPrimaryFaxNo() {
        return contactPrimaryFaxNo;
    }

    public StringFilter contactPrimaryFaxNo() {
        if (contactPrimaryFaxNo == null) {
            contactPrimaryFaxNo = new StringFilter();
        }
        return contactPrimaryFaxNo;
    }

    public void setContactPrimaryFaxNo(StringFilter contactPrimaryFaxNo) {
        this.contactPrimaryFaxNo = contactPrimaryFaxNo;
    }

    public StringFilter getContactSecondaryPhoneNo() {
        return contactSecondaryPhoneNo;
    }

    public StringFilter contactSecondaryPhoneNo() {
        if (contactSecondaryPhoneNo == null) {
            contactSecondaryPhoneNo = new StringFilter();
        }
        return contactSecondaryPhoneNo;
    }

    public void setContactSecondaryPhoneNo(StringFilter contactSecondaryPhoneNo) {
        this.contactSecondaryPhoneNo = contactSecondaryPhoneNo;
    }

    public StringFilter getContactSecondaryFaxNo() {
        return contactSecondaryFaxNo;
    }

    public StringFilter contactSecondaryFaxNo() {
        if (contactSecondaryFaxNo == null) {
            contactSecondaryFaxNo = new StringFilter();
        }
        return contactSecondaryFaxNo;
    }

    public void setContactSecondaryFaxNo(StringFilter contactSecondaryFaxNo) {
        this.contactSecondaryFaxNo = contactSecondaryFaxNo;
    }

    public StringFilter getOfficeLocation() {
        return officeLocation;
    }

    public StringFilter officeLocation() {
        if (officeLocation == null) {
            officeLocation = new StringFilter();
        }
        return officeLocation;
    }

    public void setOfficeLocation(StringFilter officeLocation) {
        this.officeLocation = officeLocation;
    }

    public OfficeFunctionalityFilter getOfficeFunctionality() {
        return officeFunctionality;
    }

    public OfficeFunctionalityFilter officeFunctionality() {
        if (officeFunctionality == null) {
            officeFunctionality = new OfficeFunctionalityFilter();
        }
        return officeFunctionality;
    }

    public void setOfficeFunctionality(OfficeFunctionalityFilter officeFunctionality) {
        this.officeFunctionality = officeFunctionality;
    }

    public StringFilter getWebsiteURL() {
        return websiteURL;
    }

    public StringFilter websiteURL() {
        if (websiteURL == null) {
            websiteURL = new StringFilter();
        }
        return websiteURL;
    }

    public void setWebsiteURL(StringFilter websiteURL) {
        this.websiteURL = websiteURL;
    }

    public StringFilter getBuildingNo() {
        return buildingNo;
    }

    public StringFilter buildingNo() {
        if (buildingNo == null) {
            buildingNo = new StringFilter();
        }
        return buildingNo;
    }

    public void setBuildingNo(StringFilter buildingNo) {
        this.buildingNo = buildingNo;
    }

    public StringFilter getVendorStreetName() {
        return vendorStreetName;
    }

    public StringFilter vendorStreetName() {
        if (vendorStreetName == null) {
            vendorStreetName = new StringFilter();
        }
        return vendorStreetName;
    }

    public void setVendorStreetName(StringFilter vendorStreetName) {
        this.vendorStreetName = vendorStreetName;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public StringFilter zipCode() {
        if (zipCode == null) {
            zipCode = new StringFilter();
        }
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
    }

    public StringFilter getDistrictName() {
        return districtName;
    }

    public StringFilter districtName() {
        if (districtName == null) {
            districtName = new StringFilter();
        }
        return districtName;
    }

    public void setDistrictName(StringFilter districtName) {
        this.districtName = districtName;
    }

    public StringFilter getAdditionalNo() {
        return additionalNo;
    }

    public StringFilter additionalNo() {
        if (additionalNo == null) {
            additionalNo = new StringFilter();
        }
        return additionalNo;
    }

    public void setAdditionalNo(StringFilter additionalNo) {
        this.additionalNo = additionalNo;
    }

    public StringFilter getCityName() {
        return cityName;
    }

    public StringFilter cityName() {
        if (cityName == null) {
            cityName = new StringFilter();
        }
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
    }

    public StringFilter getUnitNo() {
        return unitNo;
    }

    public StringFilter unitNo() {
        if (unitNo == null) {
            unitNo = new StringFilter();
        }
        return unitNo;
    }

    public void setUnitNo(StringFilter unitNo) {
        this.unitNo = unitNo;
    }

    public CountryFilter getCountry() {
        return country;
    }

    public CountryFilter country() {
        if (country == null) {
            country = new CountryFilter();
        }
        return country;
    }

    public void setCountry(CountryFilter country) {
        this.country = country;
    }

    public StringFilter getCash() {
        return cash;
    }

    public StringFilter cash() {
        if (cash == null) {
            cash = new StringFilter();
        }
        return cash;
    }

    public void setCash(StringFilter cash) {
        this.cash = cash;
    }

    public StringFilter getCredit() {
        return credit;
    }

    public StringFilter credit() {
        if (credit == null) {
            credit = new StringFilter();
        }
        return credit;
    }

    public void setCredit(StringFilter credit) {
        this.credit = credit;
    }

    public StringFilter getLetterOfCredit() {
        return letterOfCredit;
    }

    public StringFilter letterOfCredit() {
        if (letterOfCredit == null) {
            letterOfCredit = new StringFilter();
        }
        return letterOfCredit;
    }

    public void setLetterOfCredit(StringFilter letterOfCredit) {
        this.letterOfCredit = letterOfCredit;
    }

    public StringFilter getOthers() {
        return others;
    }

    public StringFilter others() {
        if (others == null) {
            others = new StringFilter();
        }
        return others;
    }

    public void setOthers(StringFilter others) {
        this.others = others;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendorCriteria that = (VendorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vendorNameEnglish, that.vendorNameEnglish) &&
            Objects.equals(vendorNameArabic, that.vendorNameArabic) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(vendorAccountNumber, that.vendorAccountNumber) &&
            Objects.equals(vendorCRNumber, that.vendorCRNumber) &&
            Objects.equals(vendorVATNumber, that.vendorVATNumber) &&
            Objects.equals(vendorType, that.vendorType) &&
            Objects.equals(vendorCategory, that.vendorCategory) &&
            Objects.equals(vendorEstablishmentDate, that.vendorEstablishmentDate) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(taken, that.taken) &&
            Objects.equals(uploaded, that.uploaded) &&
            Objects.equals(contactFullName, that.contactFullName) &&
            Objects.equals(contactEmailAddress, that.contactEmailAddress) &&
            Objects.equals(contactPrimaryPhoneNo, that.contactPrimaryPhoneNo) &&
            Objects.equals(contactPrimaryFaxNo, that.contactPrimaryFaxNo) &&
            Objects.equals(contactSecondaryPhoneNo, that.contactSecondaryPhoneNo) &&
            Objects.equals(contactSecondaryFaxNo, that.contactSecondaryFaxNo) &&
            Objects.equals(officeLocation, that.officeLocation) &&
            Objects.equals(officeFunctionality, that.officeFunctionality) &&
            Objects.equals(websiteURL, that.websiteURL) &&
            Objects.equals(buildingNo, that.buildingNo) &&
            Objects.equals(vendorStreetName, that.vendorStreetName) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(districtName, that.districtName) &&
            Objects.equals(additionalNo, that.additionalNo) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(unitNo, that.unitNo) &&
            Objects.equals(country, that.country) &&
            Objects.equals(cash, that.cash) &&
            Objects.equals(credit, that.credit) &&
            Objects.equals(letterOfCredit, that.letterOfCredit) &&
            Objects.equals(others, that.others) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vendorNameEnglish,
            vendorNameArabic,
            vendorId,
            vendorAccountNumber,
            vendorCRNumber,
            vendorVATNumber,
            vendorType,
            vendorCategory,
            vendorEstablishmentDate,
            height,
            width,
            taken,
            uploaded,
            contactFullName,
            contactEmailAddress,
            contactPrimaryPhoneNo,
            contactPrimaryFaxNo,
            contactSecondaryPhoneNo,
            contactSecondaryFaxNo,
            officeLocation,
            officeFunctionality,
            websiteURL,
            buildingNo,
            vendorStreetName,
            zipCode,
            districtName,
            additionalNo,
            cityName,
            unitNo,
            country,
            cash,
            credit,
            letterOfCredit,
            others,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vendorNameEnglish != null ? "vendorNameEnglish=" + vendorNameEnglish + ", " : "") +
            (vendorNameArabic != null ? "vendorNameArabic=" + vendorNameArabic + ", " : "") +
            (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            (vendorAccountNumber != null ? "vendorAccountNumber=" + vendorAccountNumber + ", " : "") +
            (vendorCRNumber != null ? "vendorCRNumber=" + vendorCRNumber + ", " : "") +
            (vendorVATNumber != null ? "vendorVATNumber=" + vendorVATNumber + ", " : "") +
            (vendorType != null ? "vendorType=" + vendorType + ", " : "") +
            (vendorCategory != null ? "vendorCategory=" + vendorCategory + ", " : "") +
            (vendorEstablishmentDate != null ? "vendorEstablishmentDate=" + vendorEstablishmentDate + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (taken != null ? "taken=" + taken + ", " : "") +
            (uploaded != null ? "uploaded=" + uploaded + ", " : "") +
            (contactFullName != null ? "contactFullName=" + contactFullName + ", " : "") +
            (contactEmailAddress != null ? "contactEmailAddress=" + contactEmailAddress + ", " : "") +
            (contactPrimaryPhoneNo != null ? "contactPrimaryPhoneNo=" + contactPrimaryPhoneNo + ", " : "") +
            (contactPrimaryFaxNo != null ? "contactPrimaryFaxNo=" + contactPrimaryFaxNo + ", " : "") +
            (contactSecondaryPhoneNo != null ? "contactSecondaryPhoneNo=" + contactSecondaryPhoneNo + ", " : "") +
            (contactSecondaryFaxNo != null ? "contactSecondaryFaxNo=" + contactSecondaryFaxNo + ", " : "") +
            (officeLocation != null ? "officeLocation=" + officeLocation + ", " : "") +
            (officeFunctionality != null ? "officeFunctionality=" + officeFunctionality + ", " : "") +
            (websiteURL != null ? "websiteURL=" + websiteURL + ", " : "") +
            (buildingNo != null ? "buildingNo=" + buildingNo + ", " : "") +
            (vendorStreetName != null ? "vendorStreetName=" + vendorStreetName + ", " : "") +
            (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
            (districtName != null ? "districtName=" + districtName + ", " : "") +
            (additionalNo != null ? "additionalNo=" + additionalNo + ", " : "") +
            (cityName != null ? "cityName=" + cityName + ", " : "") +
            (unitNo != null ? "unitNo=" + unitNo + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (cash != null ? "cash=" + cash + ", " : "") +
            (credit != null ? "credit=" + credit + ", " : "") +
            (letterOfCredit != null ? "letterOfCredit=" + letterOfCredit + ", " : "") +
            (others != null ? "others=" + others + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
