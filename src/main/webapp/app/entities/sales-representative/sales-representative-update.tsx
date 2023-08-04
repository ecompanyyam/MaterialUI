import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { ISalesRepresentative } from 'app/shared/model/sales-representative.model';
import { getEntity, updateEntity, createEntity, reset } from './sales-representative.reducer';

export const SalesRepresentativeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendors = useAppSelector(state => state.vendor.entities);
  const salesRepresentativeEntity = useAppSelector(state => state.salesRepresentative.entity);
  const loading = useAppSelector(state => state.salesRepresentative.loading);
  const updating = useAppSelector(state => state.salesRepresentative.updating);
  const updateSuccess = useAppSelector(state => state.salesRepresentative.updateSuccess);

  const handleClose = () => {
    navigate('/sales-representative' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVendors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...salesRepresentativeEntity,
      ...values,
      vendorsName: vendors.find(it => it.id.toString() === values.vendorsName.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...salesRepresentativeEntity,
          vendorsName: salesRepresentativeEntity?.vendorsName?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCompanyApp.salesRepresentative.home.createOrEditLabel" data-cy="SalesRepresentativeCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.salesRepresentative.home.createOrEditLabel">Create or edit a SalesRepresentative</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="sales-representative-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.fullName')}
                id="sales-representative-fullName"
                name="fullName"
                data-cy="fullName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.jobTitle')}
                id="sales-representative-jobTitle"
                name="jobTitle"
                data-cy="jobTitle"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.email')}
                id="sales-representative-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.phone')}
                id="sales-representative-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.officeLocation')}
                id="sales-representative-officeLocation"
                name="officeLocation"
                data-cy="officeLocation"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.addressLine1')}
                id="sales-representative-addressLine1"
                name="addressLine1"
                data-cy="addressLine1"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.salesRepresentative.otherDetails')}
                id="sales-representative-otherDetails"
                name="otherDetails"
                data-cy="otherDetails"
                type="textarea"
              />
              <ValidatedField
                id="sales-representative-vendorsName"
                name="vendorsName"
                data-cy="vendorsName"
                label={translate('eCompanyApp.salesRepresentative.vendorsName')}
                type="select"
                required
              >
                <option value="" key="0" />
                {vendors
                  ? vendors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.vendorNameEnglish}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sales-representative" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
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

export default SalesRepresentativeUpdate;
