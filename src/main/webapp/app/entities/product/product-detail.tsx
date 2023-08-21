import React, { useEffect, useRef } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';
import { faPrint } from '@fortawesome/free-solid-svg-icons';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();
  const pdfContentRef = useRef(null);
  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <div>
      <Row className="justify-content-center blue-bg mb-2">
        <Col md="6">
          <h2 data-cy="productDetailsHeading">
            <Translate contentKey="eCompanyApp.product.detail.title">Product</Translate>
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
              <dd>{productEntity.id}</dd>
              <dt>
                <Translate contentKey="eCompanyApp.product.vendorsName">Vendors Name</Translate>
              </dt>
              <dd>{productEntity.vendorsName ? productEntity.vendorsName.vendorNameEnglish : ''}</dd>
              <dt>
                <span id="productName">
                  <Translate contentKey="eCompanyApp.product.productName">Product Name</Translate>
                </span>
              </dt>
              <dd>{productEntity.productName}</dd>
              <dt>
                <span id="productRemark">
                  <Translate contentKey="eCompanyApp.product.productRemark">Product Remark</Translate>
                </span>
              </dt>
              <dd>{productEntity.productRemark}</dd>
              <dt>
                <span id="productOrigin">
                  <Translate contentKey="eCompanyApp.product.productOrigin">Product Origin</Translate>
                </span>
              </dt>
              <dd>{productEntity.productOrigin}</dd>
              <dt>
                <span id="productStockStatus">
                  <Translate contentKey="eCompanyApp.product.productStockStatus">Product Stock Status</Translate>
                </span>
              </dt>
              <dd>{productEntity.productStockStatus}</dd>
              <dt>
                <span id="productAvgDeliveryTime">
                  <Translate contentKey="eCompanyApp.product.productAvgDeliveryTime">Product Avg Delivery Time</Translate>
                </span>
              </dt>
              <dd>{productEntity.productAvgDeliveryTime}</dd>

              <dt>
                <span id="productManufacturer">
                  <Translate contentKey="eCompanyApp.product.productManufacturer">Product Manufacturer</Translate>
                </span>
              </dt>
              <dd>{productEntity.productManufacturer}</dd>
              <dt>
                <span id="productImage">
                  <Translate contentKey="eCompanyApp.product.productImage">Product Image</Translate>
                </span>
              </dt>
              <dd>
                {productEntity.productImage ? (
                  <div>
                    {productEntity.productImageContentType ? (
                      <a onClick={openFile(productEntity.productImageContentType, productEntity.productImage)}>
                        <img
                          src={`data:${productEntity.productImageContentType};base64,${productEntity.productImage}`}
                          style={{ maxHeight: '30px' }}
                        />
                      </a>
                    ) : null}
                    <span>{productEntity.productImageContentType}</span>
                  </div>
                ) : null}
              </dd>
              <dt>
                <span id="productAttachments">
                  <Translate contentKey="eCompanyApp.product.productAttachments">Product Attachments</Translate>
                </span>
              </dt>
              <dd>
                {productEntity.productAttachments ? (
                  <div>
                    {productEntity.productAttachmentsContentType ? (
                      <a onClick={openFile(productEntity.productAttachmentsContentType, productEntity.productAttachments)}>
                        <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                      </a>
                    ) : null}
                    <span>{productEntity.productAttachmentsContentType}</span>
                  </div>
                ) : null}
              </dd>
            </dl>
            <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
              <FontAwesomeIcon icon="arrow-left" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
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

export default ProductDetail;
