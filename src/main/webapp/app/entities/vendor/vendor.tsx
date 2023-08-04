import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faLongArrowUp, faLongArrowDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vendor.reducer';

export const Vendor = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const vendorList = useAppSelector(state => state.vendor.entities);
  const loading = useAppSelector(state => state.vendor.loading);
  const totalItems = useAppSelector(state => state.vendor.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faLongArrowUp : faLongArrowDown;
    }
  };

  return (
    <div>
      <h2 id="vendor-heading" data-cy="VendorHeading">
        <Translate contentKey="eCompanyApp.vendor.home.title">Vendors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.vendor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vendor/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.vendor.home.createLabel">Create new Vendor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vendorList && vendorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.vendor.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('vendorNameEnglish')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorNameEnglish">Vendor Name English</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorNameEnglish')} />
                </th>
                <th className="hand" onClick={sort('vendorNameArabic')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorNameArabic">Vendor Name Arabic</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorNameArabic')} />
                </th>
                <th className="hand" onClick={sort('vendorId')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorId">Vendor Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorId')} />
                </th>
                <th className="hand" onClick={sort('vendorAccountNumber')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorAccountNumber">Vendor Account Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorAccountNumber')} />
                </th>
                <th className="hand" onClick={sort('vendorCRNumber')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorCRNumber">Vendor CR Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorCRNumber')} />
                </th>
                <th className="hand" onClick={sort('vendorVATNumber')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorVATNumber">Vendor VAT Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorVATNumber')} />
                </th>
                <th className="hand" onClick={sort('vendorType')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorType">Vendor Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorType')} />
                </th>
                <th className="hand" onClick={sort('vendorCategory')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorCategory">Vendor Category</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorCategory')} />
                </th>
                <th className="hand" onClick={sort('vendorEstablishmentDate')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorEstablishmentDate">Vendor Establishment Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorEstablishmentDate')} />
                </th>
                <th className="hand" onClick={sort('vendorLogo')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorLogo">Vendor Logo</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorLogo')} />
                </th>
                <th className="hand" onClick={sort('contactFullName')}>
                  <Translate contentKey="eCompanyApp.vendor.contactFullName">Contact Full Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactFullName')} />
                </th>
                <th className="hand" onClick={sort('contactEmailAddress')}>
                  <Translate contentKey="eCompanyApp.vendor.contactEmailAddress">Contact Email Address</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactEmailAddress')} />
                </th>
                <th className="hand" onClick={sort('contactPrimaryPhoneNo')}>
                  <Translate contentKey="eCompanyApp.vendor.contactPrimaryPhoneNo">Contact Primary Phone No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactPrimaryPhoneNo')} />
                </th>
                <th className="hand" onClick={sort('contactPrimaryFaxNo')}>
                  <Translate contentKey="eCompanyApp.vendor.contactPrimaryFaxNo">Contact Primary Fax No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactPrimaryFaxNo')} />
                </th>
                <th className="hand" onClick={sort('contactSecondaryPhoneNo')}>
                  <Translate contentKey="eCompanyApp.vendor.contactSecondaryPhoneNo">Contact Secondary Phone No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactSecondaryPhoneNo')} />
                </th>
                <th className="hand" onClick={sort('contactSecondaryFaxNo')}>
                  <Translate contentKey="eCompanyApp.vendor.contactSecondaryFaxNo">Contact Secondary Fax No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contactSecondaryFaxNo')} />
                </th>
                <th className="hand" onClick={sort('officeLocation')}>
                  <Translate contentKey="eCompanyApp.vendor.officeLocation">Office Location</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('officeLocation')} />
                </th>
                <th className="hand" onClick={sort('officeFunctionality')}>
                  <Translate contentKey="eCompanyApp.vendor.officeFunctionality">Office Functionality</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('officeFunctionality')} />
                </th>
                <th className="hand" onClick={sort('websiteURL')}>
                  <Translate contentKey="eCompanyApp.vendor.websiteURL">Website URL</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('websiteURL')} />
                </th>
                <th className="hand" onClick={sort('buildingNo')}>
                  <Translate contentKey="eCompanyApp.vendor.buildingNo">Building No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('buildingNo')} />
                </th>
                <th className="hand" onClick={sort('vendorStreetName')}>
                  <Translate contentKey="eCompanyApp.vendor.vendorStreetName">Vendor Street Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vendorStreetName')} />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="eCompanyApp.vendor.zipCode">Zip Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('zipCode')} />
                </th>
                <th className="hand" onClick={sort('districtName')}>
                  <Translate contentKey="eCompanyApp.vendor.districtName">District Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('districtName')} />
                </th>
                <th className="hand" onClick={sort('additionalNo')}>
                  <Translate contentKey="eCompanyApp.vendor.additionalNo">Additional No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('additionalNo')} />
                </th>
                <th className="hand" onClick={sort('cityName')}>
                  <Translate contentKey="eCompanyApp.vendor.cityName">City Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cityName')} />
                </th>
                <th className="hand" onClick={sort('unitNo')}>
                  <Translate contentKey="eCompanyApp.vendor.unitNo">Unit No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unitNo')} />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="eCompanyApp.vendor.country">Country</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('country')} />
                </th>
                <th className="hand" onClick={sort('googleMap')}>
                  <Translate contentKey="eCompanyApp.vendor.googleMap">Google Map</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('googleMap')} />
                </th>
                <th className="hand" onClick={sort('combinedAddress')}>
                  <Translate contentKey="eCompanyApp.vendor.combinedAddress">Combined Address</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('combinedAddress')} />
                </th>
                <th className="hand" onClick={sort('cRCertificateUpload')}>
                  <Translate contentKey="eCompanyApp.vendor.cRCertificateUpload">C R Certificate Upload</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cRCertificateUpload')} />
                </th>
                <th className="hand" onClick={sort('vATCertificateUpload')}>
                  <Translate contentKey="eCompanyApp.vendor.vATCertificateUpload">V AT Certificate Upload</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vATCertificateUpload')} />
                </th>
                <th className="hand" onClick={sort('nationalAddressUpload')}>
                  <Translate contentKey="eCompanyApp.vendor.nationalAddressUpload">National Address Upload</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nationalAddressUpload')} />
                </th>
                <th className="hand" onClick={sort('companyProfileUpload')}>
                  <Translate contentKey="eCompanyApp.vendor.companyProfileUpload">Company Profile Upload</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('companyProfileUpload')} />
                </th>
                <th className="hand" onClick={sort('otherUpload')}>
                  <Translate contentKey="eCompanyApp.vendor.otherUpload">Other Upload</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('otherUpload')} />
                </th>
                <th className="hand" onClick={sort('cash')}>
                  <Translate contentKey="eCompanyApp.vendor.cash">Cash</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('cash')} />
                </th>
                <th className="hand" onClick={sort('credit')}>
                  <Translate contentKey="eCompanyApp.vendor.credit">Credit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('credit')} />
                </th>
                <th className="hand" onClick={sort('letterOfCredit')}>
                  <Translate contentKey="eCompanyApp.vendor.letterOfCredit">Letter Of Credit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('letterOfCredit')} />
                </th>
                <th className="hand" onClick={sort('others')}>
                  <Translate contentKey="eCompanyApp.vendor.others">Others</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('others')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vendorList.map((vendor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vendor/${vendor.id}`} color="link" size="sm">
                      {vendor.id}
                    </Button>
                  </td>
                  <td>{vendor.vendorNameEnglish}</td>
                  <td>{vendor.vendorNameArabic}</td>
                  <td>{vendor.vendorId}</td>
                  <td>{vendor.vendorAccountNumber}</td>
                  <td>{vendor.vendorCRNumber}</td>
                  <td>{vendor.vendorVATNumber}</td>
                  <td>
                    <Translate contentKey={`eCompanyApp.VendorType.${vendor.vendorType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.VendorCategory.${vendor.vendorCategory}`} />
                  </td>
                  <td>{vendor.vendorEstablishmentDate}</td>
                  <td>
                    {vendor.vendorLogo ? (
                      <div>
                        {vendor.vendorLogoContentType ? (
                          <a onClick={openFile(vendor.vendorLogoContentType, vendor.vendorLogo)}>
                            <img src={`data:${vendor.vendorLogoContentType};base64,${vendor.vendorLogo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.vendorLogoContentType}, {byteSize(vendor.vendorLogo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{vendor.contactFullName}</td>
                  <td>{vendor.contactEmailAddress}</td>
                  <td>{vendor.contactPrimaryPhoneNo}</td>
                  <td>{vendor.contactPrimaryFaxNo}</td>
                  <td>{vendor.contactSecondaryPhoneNo}</td>
                  <td>{vendor.contactSecondaryFaxNo}</td>
                  <td>{vendor.officeLocation}</td>
                  <td>
                    <Translate contentKey={`eCompanyApp.OfficeFunctionality.${vendor.officeFunctionality}`} />
                  </td>
                  <td>{vendor.websiteURL}</td>
                  <td>{vendor.buildingNo}</td>
                  <td>{vendor.vendorStreetName}</td>
                  <td>{vendor.zipCode}</td>
                  <td>{vendor.districtName}</td>
                  <td>{vendor.additionalNo}</td>
                  <td>{vendor.cityName}</td>
                  <td>{vendor.unitNo}</td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Country.${vendor.country}`} />
                  </td>
                  <td>
                    {vendor.googleMap.substring(0, 20)}
                    {vendor.googleMap.length >= 20 && '...'}
                  </td>
                  <td>
                    {vendor.combinedAddress.substring(0, 20)}
                    {vendor.combinedAddress.length >= 20 && '...'}
                  </td>
                  <td>
                    {vendor.cRCertificateUpload ? (
                      <div>
                        {vendor.cRCertificateUploadContentType ? (
                          <a onClick={openFile(vendor.cRCertificateUploadContentType, vendor.cRCertificateUpload)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.cRCertificateUploadContentType}, {byteSize(vendor.cRCertificateUpload)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {vendor.vATCertificateUpload ? (
                      <div>
                        {vendor.vATCertificateUploadContentType ? (
                          <a onClick={openFile(vendor.vATCertificateUploadContentType, vendor.vATCertificateUpload)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.vATCertificateUploadContentType}, {byteSize(vendor.vATCertificateUpload)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {vendor.nationalAddressUpload ? (
                      <div>
                        {vendor.nationalAddressUploadContentType ? (
                          <a onClick={openFile(vendor.nationalAddressUploadContentType, vendor.nationalAddressUpload)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.nationalAddressUploadContentType}, {byteSize(vendor.nationalAddressUpload)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {vendor.companyProfileUpload ? (
                      <div>
                        {vendor.companyProfileUploadContentType ? (
                          <a onClick={openFile(vendor.companyProfileUploadContentType, vendor.companyProfileUpload)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.companyProfileUploadContentType}, {byteSize(vendor.companyProfileUpload)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {vendor.otherUpload ? (
                      <div>
                        {vendor.otherUploadContentType ? (
                          <a onClick={openFile(vendor.otherUploadContentType, vendor.otherUpload)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {vendor.otherUploadContentType}, {byteSize(vendor.otherUpload)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{vendor.cash}</td>
                  <td>{vendor.credit}</td>
                  <td>{vendor.letterOfCredit}</td>
                  <td>{vendor.others}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vendor/${vendor.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vendor/${vendor.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vendor/${vendor.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eCompanyApp.vendor.home.notFound">No Vendors found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={vendorList && vendorList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Vendor;
