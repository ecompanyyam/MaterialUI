import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, FormGroup } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { IBankDetail } from 'app/shared/model/bank-detail.model';
import { getEntity, updateEntity, createEntity, reset } from './bank-detail.reducer';

export const BankDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendors = useAppSelector(state => state.vendor.entities);
  const bankDetailEntity = useAppSelector(state => state.bankDetail.entity);
  const loading = useAppSelector(state => state.bankDetail.loading);
  const updating = useAppSelector(state => state.bankDetail.updating);
  const updateSuccess = useAppSelector(state => state.bankDetail.updateSuccess);

  const handleClose = () => {
    navigate('/bank-detail' + location.search);
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
      ...bankDetailEntity,
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
          ...bankDetailEntity,
          vendorsName: bankDetailEntity?.vendorsName?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="3">
          <h2 id="eCompanyApp.bankDetail.home.createOrEditLabel" data-cy="BankDetailCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.bankDetail.home.createOrEditLabel">Create or edit a BankDetail</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="3">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm className="row" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bank-detail-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="bank-detail-vendorsName"
                name="vendorsName"
                data-cy="vendorsName"
                label={translate('eCompanyApp.bankDetail.vendorsName')}
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
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.bankDetail.bankAccount')}
                id="bank-detail-bankAccount"
                name="bankAccount"
                data-cy="bankAccount"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('eCompanyApp.bankDetail.bankName')}
                id="bank-detail-bankName"
                name="bankName"
                data-cy="bankName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.bankDetail.branchSwiftCode')}
                id="bank-detail-branchSwiftCode"
                name="branchSwiftCode"
                data-cy="branchSwiftCode"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.bankDetail.ibanNo')}
                id="bank-detail-ibanNo"
                name="ibanNo"
                data-cy="ibanNo"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.bankDetail.accountNumber')}
                id="bank-detail-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-detail" replace color="info mt-4 back_btn">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
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

export default BankDetailUpdate;
