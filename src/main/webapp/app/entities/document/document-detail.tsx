import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document.reducer';

export const DocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">
          <Translate contentKey="eCompanyApp.document.detail.title">Document</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="eCompanyApp.document.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentType}</dd>
          <dt>
            <span id="organizationName">
              <Translate contentKey="eCompanyApp.document.organizationName">Organization Name</Translate>
            </span>
          </dt>
          <dd>{documentEntity.organizationName}</dd>
          <dt>
            <span id="documentName">
              <Translate contentKey="eCompanyApp.document.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentName}</dd>
          <dt>
            <span id="documentNumber">
              <Translate contentKey="eCompanyApp.document.documentNumber">Document Number</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentNumber}</dd>
          <dt>
            <span id="issueDate">
              <Translate contentKey="eCompanyApp.document.issueDate">Issue Date</Translate>
            </span>
          </dt>
          <dd>{documentEntity.issueDate ? <TextFormat value={documentEntity.issueDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="eCompanyApp.document.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.expiryDate ? <TextFormat value={documentEntity.expiryDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="documentStatus">
              <Translate contentKey="eCompanyApp.document.documentStatus">Document Status</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentStatus}</dd>
          <dt>
            <span id="uploadFile">
              <Translate contentKey="eCompanyApp.document.uploadFile">Upload File</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.uploadFile ? (
              <div>
                {documentEntity.uploadFileContentType ? (
                  <a onClick={openFile(documentEntity.uploadFileContentType, documentEntity.uploadFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {documentEntity.uploadFileContentType}, {byteSize(documentEntity.uploadFile)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
