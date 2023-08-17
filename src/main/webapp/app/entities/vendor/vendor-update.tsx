import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVendor } from 'app/shared/model/vendor.model';
import { VendorType } from 'app/shared/model/enumerations/vendor-type.model';
import { VendorCategory } from 'app/shared/model/enumerations/vendor-category.model';
import { OfficeFunctionality } from 'app/shared/model/enumerations/office-functionality.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { getEntity, updateEntity, createEntity, reset } from './vendor.reducer';

export const VendorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendorEntity = useAppSelector(state => state.vendor.entity);
  const loading = useAppSelector(state => state.vendor.loading);
  const updating = useAppSelector(state => state.vendor.updating);
  const updateSuccess = useAppSelector(state => state.vendor.updateSuccess);
  const vendorTypeValues = Object.keys(VendorType);
  const vendorCategoryValues = Object.keys(VendorCategory);
  const officeFunctionalityValues = Object.keys(OfficeFunctionality);
  const countryValues = Object.keys(Country);

  const handleClose = () => {
    navigate('/vendor' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.taken = convertDateTimeToServer(values.taken);
    values.uploaded = convertDateTimeToServer(values.uploaded);

    const entity = {
      ...vendorEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          taken: displayDefaultDateTime(),
          uploaded: displayDefaultDateTime(),
        }
      : {
          vendorType: 'LOCAL',
          vendorCategory: 'MANUFACTURER',
          officeFunctionality: 'MAIN',
          country: 'INDIA',
          ...vendorEntity,
          taken: convertDateTimeFromServer(vendorEntity.taken),
          uploaded: convertDateTimeFromServer(vendorEntity.uploaded),
        };
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="3">
          <h2 id="eCompanyApp.vendor.home.createOrEditLabel" data-cy="VendorCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.vendor.home.createOrEditLabel">Create or edit a Vendor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="6">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="vendor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorId')}
                id="vendor-vendorId"
                name="vendorId"
                className="validated-field-container"
                data-cy="vendorId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorNameEnglish')}
                id="vendor-vendorNameEnglish"
                name="vendorNameEnglish"
                className="validated-field-container"
                data-cy="vendorNameEnglish"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorNameArabic')}
                id="vendor-vendorNameArabic"
                name="vendorNameArabic"
                className="validated-field-container"
                data-cy="vendorNameArabic"
                type="text"
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.vendorLogo')}
                id="vendor-vendorLogo"
                name="vendorLogo"
                className="validated-blob-field-container"
                required
                data-cy="vendorLogo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorAccountNumber')}
                id="vendor-vendorAccountNumber"
                name="vendorAccountNumber"
                className="validated-field-container"
                data-cy="vendorAccountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorCRNumber')}
                id="vendor-vendorCRNumber"
                name="vendorCRNumber"
                className="validated-field-container"
                data-cy="vendorCRNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorVATNumber')}
                id="vendor-vendorVATNumber"
                name="vendorVATNumber"
                className="validated-field-container"
                data-cy="vendorVATNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorType')}
                id="vendor-vendorType"
                name="vendorType"
                className="validated-field-container"
                data-cy="vendorType"
                type="select"
              >
                {vendorTypeValues.map(vendorType => (
                  <option value={vendorType} key={vendorType}>
                    {translate('eCompanyApp.VendorType.' + vendorType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorCategory')}
                id="vendor-vendorCategory"
                name="vendorCategory"
                className="validated-field-container"
                data-cy="vendorCategory"
                type="select"
              >
                {vendorCategoryValues.map(vendorCategory => (
                  <option value={vendorCategory} key={vendorCategory}>
                    {translate('eCompanyApp.VendorCategory.' + vendorCategory)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorEstablishmentDate')}
                id="vendor-vendorEstablishmentDate"
                name="vendorEstablishmentDate"
                className="validated-field-container"
                data-cy="vendorEstablishmentDate"
                type="text"
              />
              <h2>PRIMARY CONTACT DETAILS</h2>
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactFullName')}
                id="vendor-contactFullName"
                name="contactFullName"
                className="validated-field-container"
                data-cy="contactFullName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactEmailAddress')}
                id="vendor-contactEmailAddress"
                name="contactEmailAddress"
                className="validated-field-container"
                data-cy="contactEmailAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactPrimaryPhoneNo')}
                id="vendor-contactPrimaryPhoneNo"
                name="contactPrimaryPhoneNo"
                className="validated-field-container"
                data-cy="contactPrimaryPhoneNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactPrimaryFaxNo')}
                id="vendor-contactPrimaryFaxNo"
                name="contactPrimaryFaxNo"
                className="validated-field-container"
                data-cy="contactPrimaryFaxNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactSecondaryPhoneNo')}
                id="vendor-contactSecondaryPhoneNo"
                name="contactSecondaryPhoneNo"
                className="validated-field-container"
                data-cy="contactSecondaryPhoneNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactSecondaryFaxNo')}
                id="vendor-contactSecondaryFaxNo"
                name="contactSecondaryFaxNo"
                className="validated-field-container"
                data-cy="contactSecondaryFaxNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.officeLocation')}
                id="vendor-officeLocation"
                name="officeLocation"
                className="validated-field-container"
                data-cy="officeLocation"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.officeFunctionality')}
                id="vendor-officeFunctionality"
                name="officeFunctionality"
                className="validated-field-container"
                data-cy="officeFunctionality"
                type="select"
              >
                {officeFunctionalityValues.map(officeFunctionality => (
                  <option value={officeFunctionality} key={officeFunctionality}>
                    {translate('eCompanyApp.OfficeFunctionality.' + officeFunctionality)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendor.websiteURL')}
                id="vendor-websiteURL"
                name="websiteURL"
                className="validated-field-container"
                data-cy="websiteURL"
                type="text"
              />
              <h2>NATIONAL ADDRESS</h2>
              <ValidatedField
                label={translate('eCompanyApp.vendor.buildingNo')}
                id="vendor-buildingNo"
                name="buildingNo"
                className="validated-field-container"
                data-cy="buildingNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorStreetName')}
                id="vendor-vendorStreetName"
                name="vendorStreetName"
                className="validated-field-container"
                data-cy="vendorStreetName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.zipCode')}
                id="vendor-zipCode"
                name="zipCode"
                className="validated-field-container"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.districtName')}
                id="vendor-districtName"
                name="districtName"
                className="validated-field-container"
                data-cy="districtName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.additionalNo')}
                id="vendor-additionalNo"
                name="additionalNo"
                className="validated-field-container"
                data-cy="additionalNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.cityName')}
                id="vendor-cityName"
                name="cityName"
                className="validated-field-container"
                data-cy="cityName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.unitNo')}
                id="vendor-unitNo"
                name="unitNo"
                className="validated-field-container"
                data-cy="unitNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.country')}
                id="vendor-country"
                name="country"
                className="validated-field-container"
                data-cy="country"
                type="select"
              >
                {countryValues.map(country => (
                  <option value={country} key={country}>
                    {translate('eCompanyApp.Country.' + country)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendor.cash')}
                id="vendor-cash"
                name="cash"
                className="validated-field-container"
                data-cy="cash"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.credit')}
                id="vendor-credit"
                name="credit"
                className="validated-field-container"
                data-cy="credit"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.letterOfCredit')}
                id="vendor-letterOfCredit"
                name="letterOfCredit"
                className="validated-field-container"
                data-cy="letterOfCredit"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.others')}
                id="vendor-others"
                name="others"
                className="validated-field-container"
                data-cy="others"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.googleMap')}
                id="vendor-googleMap"
                name="googleMap"
                className="validated-field-container"
                data-cy="googleMap"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.combinedAddress')}
                id="vendor-combinedAddress"
                name="combinedAddress"
                className="validated-field-container"
                data-cy="combinedAddress"
                type="textarea"
              />
              <h2>UPLOAD DOCUMENTS</h2>
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.cRCertificateUpload')}
                id="vendor-cRCertificateUpload"
                name="cRCertificateUpload"
                className="validated-field-container"
                data-cy="cRCertificateUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.vATCertificateUpload')}
                id="vendor-vATCertificateUpload"
                name="vATCertificateUpload"
                className="validated-field-container"
                data-cy="vATCertificateUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.nationalAddressUpload')}
                id="vendor-nationalAddressUpload"
                name="nationalAddressUpload"
                className="validated-field-container"
                data-cy="nationalAddressUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.companyProfileUpload')}
                id="vendor-companyProfileUpload"
                name="companyProfileUpload"
                className="validated-field-container"
                data-cy="companyProfileUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.otherUpload')}
                id="vendor-otherUpload"
                name="otherUpload"
                className="validated-field-container"
                data-cy="otherUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vendor" replace color="info mt-4 back_btn">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary mt-4 back_btn" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VendorUpdate;
