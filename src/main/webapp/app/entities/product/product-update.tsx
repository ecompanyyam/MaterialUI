import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { StockStatus } from 'app/shared/model/enumerations/stock-status.model';
import { TimeTaken } from 'app/shared/model/enumerations/time-taken.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendors = useAppSelector(state => state.vendor.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const countryValues = Object.keys(Country);
  const stockStatusValues = Object.keys(StockStatus);
  const timeTakenValues = Object.keys(TimeTaken);

  const handleClose = () => {
    navigate('/product' + location.search);
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
    values.taken = convertDateTimeToServer(values.taken);
    values.uploaded = convertDateTimeToServer(values.uploaded);

    const entity = {
      ...productEntity,
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
      ? {
          taken: displayDefaultDateTime(),
          uploaded: displayDefaultDateTime(),
        }
      : {
          productOrigin: 'INDIA',
          productStockStatus: 'CUSTOM_ORDER',
          productAvgDeliveryTime: 'WEEKLY',
          ...productEntity,
          taken: convertDateTimeFromServer(productEntity.taken),
          uploaded: convertDateTimeFromServer(productEntity.uploaded),
          vendorsName: productEntity?.vendorsName?.id,
        };
  const metadata = (
    <div>
      <ValidatedField label={translate('eCompanyApp.product.height')} id="product-height" name="height" data-cy="height" type="text" />
      <ValidatedField label={translate('eCompanyApp.product.width')} id="product-width" name="width" data-cy="width" type="text" />
      <ValidatedField
        label={translate('eCompanyApp.product.taken')}
        id="product-taken"
        name="taken"
        data-cy="taken"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
      <ValidatedField
        label={translate('eCompanyApp.product.uploaded')}
        id="product-uploaded"
        name="uploaded"
        data-cy="uploaded"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
    </div>
  );
  const metadataRows = isNew ? '' : metadata;
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCompanyApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="12">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm className="row" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="product-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eCompanyApp.product.productName')}
                id="product-productName"
                name="productName"
                className="col-lg-3"
                data-cy="productName"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.product.productRemark')}
                id="product-productRemark"
                name="productRemark"
                className="col-lg-3"
                data-cy="productRemark"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.product.productOrigin')}
                id="product-productOrigin"
                name="productOrigin"
                className="col-lg-3"
                data-cy="productOrigin"
                type="select"
              >
                {countryValues.map(country => (
                  <option value={country} key={country}>
                    {translate('eCompanyApp.Country.' + country)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.product.productStockStatus')}
                id="product-productStockStatus"
                className="col-lg-3"
                name="productStockStatus"
                data-cy="productStockStatus"
                type="select"
              >
                {stockStatusValues.map(stockStatus => (
                  <option value={stockStatus} key={stockStatus}>
                    {translate('eCompanyApp.StockStatus.' + stockStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.product.productAvgDeliveryTime')}
                id="product-productAvgDeliveryTime"
                className="col-lg-3"
                name="productAvgDeliveryTime"
                data-cy="productAvgDeliveryTime"
                type="select"
              >
                {timeTakenValues.map(timeTaken => (
                  <option value={timeTaken} key={timeTaken}>
                    {translate('eCompanyApp.TimeTaken.' + timeTaken)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.product.productManufacturer')}
                id="product-productManufacturer"
                name="productManufacturer"
                className="col-lg-3"
                data-cy="productManufacturer"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.product.productImage')}
                id="product-productImage"
                name="productImage"
                className="col-lg-3"
                data-cy="productImage"
                isImage
                accept="image/*"
              />
              <ValidatedBlobField
                label={translate('eCompanyApp.product.productAttachments')}
                id="product-productAttachments"
                className="col-lg-3"
                name="productAttachments"
                data-cy="productAttachments"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="product-vendorsName"
                name="vendorsName"
                className="col-lg-3"
                data-cy="vendorsName"
                label={translate('eCompanyApp.product.vendorsName')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info mt-4 back_btn">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
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

export default ProductUpdate;
