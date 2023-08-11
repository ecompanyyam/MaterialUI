import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vendor.reducer';

export const VendorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vendorEntity = useAppSelector(state => state.vendor.entity);
  return (
    <Row>
      <Col md="12">
        <h2 data-cy="vendorDetailsHeading">
          <Translate contentKey="eCompanyApp.vendor.detail.title">Vendor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.id}</dd>
          <dt>
            <span id="vendorNameEnglish">
              <Translate contentKey="eCompanyApp.vendor.vendorNameEnglish">Vendor Name English</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorNameEnglish}</dd>
          <dt>
            <span id="vendorNameArabic">
              <Translate contentKey="eCompanyApp.vendor.vendorNameArabic">Vendor Name Arabic</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorNameArabic}</dd>
          <dt>
            <span id="vendorId">
              <Translate contentKey="eCompanyApp.vendor.vendorId">Vendor Id</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorId}</dd>
          <dt>
            <span id="vendorAccountNumber">
              <Translate contentKey="eCompanyApp.vendor.vendorAccountNumber">Vendor Account Number</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorAccountNumber}</dd>
          <dt>
            <span id="vendorCRNumber">
              <Translate contentKey="eCompanyApp.vendor.vendorCRNumber">Vendor CR Number</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorCRNumber}</dd>
          <dt>
            <span id="vendorVATNumber">
              <Translate contentKey="eCompanyApp.vendor.vendorVATNumber">Vendor VAT Number</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorVATNumber}</dd>
          <dt>
            <span id="vendorType">
              <Translate contentKey="eCompanyApp.vendor.vendorType">Vendor Type</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorType}</dd>
          <dt>
            <span id="vendorCategory">
              <Translate contentKey="eCompanyApp.vendor.vendorCategory">Vendor Category</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorCategory}</dd>
          <dt>
            <span id="vendorEstablishmentDate">
              <Translate contentKey="eCompanyApp.vendor.vendorEstablishmentDate">Vendor Establishment Date</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorEstablishmentDate}</dd>
          <dt>
            <span id="vendorLogo">
              <Translate contentKey="eCompanyApp.vendor.vendorLogo">Vendor Logo</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.vendorLogo ? (
              <div>
                {vendorEntity.vendorLogoContentType ? (
                  <a onClick={openFile(vendorEntity.vendorLogoContentType, vendorEntity.vendorLogo)}>
                    <img
                      src={`data:${vendorEntity.vendorLogoContentType};base64,${vendorEntity.vendorLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {vendorEntity.vendorLogoContentType}, {byteSize(vendorEntity.vendorLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="height">
              <Translate contentKey="eCompanyApp.vendor.height">Height</Translate>
            </span>
          </dt>
          <dt>
            <span id="contactFullName">
              <Translate contentKey="eCompanyApp.vendor.contactFullName">Contact Full Name</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactFullName}</dd>
          <dt>
            <span id="contactEmailAddress">
              <Translate contentKey="eCompanyApp.vendor.contactEmailAddress">Contact Email Address</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactEmailAddress}</dd>
          <dt>
            <span id="contactPrimaryPhoneNo">
              <Translate contentKey="eCompanyApp.vendor.contactPrimaryPhoneNo">Contact Primary Phone No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactPrimaryPhoneNo}</dd>
          <dt>
            <span id="contactPrimaryFaxNo">
              <Translate contentKey="eCompanyApp.vendor.contactPrimaryFaxNo">Contact Primary Fax No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactPrimaryFaxNo}</dd>
          <dt>
            <span id="contactSecondaryPhoneNo">
              <Translate contentKey="eCompanyApp.vendor.contactSecondaryPhoneNo">Contact Secondary Phone No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactSecondaryPhoneNo}</dd>
          <dt>
            <span id="contactSecondaryFaxNo">
              <Translate contentKey="eCompanyApp.vendor.contactSecondaryFaxNo">Contact Secondary Fax No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.contactSecondaryFaxNo}</dd>
          <dt>
            <span id="officeLocation">
              <Translate contentKey="eCompanyApp.vendor.officeLocation">Office Location</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.officeLocation}</dd>
          <dt>
            <span id="officeFunctionality">
              <Translate contentKey="eCompanyApp.vendor.officeFunctionality">Office Functionality</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.officeFunctionality}</dd>
          <dt>
            <span id="websiteURL">
              <Translate contentKey="eCompanyApp.vendor.websiteURL">Website URL</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.websiteURL}</dd>
          <dt>
            <span id="buildingNo">
              <Translate contentKey="eCompanyApp.vendor.buildingNo">Building No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.buildingNo}</dd>
          <dt>
            <span id="vendorStreetName">
              <Translate contentKey="eCompanyApp.vendor.vendorStreetName">Vendor Street Name</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.vendorStreetName}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="eCompanyApp.vendor.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.zipCode}</dd>
          <dt>
            <span id="districtName">
              <Translate contentKey="eCompanyApp.vendor.districtName">District Name</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.districtName}</dd>
          <dt>
            <span id="additionalNo">
              <Translate contentKey="eCompanyApp.vendor.additionalNo">Additional No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.additionalNo}</dd>
          <dt>
            <span id="cityName">
              <Translate contentKey="eCompanyApp.vendor.cityName">City Name</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.cityName}</dd>
          <dt>
            <span id="unitNo">
              <Translate contentKey="eCompanyApp.vendor.unitNo">Unit No</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.unitNo}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="eCompanyApp.vendor.country">Country</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.country}</dd>
          <dt>
            <span id="googleMap">
              <Translate contentKey="eCompanyApp.vendor.googleMap">Google Map</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.googleMap}</dd>
          <dt>
            <span id="combinedAddress">
              <Translate contentKey="eCompanyApp.vendor.combinedAddress">Combined Address</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.combinedAddress}</dd>
          <dt>
            <span id="cRCertificateUpload">
              <Translate contentKey="eCompanyApp.vendor.cRCertificateUpload">C R Certificate Upload</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.cRCertificateUpload ? (
              <div>
                {vendorEntity.cRCertificateUploadContentType ? (
                  <a onClick={openFile(vendorEntity.cRCertificateUploadContentType, vendorEntity.cRCertificateUpload)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {vendorEntity.cRCertificateUploadContentType}, {byteSize(vendorEntity.cRCertificateUpload)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="vATCertificateUpload">
              <Translate contentKey="eCompanyApp.vendor.vATCertificateUpload">V AT Certificate Upload</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.vATCertificateUpload ? (
              <div>
                {vendorEntity.vATCertificateUploadContentType ? (
                  <a onClick={openFile(vendorEntity.vATCertificateUploadContentType, vendorEntity.vATCertificateUpload)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {vendorEntity.vATCertificateUploadContentType}, {byteSize(vendorEntity.vATCertificateUpload)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="nationalAddressUpload">
              <Translate contentKey="eCompanyApp.vendor.nationalAddressUpload">National Address Upload</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.nationalAddressUpload ? (
              <div>
                {vendorEntity.nationalAddressUploadContentType ? (
                  <a onClick={openFile(vendorEntity.nationalAddressUploadContentType, vendorEntity.nationalAddressUpload)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {vendorEntity.nationalAddressUploadContentType}, {byteSize(vendorEntity.nationalAddressUpload)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="companyProfileUpload">
              <Translate contentKey="eCompanyApp.vendor.companyProfileUpload">Company Profile Upload</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.companyProfileUpload ? (
              <div>
                {vendorEntity.companyProfileUploadContentType ? (
                  <a onClick={openFile(vendorEntity.companyProfileUploadContentType, vendorEntity.companyProfileUpload)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {vendorEntity.companyProfileUploadContentType}, {byteSize(vendorEntity.companyProfileUpload)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="otherUpload">
              <Translate contentKey="eCompanyApp.vendor.otherUpload">Other Upload</Translate>
            </span>
          </dt>
          <dd>
            {vendorEntity.otherUpload ? (
              <div>
                {vendorEntity.otherUploadContentType ? (
                  <a onClick={openFile(vendorEntity.otherUploadContentType, vendorEntity.otherUpload)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {vendorEntity.otherUploadContentType}, {byteSize(vendorEntity.otherUpload)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="cash">
              <Translate contentKey="eCompanyApp.vendor.cash">Cash</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.cash}</dd>
          <dt>
            <span id="credit">
              <Translate contentKey="eCompanyApp.vendor.credit">Credit</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.credit}</dd>
          <dt>
            <span id="letterOfCredit">
              <Translate contentKey="eCompanyApp.vendor.letterOfCredit">Letter Of Credit</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.letterOfCredit}</dd>
          <dt>
            <span id="others">
              <Translate contentKey="eCompanyApp.vendor.others">Others</Translate>
            </span>
          </dt>
          <dd>{vendorEntity.others}</dd>
        </dl>
        <Button tag={Link} to="/vendor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vendor/${vendorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VendorDetail;
