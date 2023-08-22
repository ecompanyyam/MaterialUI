import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocument } from 'app/shared/model/document.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';
import { getEntity, updateEntity, createEntity, reset } from './document.reducer';

export const DocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documentEntity = useAppSelector(state => state.document.entity);
  const loading = useAppSelector(state => state.document.loading);
  const updating = useAppSelector(state => state.document.updating);
  const updateSuccess = useAppSelector(state => state.document.updateSuccess);
  const documentTypeValues = Object.keys(DocumentType);
  const documentStatusValues = Object.keys(DocumentStatus);

  const handleClose = () => {
    navigate('/document' + location.search);
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
    values.issueDate = convertDateTimeToServer(values.issueDate);
    values.expiryDate = convertDateTimeToServer(values.expiryDate);

    const entity = {
      ...documentEntity,
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
          issueDate: displayDefaultDateTime(),
          expiryDate: displayDefaultDateTime(),
        }
      : {
          documentType: 'QUALITY_CERTIFICATES',
          documentStatus: 'STILL_VALID',
          ...documentEntity,
          issueDate: convertDateTimeFromServer(documentEntity.issueDate),
          expiryDate: convertDateTimeFromServer(documentEntity.expiryDate),
        };

  return (
    <div>
      <Row className="justify-content-center blue-bg mb-2">
        <Col md="6">
          <h2 id="eCompanyApp.document.home.createOrEditLabel" data-cy="DocumentCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.document.home.createOrEditLabel">Create or edit a Document</Translate>
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
                  id="document-id"
                  className="validated-field-container"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.document.documentType')}
                id="document-documentType"
                name="documentType"
                className="validated-field-container"
                data-cy="documentType"
                type="select"
              >
                {documentTypeValues.map(documentType => (
                  <option value={documentType} key={documentType}>
                    {translate('eCompanyApp.DocumentType.' + documentType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.document.organizationName')}
                id="document-organizationName"
                name="organizationName"
                className="validated-field-container"
                data-cy="organizationName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.document.documentName')}
                id="document-documentName"
                name="documentName"
                className="validated-field-container"
                data-cy="documentName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.document.documentNumber')}
                id="document-documentNumber"
                name="documentNumber"
                className="validated-field-container"
                data-cy="documentNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.document.issueDate')}
                id="document-issueDate"
                name="issueDate"
                className="validated-field-container"
                data-cy="issueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCompanyApp.document.expiryDate')}
                id="document-expiryDate"
                name="expiryDate"
                className="validated-field-container"
                data-cy="expiryDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCompanyApp.document.documentStatus')}
                id="document-documentStatus"
                name="documentStatus"
                className="validated-field-container"
                data-cy="documentStatus"
                type="select"
              >
                {documentStatusValues.map(documentStatus => (
                  <option value={documentStatus} key={documentStatus}>
                    {translate('eCompanyApp.DocumentStatus.' + documentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedBlobField
                label={translate('eCompanyApp.document.uploadFile')}
                id="document-uploadFile"
                className="validated-blob-field-container"
                name="uploadFile"
                data-cy="uploadFile"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document" replace color="info">
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

export default DocumentUpdate;
