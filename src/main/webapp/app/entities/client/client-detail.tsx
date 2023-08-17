import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row className="justify-content-center">
      <Col md="3">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="eCompanyApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="clientName">
              <Translate contentKey="eCompanyApp.client.clientName">Client Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientName}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="eCompanyApp.client.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.logo ? (
              <div>
                {clientEntity.logoContentType ? (
                  <a onClick={openFile(clientEntity.logoContentType, clientEntity.logo)}>
                    <img src={`data:${clientEntity.logoContentType};base64,${clientEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {clientEntity.logoContentType}, {byteSize(clientEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="height">
              <Translate contentKey="eCompanyApp.client.height">Height</Translate>
            </span>
          </dt>
          {/* <dd>{clientEntity.height}</dd> */}
          {/* <dt> */}
          {/*   <span id="width"> */}
          {/*     <Translate contentKey="eCompanyApp.client.width">Width</Translate> */}
          {/*   </span> */}
          {/* </dt> */}
          {/* <dd>{clientEntity.width}</dd> */}
          {/* <dt> */}
          {/*   <span id="taken"> */}
          {/*     <Translate contentKey="eCompanyApp.client.taken">Taken</Translate> */}
          {/*   </span> */}
          {/* </dt> */}
          {/* <dd>{clientEntity.taken ? <TextFormat value={clientEntity.taken} type="date" format={APP_DATE_FORMAT} /> : null}</dd> */}
          {/* <dt> */}
          {/*   <span id="uploaded"> */}
          {/*     <Translate contentKey="eCompanyApp.client.uploaded">Uploaded</Translate> */}
          {/*   </span> */}
          {/* </dt> */}
          {/* <dd>{clientEntity.uploaded ? <TextFormat value={clientEntity.uploaded} type="date" format={APP_DATE_FORMAT} /> : null}</dd> */}
          <dt>
            <span id="dateOfSubmittal">
              <Translate contentKey="eCompanyApp.client.dateOfSubmittal">Date Of Submittal</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateOfSubmittal ? <TextFormat value={clientEntity.dateOfSubmittal} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvalStatus">
              <Translate contentKey="eCompanyApp.client.approvalStatus">Approval Status</Translate>
            </span>
          </dt>
          <dd>{clientEntity.approvalStatus}</dd>
          <dt>
            <span id="registrationNumber">
              <Translate contentKey="eCompanyApp.client.registrationNumber">Registration Number</Translate>
            </span>
          </dt>
          <dd>{clientEntity.registrationNumber}</dd>
          <dt>
            <span id="dateOfRegistration">
              <Translate contentKey="eCompanyApp.client.dateOfRegistration">Date Of Registration</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateOfRegistration ? (
              <TextFormat value={clientEntity.dateOfRegistration} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateOfExpiry">
              <Translate contentKey="eCompanyApp.client.dateOfExpiry">Date Of Expiry</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateOfExpiry ? <TextFormat value={clientEntity.dateOfExpiry} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvalDocument">
              <Translate contentKey="eCompanyApp.client.approvalDocument">Approval Document</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.approvalDocument ? (
              <div>
                {clientEntity.approvalDocumentContentType ? (
                  <a onClick={openFile(clientEntity.approvalDocumentContentType, clientEntity.approvalDocument)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {clientEntity.approvalDocumentContentType}, {byteSize(clientEntity.approvalDocument)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
