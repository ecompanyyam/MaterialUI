import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, Nav, NavItem, NavLink, TabContent, TabPane } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import classnames from 'classnames';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { VendorType } from 'app/shared/model/enumerations/vendor-type.model';
import { VendorCategory } from 'app/shared/model/enumerations/vendor-category.model';
import { OfficeFunctionality } from 'app/shared/model/enumerations/office-functionality.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { getEntity, updateEntity, createEntity, reset } from './vendor.reducer';
import GoogleMapComponent from 'app/shared/GoogleMapComponent';

export const VendorUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const vendorEntity = useAppSelector(state => state.vendor.entity);
  const updating = useAppSelector(state => state.vendor.updating);
  const updateSuccess = useAppSelector(state => state.vendor.updateSuccess);
  const parseGoogleMap = (googleMap: string) => {
    const defaultLat = 37.7749; // Default latitude (e.g., San Francisco)
    const defaultLng = -122.4194; // Default longitude (e.g., San Francisco)
    if (googleMap) {
      const [latitude, longitude] = googleMap.split(',');
      return { latitude, longitude };
    } else {
      // Provide default values for latitude and longitude
      return { latitude: defaultLat, longitude: defaultLng }; // Replace defaultLatitude and defaultLongitude with actual default values
    }
  };
  const handleClose = () => {
    navigate('/vendor' + location.search);
  };
  // Step 1: Define state variables for tab data
  const [tab1Data, setTab1Data] = useState<any>({});
  const [tab2Data, setTab2Data] = useState<any>({});
  const [tab3Data, setTab3Data] = useState<any>({});
  const [tab4Data, setTab4Data] = useState<any>({});
  const [activeTab, setActiveTab] = useState(1);

  // Step 2: Define tab change handler
  const handleTabChange = (tabData: any, tabNumber: number) => {
    if (tabNumber === 1) {
      setTab1Data(tabData);
    } else if (tabNumber === 2) {
      setTab2Data(tabData);
    } else if (tabNumber === 3) {
      setTab3Data(tabData);
    } else if (tabNumber === 4) {
      setTab4Data(tabData);
    }
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
  useEffect(() => {
    if (updateSuccess) {
      // Save the modified Google Map location to the vendorEntity before navigating
      const updatedEntity = { ...vendorEntity, googleMap: vendorEntity.googleMap };
      if (isNew) {
        dispatch(createEntity(updatedEntity));
      } else {
        dispatch(updateEntity(updatedEntity));
      }
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
          ...parseGoogleMap(vendorEntity.googleMap),
        };
  return (
    <div>
      <Row className="justify-content-center blue-bg mb-2">
        <Col md="6">
          <h2 id="eCompanyApp.vendor.home.createOrEditLabel" data-cy="VendorCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.vendor.home.createOrEditLabel">Create or edit a Vendor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="6">
          <Nav className="nav-tabs-container" tabs>
            <NavItem>
              <NavLink className={classnames({ active: activeTab === 1 })} onClick={() => setActiveTab(1)}>
                VENDOR INFORMATION
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className={classnames({ active: activeTab === 2 })} onClick={() => setActiveTab(2)}>
                PRIMARY CONTACT
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className={classnames({ active: activeTab === 3 })} onClick={() => setActiveTab(3)}>
                NATIONAL ADDRESS
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className={classnames({ active: activeTab === 4 })} onClick={() => setActiveTab(4)}>
                UPLOAD DOCUMENTS
              </NavLink>
            </NavItem>
            {/* ... repeat for other tabs ... */}
          </Nav>
          <TabContent activeTab={activeTab}>
            <TabPane tabId={1}>
              <FormFieldsTab1
                tabData={tab1Data}
                defaultValues={defaultValues}
                saveEntity={saveEntity}
                onTabDataChange={data => handleTabChange(data, 1)}
              />
              <Button color="primary" className="next-button" onClick={() => setActiveTab(activeTab + 1)}>
                Next
              </Button>
            </TabPane>
            <TabPane tabId={2}>
              <FormFieldsTab2
                tabData={tab2Data}
                onTabDataChange={data => handleTabChange(data, 2)}
                defaultValues={defaultValues}
                saveEntity={saveEntity}
              />
              <Button color="primary" className="next-button" onClick={() => setActiveTab(activeTab + 1)}>
                Next
              </Button>
            </TabPane>
            <TabPane tabId={3}>
              <FormFieldsTab3
                tabData={tab3Data}
                defaultValues={defaultValues}
                saveEntity={saveEntity}
                onTabDataChange={data => handleTabChange(data, 3)}
              />
              <Button color="primary" className="next-button" onClick={() => setActiveTab(activeTab + 1)}>
                Next
              </Button>
            </TabPane>
            <TabPane tabId={4}>
              <FormFieldsTab4
                tabData={tab4Data}
                defaultValues={defaultValues}
                saveEntity={saveEntity}
                onTabDataChange={data => handleTabChange(data, 4)}
              />
            </TabPane>
            {/* ... repeat for other tabs ... */}
          </TabContent>

          {/* ... repeat for other fields ... */}
        </Col>
      </Row>
    </div>
  );
};

const FormFieldsTab1: React.FC<{
  tabData: any;
  onTabDataChange: (data: any) => void;
  defaultValues: () => any; // Add defaultValues prop type
  saveEntity: (values: any) => void; // Add saveEntity prop type
}> = ({ tabData, onTabDataChange, defaultValues, saveEntity }) => {
  const vendorTypeValues = Object.keys(VendorType);
  const vendorCategoryValues = Object.keys(VendorCategory);
  return (
    <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
      <ValidatedField
        label={translate('eCompanyApp.vendor.vendorId')}
        name="vendorId"
        className="validated-field-container"
        data-cy="vendorId"
        type="text"
      />
      <ValidatedField
        label={translate('eCompanyApp.vendor.vendorNameEnglish')}
        name="vendorNameEnglish"
        className="validated-field-container"
        data-cy="vendorNameEnglish"
        type="text"
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
      {/* ... repeat for other fields ... */}
    </ValidatedForm>
  );
};

const FormFieldsTab2: React.FC<{
  tabData: any;
  onTabDataChange: (data: any) => void;
  defaultValues: () => any; // Add defaultValues prop type
  saveEntity: (values: any) => void; // Add saveEntity prop type
}> = ({ tabData, onTabDataChange, defaultValues, saveEntity }) => {
  const officeFunctionalityValues = Object.keys(OfficeFunctionality);
  return (
    <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
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
      {/* ... repeat for other fields ... */}
    </ValidatedForm>
  );
};

const FormFieldsTab3: React.FC<{
  tabData: any;
  onTabDataChange: (data: any) => void;
  defaultValues: () => any; // Add defaultValues prop type
  saveEntity: (values: any) => void; // Add saveEntity prop type
}> = ({ tabData, onTabDataChange, defaultValues, saveEntity }) => {
  const vendorEntity = useAppSelector(state => state.vendor.entity);
  const countryValues = Object.keys(Country);
  const [selectedCoordinates, setSelectedCoordinates] = useState<{
    latitude: number;
    longitude: number;
  } | null>(null);
  return (
    <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
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
      {/* ... repeat for other fields ... */}
    </ValidatedForm>
  );
};

const FormFieldsTab4: React.FC<{
  tabData: any;
  onTabDataChange: (data: any) => void;
  defaultValues: () => any; // Add defaultValues prop type
  saveEntity: (values: any) => void; // Add saveEntity prop type
}> = ({ tabData, onTabDataChange, defaultValues, saveEntity }) => {
  const updating = useAppSelector(state => state.product.updating);
  return (
    <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
      <ValidatedBlobField
        label={translate('eCompanyApp.vendor.cRCertificateUpload')}
        id="vendor-cRCertificateUpload"
        name="cRCertificateUpload"
        className="validated-blob-field-container"
        data-cy="cRCertificateUpload"
        openActionLabel={translate('entity.action.open')}
      />
      <ValidatedBlobField
        label={translate('eCompanyApp.vendor.vATCertificateUpload')}
        id="vendor-vATCertificateUpload"
        name="vATCertificateUpload"
        className="validated-blob-field-container"
        data-cy="vATCertificateUpload"
        openActionLabel={translate('entity.action.open')}
      />
      <ValidatedBlobField
        label={translate('eCompanyApp.vendor.nationalAddressUpload')}
        id="vendor-nationalAddressUpload"
        name="nationalAddressUpload"
        className="validated-blob-field-container"
        data-cy="nationalAddressUpload"
        openActionLabel={translate('entity.action.open')}
      />
      <ValidatedBlobField
        label={translate('eCompanyApp.vendor.companyProfileUpload')}
        id="vendor-companyProfileUpload"
        name="companyProfileUpload"
        className="validated-blob-field-container"
        data-cy="companyProfileUpload"
        openActionLabel={translate('entity.action.open')}
      />
      <ValidatedBlobField
        label={translate('eCompanyApp.vendor.otherUpload')}
        id="vendor-otherUpload"
        name="otherUpload"
        className="validated-blob-field-container"
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
  );
};

export default VendorUpdate;
