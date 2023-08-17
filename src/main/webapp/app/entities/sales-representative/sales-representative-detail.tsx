import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sales-representative.reducer';

export const SalesRepresentativeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const salesRepresentativeEntity = useAppSelector(state => state.salesRepresentative.entity);
  return (
    <Row className="justify-content-center align-items-center">
      <Col md="8">
        <h2 data-cy="salesRepresentativeDetailsHeading">
          <Translate contentKey="eCompanyApp.salesRepresentative.detail.title">SalesRepresentative</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.id}</dd>
          <dt>
            <span id="fullName">
              <Translate contentKey="eCompanyApp.salesRepresentative.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.fullName}</dd>
          <dt>
            <span id="jobTitle">
              <Translate contentKey="eCompanyApp.salesRepresentative.jobTitle">Job Title</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.jobTitle}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="eCompanyApp.salesRepresentative.email">Email</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="eCompanyApp.salesRepresentative.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.phone}</dd>
          <dt>
            <span id="officeLocation">
              <Translate contentKey="eCompanyApp.salesRepresentative.officeLocation">Office Location</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.officeLocation}</dd>
          <dt>
            <span id="addressLine1">
              <Translate contentKey="eCompanyApp.salesRepresentative.addressLine1">Address Line 1</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.addressLine1}</dd>
          <dt>
            <span id="otherDetails">
              <Translate contentKey="eCompanyApp.salesRepresentative.otherDetails">Other Details</Translate>
            </span>
          </dt>
          <dd>{salesRepresentativeEntity.otherDetails}</dd>
          <dt>
            <Translate contentKey="eCompanyApp.salesRepresentative.vendorsName">Vendors Name</Translate>
          </dt>
          <dd>{salesRepresentativeEntity.vendorsName ? salesRepresentativeEntity.vendorsName.vendorNameEnglish : ''}</dd>
        </dl>
        <Button tag={Link} to="/sales-representative" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sales-representative/${salesRepresentativeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SalesRepresentativeDetail;
