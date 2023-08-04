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
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IFeedBackFromEmployee } from 'app/shared/model/feed-back-from-employee.model';
import { FeedBackCategory } from 'app/shared/model/enumerations/feed-back-category.model';
import { getEntity, updateEntity, createEntity, reset } from './feed-back-from-employee.reducer';

export const FeedBackFromEmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendors = useAppSelector(state => state.vendor.entities);
  const products = useAppSelector(state => state.product.entities);
  const feedBackFromEmployeeEntity = useAppSelector(state => state.feedBackFromEmployee.entity);
  const loading = useAppSelector(state => state.feedBackFromEmployee.loading);
  const updating = useAppSelector(state => state.feedBackFromEmployee.updating);
  const updateSuccess = useAppSelector(state => state.feedBackFromEmployee.updateSuccess);
  const feedBackCategoryValues = Object.keys(FeedBackCategory);

  const handleClose = () => {
    navigate('/feed-back-from-employee' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVendors({}));
    dispatch(getProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...feedBackFromEmployeeEntity,
      ...values,
      vendorsName: vendors.find(it => it.id.toString() === values.vendorsName.toString()),
      productName: products.find(it => it.id.toString() === values.productName.toString()),
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
          feedBackCategory: 'ADVICE',
          ...feedBackFromEmployeeEntity,
          vendorsName: feedBackFromEmployeeEntity?.vendorsName?.id,
          productName: feedBackFromEmployeeEntity?.productName?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCompanyApp.feedBackFromEmployee.home.createOrEditLabel" data-cy="FeedBackFromEmployeeCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.feedBackFromEmployee.home.createOrEditLabel">
              Create or edit a FeedBackFromEmployee
            </Translate>
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
                  id="feed-back-from-employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.feedBackFromEmployee.refContractPONumber')}
                id="feed-back-from-employee-refContractPONumber"
                name="refContractPONumber"
                data-cy="refContractPONumber"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.feedBackFromEmployee.feedBackCategory')}
                id="feed-back-from-employee-feedBackCategory"
                name="feedBackCategory"
                data-cy="feedBackCategory"
                type="select"
              >
                {feedBackCategoryValues.map(feedBackCategory => (
                  <option value={feedBackCategory} key={feedBackCategory}>
                    {translate('eCompanyApp.FeedBackCategory.' + feedBackCategory)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.feedBackFromEmployee.comment')}
                id="feed-back-from-employee-comment"
                name="comment"
                data-cy="comment"
                type="textarea"
              />
              <ValidatedField
                id="feed-back-from-employee-vendorsName"
                name="vendorsName"
                data-cy="vendorsName"
                label={translate('eCompanyApp.feedBackFromEmployee.vendorsName')}
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
              <ValidatedField
                id="feed-back-from-employee-productName"
                name="productName"
                data-cy="productName"
                label={translate('eCompanyApp.feedBackFromEmployee.productName')}
                type="select"
                required
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.productName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/feed-back-from-employee" replace color="info">
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

export default FeedBackFromEmployeeUpdate;
