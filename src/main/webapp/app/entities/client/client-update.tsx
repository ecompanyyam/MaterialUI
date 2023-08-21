import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { ApprovalStatus } from 'app/shared/model/enumerations/approval-status.model';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';

export const ClientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);
  const updating = useAppSelector(state => state.client.updating);
  const updateSuccess = useAppSelector(state => state.client.updateSuccess);
  const approvalStatusValues = Object.keys(ApprovalStatus);

  const handleClose = () => {
    navigate('/client' + location.search);
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
    values.dateOfSubmittal = convertDateTimeToServer(values.dateOfSubmittal);
    values.dateOfRegistration = convertDateTimeToServer(values.dateOfRegistration);
    values.dateOfExpiry = convertDateTimeToServer(values.dateOfExpiry);

    const entity = {
      ...clientEntity,
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
          dateOfSubmittal: displayDefaultDateTime(),
          dateOfRegistration: displayDefaultDateTime(),
          dateOfExpiry: displayDefaultDateTime(),
        }
      : {
          approvalStatus: 'SUBMITTAL_UNDER_PREPARATION',
          ...clientEntity,
          taken: convertDateTimeFromServer(clientEntity.taken),
          uploaded: convertDateTimeFromServer(clientEntity.uploaded),
          dateOfSubmittal: convertDateTimeFromServer(clientEntity.dateOfSubmittal),
          dateOfRegistration: convertDateTimeFromServer(clientEntity.dateOfRegistration),
          dateOfExpiry: convertDateTimeFromServer(clientEntity.dateOfExpiry),
        };
  return (
    <div>
      <Row className="justify-content-center blue-bg mb-2">
        <Col md="6">
          <h2 id="eCompanyApp.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
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
                  id="client-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.client.clientName')}
                id="client-clientName"
                name="clientName"
                className="validated-field-container"
                data-cy="clientName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.client.dateOfSubmittal')}
                id="client-dateOfSubmittal"
                name="dateOfSubmittal"
                className="validated-field-container"
                data-cy="dateOfSubmittal"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCompanyApp.client.approvalStatus')}
                id="client-approvalStatus"
                name="approvalStatus"
                className="validated-field-container"
                data-cy="approvalStatus"
                type="select"
              >
                {approvalStatusValues.map(approvalStatus => (
                  <option value={approvalStatus} key={approvalStatus}>
                    {translate('eCompanyApp.ApprovalStatus.' + approvalStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.client.registrationNumber')}
                id="client-registrationNumber"
                name="registrationNumber"
                className="validated-field-container"
                data-cy="registrationNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.client.dateOfRegistration')}
                id="client-dateOfRegistration"
                name="dateOfRegistration"
                className="validated-field-container"
                data-cy="dateOfRegistration"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCompanyApp.client.dateOfExpiry')}
                id="client-dateOfExpiry"
                name="dateOfExpiry"
                className="validated-field-container"
                data-cy="dateOfExpiry"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.client.logo')}
                id="client-logo"
                name="logo"
                data-cy="logo"
                isImage
                accept="image/*"
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.client.approvalDocument')}
                id="client-approvalDocument"
                name="approvalDocument"
                data-cy="approvalDocument"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client" replace color="info">
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

export default ClientUpdate;
