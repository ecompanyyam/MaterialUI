import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feed-back-from-employee.reducer';

export const FeedBackFromEmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedBackFromEmployeeEntity = useAppSelector(state => state.feedBackFromEmployee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedBackFromEmployeeDetailsHeading">
          <Translate contentKey="eCompanyApp.feedBackFromEmployee.detail.title">FeedBackFromEmployee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{feedBackFromEmployeeEntity.id}</dd>
          <dt>
            <span id="refContractPONumber">
              <Translate contentKey="eCompanyApp.feedBackFromEmployee.refContractPONumber">Ref Contract PO Number</Translate>
            </span>
          </dt>
          <dd>{feedBackFromEmployeeEntity.refContractPONumber}</dd>
          <dt>
            <span id="feedBackCategory">
              <Translate contentKey="eCompanyApp.feedBackFromEmployee.feedBackCategory">Feed Back Category</Translate>
            </span>
          </dt>
          <dd>{feedBackFromEmployeeEntity.feedBackCategory}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="eCompanyApp.feedBackFromEmployee.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{feedBackFromEmployeeEntity.comment}</dd>
          <dt>
            <Translate contentKey="eCompanyApp.feedBackFromEmployee.vendorsName">Vendors Name</Translate>
          </dt>
          <dd>{feedBackFromEmployeeEntity.vendorsName ? feedBackFromEmployeeEntity.vendorsName.vendorNameEnglish : ''}</dd>
          <dt>
            <Translate contentKey="eCompanyApp.feedBackFromEmployee.productName">Product Name</Translate>
          </dt>
          <dd>{feedBackFromEmployeeEntity.productName ? feedBackFromEmployeeEntity.productName.productName : ''}</dd>
        </dl>
        <Button tag={Link} to="/feed-back-from-employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feed-back-from-employee/${feedBackFromEmployeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedBackFromEmployeeDetail;
