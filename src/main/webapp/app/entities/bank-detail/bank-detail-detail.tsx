import React, { useEffect, useRef } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-detail.reducer';
import { faPrint } from '@fortawesome/free-solid-svg-icons';

export const BankDetailDetail = () => {
  const dispatch = useAppDispatch();
  const pdfContentRef = useRef(null);

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankDetailEntity = useAppSelector(state => state.bankDetail.entity);
  return (
    <div>
      <Row className="justify-content-center blue-bg mb-2">
        <Col md="6">
          <h2 data-cy="bankDetailDetailsHeading">
            <Translate contentKey="eCompanyApp.bankDetail.detail.title">BankDetail</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center align-items-center">
        <Col md="6">
          <div ref={pdfContentRef}>
            <dl className="jh-entity-details details-list">
              <dt>
                <span id="id">
                  <Translate contentKey="global.field.id">ID</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.id}</dd>
              <dt>
                <span id="bankAccount">
                  <Translate contentKey="eCompanyApp.bankDetail.bankAccount">Bank Account</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.bankAccount ? 'true' : 'false'}</dd>
              <dt>
                <span id="bankName">
                  <Translate contentKey="eCompanyApp.bankDetail.bankName">Bank Name</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.bankName}</dd>
              <dt>
                <span id="branchSwiftCode">
                  <Translate contentKey="eCompanyApp.bankDetail.branchSwiftCode">Branch Swift Code</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.branchSwiftCode}</dd>
              <dt>
                <span id="ibanNo">
                  <Translate contentKey="eCompanyApp.bankDetail.ibanNo">Iban No</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.ibanNo}</dd>
              <dt>
                <span id="accountNumber">
                  <Translate contentKey="eCompanyApp.bankDetail.accountNumber">Account Number</Translate>
                </span>
              </dt>
              <dd>{bankDetailEntity.accountNumber}</dd>
              <dt>
                <Translate contentKey="eCompanyApp.bankDetail.vendorsName">Vendors Name</Translate>
              </dt>
              <dd>{bankDetailEntity.vendorsName ? bankDetailEntity.vendorsName.vendorNameEnglish : ''}</dd>
            </dl>
            <Button tag={Link} to="/bank-detail" replace color="info" data-cy="entityDetailsBackButton">
              <FontAwesomeIcon icon="arrow-left" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`/bank-detail/${bankDetailEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.edit">Edit</Translate>
              </span>
            </Button>
            &nbsp;
            <Button color="info" onClick={() => window.print()}>
              <FontAwesomeIcon icon={faPrint} />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.PDF">Print PDF</Translate>
              </span>{' '}
            </Button>
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default BankDetailDetail;
