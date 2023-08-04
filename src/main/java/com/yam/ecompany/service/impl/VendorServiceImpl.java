package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.repository.VendorRepository;
import com.yam.ecompany.service.VendorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vendor}.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor save(Vendor vendor) {
        log.debug("Request to save Vendor : {}", vendor);
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor update(Vendor vendor) {
        log.debug("Request to update Vendor : {}", vendor);
        return vendorRepository.save(vendor);
    }

    @Override
    public Optional<Vendor> partialUpdate(Vendor vendor) {
        log.debug("Request to partially update Vendor : {}", vendor);

        return vendorRepository
            .findById(vendor.getId())
            .map(existingVendor -> {
                if (vendor.getVendorNameEnglish() != null) {
                    existingVendor.setVendorNameEnglish(vendor.getVendorNameEnglish());
                }
                if (vendor.getVendorNameArabic() != null) {
                    existingVendor.setVendorNameArabic(vendor.getVendorNameArabic());
                }
                if (vendor.getVendorId() != null) {
                    existingVendor.setVendorId(vendor.getVendorId());
                }
                if (vendor.getVendorAccountNumber() != null) {
                    existingVendor.setVendorAccountNumber(vendor.getVendorAccountNumber());
                }
                if (vendor.getVendorCRNumber() != null) {
                    existingVendor.setVendorCRNumber(vendor.getVendorCRNumber());
                }
                if (vendor.getVendorVATNumber() != null) {
                    existingVendor.setVendorVATNumber(vendor.getVendorVATNumber());
                }
                if (vendor.getVendorType() != null) {
                    existingVendor.setVendorType(vendor.getVendorType());
                }
                if (vendor.getVendorCategory() != null) {
                    existingVendor.setVendorCategory(vendor.getVendorCategory());
                }
                if (vendor.getVendorEstablishmentDate() != null) {
                    existingVendor.setVendorEstablishmentDate(vendor.getVendorEstablishmentDate());
                }
                if (vendor.getVendorLogo() != null) {
                    existingVendor.setVendorLogo(vendor.getVendorLogo());
                }
                if (vendor.getVendorLogoContentType() != null) {
                    existingVendor.setVendorLogoContentType(vendor.getVendorLogoContentType());
                }
                if (vendor.getHeight() != null) {
                    existingVendor.setHeight(vendor.getHeight());
                }
                if (vendor.getWidth() != null) {
                    existingVendor.setWidth(vendor.getWidth());
                }
                if (vendor.getTaken() != null) {
                    existingVendor.setTaken(vendor.getTaken());
                }
                if (vendor.getUploaded() != null) {
                    existingVendor.setUploaded(vendor.getUploaded());
                }
                if (vendor.getContactFullName() != null) {
                    existingVendor.setContactFullName(vendor.getContactFullName());
                }
                if (vendor.getContactEmailAddress() != null) {
                    existingVendor.setContactEmailAddress(vendor.getContactEmailAddress());
                }
                if (vendor.getContactPrimaryPhoneNo() != null) {
                    existingVendor.setContactPrimaryPhoneNo(vendor.getContactPrimaryPhoneNo());
                }
                if (vendor.getContactPrimaryFaxNo() != null) {
                    existingVendor.setContactPrimaryFaxNo(vendor.getContactPrimaryFaxNo());
                }
                if (vendor.getContactSecondaryPhoneNo() != null) {
                    existingVendor.setContactSecondaryPhoneNo(vendor.getContactSecondaryPhoneNo());
                }
                if (vendor.getContactSecondaryFaxNo() != null) {
                    existingVendor.setContactSecondaryFaxNo(vendor.getContactSecondaryFaxNo());
                }
                if (vendor.getOfficeLocation() != null) {
                    existingVendor.setOfficeLocation(vendor.getOfficeLocation());
                }
                if (vendor.getOfficeFunctionality() != null) {
                    existingVendor.setOfficeFunctionality(vendor.getOfficeFunctionality());
                }
                if (vendor.getWebsiteURL() != null) {
                    existingVendor.setWebsiteURL(vendor.getWebsiteURL());
                }
                if (vendor.getBuildingNo() != null) {
                    existingVendor.setBuildingNo(vendor.getBuildingNo());
                }
                if (vendor.getVendorStreetName() != null) {
                    existingVendor.setVendorStreetName(vendor.getVendorStreetName());
                }
                if (vendor.getZipCode() != null) {
                    existingVendor.setZipCode(vendor.getZipCode());
                }
                if (vendor.getDistrictName() != null) {
                    existingVendor.setDistrictName(vendor.getDistrictName());
                }
                if (vendor.getAdditionalNo() != null) {
                    existingVendor.setAdditionalNo(vendor.getAdditionalNo());
                }
                if (vendor.getCityName() != null) {
                    existingVendor.setCityName(vendor.getCityName());
                }
                if (vendor.getUnitNo() != null) {
                    existingVendor.setUnitNo(vendor.getUnitNo());
                }
                if (vendor.getCountry() != null) {
                    existingVendor.setCountry(vendor.getCountry());
                }
                if (vendor.getGoogleMap() != null) {
                    existingVendor.setGoogleMap(vendor.getGoogleMap());
                }
                if (vendor.getCombinedAddress() != null) {
                    existingVendor.setCombinedAddress(vendor.getCombinedAddress());
                }
                if (vendor.getcRCertificateUpload() != null) {
                    existingVendor.setcRCertificateUpload(vendor.getcRCertificateUpload());
                }
                if (vendor.getcRCertificateUploadContentType() != null) {
                    existingVendor.setcRCertificateUploadContentType(vendor.getcRCertificateUploadContentType());
                }
                if (vendor.getvATCertificateUpload() != null) {
                    existingVendor.setvATCertificateUpload(vendor.getvATCertificateUpload());
                }
                if (vendor.getvATCertificateUploadContentType() != null) {
                    existingVendor.setvATCertificateUploadContentType(vendor.getvATCertificateUploadContentType());
                }
                if (vendor.getNationalAddressUpload() != null) {
                    existingVendor.setNationalAddressUpload(vendor.getNationalAddressUpload());
                }
                if (vendor.getNationalAddressUploadContentType() != null) {
                    existingVendor.setNationalAddressUploadContentType(vendor.getNationalAddressUploadContentType());
                }
                if (vendor.getCompanyProfileUpload() != null) {
                    existingVendor.setCompanyProfileUpload(vendor.getCompanyProfileUpload());
                }
                if (vendor.getCompanyProfileUploadContentType() != null) {
                    existingVendor.setCompanyProfileUploadContentType(vendor.getCompanyProfileUploadContentType());
                }
                if (vendor.getOtherUpload() != null) {
                    existingVendor.setOtherUpload(vendor.getOtherUpload());
                }
                if (vendor.getOtherUploadContentType() != null) {
                    existingVendor.setOtherUploadContentType(vendor.getOtherUploadContentType());
                }
                if (vendor.getCash() != null) {
                    existingVendor.setCash(vendor.getCash());
                }
                if (vendor.getCredit() != null) {
                    existingVendor.setCredit(vendor.getCredit());
                }
                if (vendor.getLetterOfCredit() != null) {
                    existingVendor.setLetterOfCredit(vendor.getLetterOfCredit());
                }
                if (vendor.getOthers() != null) {
                    existingVendor.setOthers(vendor.getOthers());
                }

                return existingVendor;
            })
            .map(vendorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vendor> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vendor> findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        return vendorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.deleteById(id);
    }
}
