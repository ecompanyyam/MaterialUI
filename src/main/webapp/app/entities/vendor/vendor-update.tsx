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
  const metadata = (
    <div>
      <ValidatedField label={translate('eCompanyApp.vendor.height')} id="vendor-height" name="height" data-cy="height" type="text" />
      <ValidatedField label={translate('eCompanyApp.vendor.width')} id="vendor-width" name="width" data-cy="width" type="text" />
      <ValidatedField
        label={translate('eCompanyApp.vendor.taken')}
        id="vendor-taken"
        name="taken"
        data-cy="taken"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
      <ValidatedField
        label={translate('eCompanyApp.vendor.uploaded')}
        id="vendor-uploaded"
        name="uploaded"
        data-cy="uploaded"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
    </div>
  );
  const metadataRows = isNew ? '' : metadata;
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCompanyApp.vendor.home.createOrEditLabel" data-cy="VendorCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.vendor.home.createOrEditLabel">Create or edit a Vendor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="12">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm className="row" defaultValues={defaultValues()} onSubmit={saveEntity}>
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
                label={translate('eCompanyApp.vendor.vendorNameEnglish')}
                id="vendor-vendorNameEnglish"
                name="vendorNameEnglish"
                className="col-lg-3"
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
                className="col-lg-3"
                data-cy="vendorNameArabic"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorId')}
                id="vendor-vendorId"
                name="vendorId"
                className="col-lg-3"
                data-cy="vendorId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorAccountNumber')}
                id="vendor-vendorAccountNumber"
                name="vendorAccountNumber"
                className="col-lg-3"
                data-cy="vendorAccountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorCRNumber')}
                id="vendor-vendorCRNumber"
                name="vendorCRNumber"
                className="col-lg-3"
                data-cy="vendorCRNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorVATNumber')}
                id="vendor-vendorVATNumber"
                name="vendorVATNumber"
                className="col-lg-3"
                data-cy="vendorVATNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorType')}
                id="vendor-vendorType"
                name="vendorType"
                className="col-lg-3"
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
                className="col-lg-3"
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
                className="col-lg-3"
                data-cy="vendorEstablishmentDate"
                type="text"
              />
              {metadataRows}
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.vendorLogo')}
                id="vendor-vendorLogo"
                name="vendorLogo"
                className="col-lg-3"
                data-cy="vendorLogo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactFullName')}
                id="vendor-contactFullName"
                name="contactFullName"
                className="col-lg-3"
                data-cy="contactFullName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactEmailAddress')}
                id="vendor-contactEmailAddress"
                name="contactEmailAddress"
                className="col-lg-3"
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
                className="col-lg-3"
                data-cy="contactPrimaryPhoneNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactPrimaryFaxNo')}
                id="vendor-contactPrimaryFaxNo"
                name="contactPrimaryFaxNo"
                className="col-lg-3"
                data-cy="contactPrimaryFaxNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactSecondaryPhoneNo')}
                id="vendor-contactSecondaryPhoneNo"
                name="contactSecondaryPhoneNo"
                className="col-lg-3"
                data-cy="contactSecondaryPhoneNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.contactSecondaryFaxNo')}
                id="vendor-contactSecondaryFaxNo"
                name="contactSecondaryFaxNo"
                className="col-lg-3"
                data-cy="contactSecondaryFaxNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.officeLocation')}
                id="vendor-officeLocation"
                name="officeLocation"
                className="col-lg-3"
                data-cy="officeLocation"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.officeFunctionality')}
                id="vendor-officeFunctionality"
                name="officeFunctionality"
                className="col-lg-3"
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
                className="col-lg-3"
                data-cy="websiteURL"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.buildingNo')}
                id="vendor-buildingNo"
                name="buildingNo"
                className="col-lg-3"
                data-cy="buildingNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.vendorStreetName')}
                id="vendor-vendorStreetName"
                name="vendorStreetName"
                className="col-lg-3"
                data-cy="vendorStreetName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.zipCode')}
                id="vendor-zipCode"
                className="col-lg-3"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.districtName')}
                id="vendor-districtName"
                name="districtName"
                className="col-lg-3"
                data-cy="districtName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.additionalNo')}
                id="vendor-additionalNo"
                name="additionalNo"
                className="col-lg-3"
                data-cy="additionalNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.cityName')}
                id="vendor-cityName"
                name="cityName"
                className="col-lg-3"
                data-cy="cityName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.unitNo')}
                id="vendor-unitNo"
                name="unitNo"
                className="col-lg-3"
                data-cy="unitNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.country')}
                id="vendor-country"
                name="country"
                className="col-lg-3"
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
                label={translate('eCompanyApp.vendor.googleMap')}
                id="vendor-googleMap"
                name="googleMap"
                className="col-lg-3"
                data-cy="googleMap"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.combinedAddress')}
                id="vendor-combinedAddress"
                name="combinedAddress"
                className="col-lg-3"
                data-cy="combinedAddress"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.cRCertificateUpload')}
                id="vendor-cRCertificateUpload"
                name="cRCertificateUpload"
                className="col-lg-3"
                data-cy="cRCertificateUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.vATCertificateUpload')}
                id="vendor-vATCertificateUpload"
                name="vATCertificateUpload"
                className="col-lg-3"
                data-cy="vATCertificateUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.nationalAddressUpload')}
                id="vendor-nationalAddressUpload"
                name="nationalAddressUpload"
                className="col-lg-3"
                data-cy="nationalAddressUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.companyProfileUpload')}
                id="vendor-companyProfileUpload"
                name="companyProfileUpload"
                className="col-lg-3"
                data-cy="companyProfileUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.vendor.otherUpload')}
                id="vendor-otherUpload"
                name="otherUpload"
                className="col-lg-3"
                data-cy="otherUpload"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.cash')}
                id="vendor-cash"
                name="cash"
                data-cy="cash"
                className="col-lg-3"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.credit')}
                id="vendor-credit"
                name="credit"
                className="col-lg-3"
                data-cy="credit"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.letterOfCredit')}
                id="vendor-letterOfCredit"
                name="letterOfCredit"
                className="col-lg-3"
                data-cy="letterOfCredit"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendor.others')}
                id="vendor-others"
                name="others"
                className="col-lg-3"
                data-cy="others"
                type="text"
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
