import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { openFile, byteSize, Translate, translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faLongArrowUp, faLongArrowDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ProductsGrid } from './products-grid';


import { searchEntities, getEntities } from './product.reducer';

export const Product = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);
  const totalItems = useAppSelector(state => state.product.totalItems);
  const [search, setSearch] = useState('');

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const startSearching = e => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      dispatch(
        searchEntities({
          query: search,
          page: paginationState.activePage - 1,
          size: paginationState.itemsPerPage,
          sort: `${paginationState.sort},${paginationState.order}`,
        })
      );
    }
    e.preventDefault();
  };

  const clear = () => {
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  const handleSearch = event => setSearch(event.target.value);

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faLongArrowUp : faLongArrowDown;
    }
  };

  return (
    <div>
      <h2 id="product-heading" data-cy="ProductHeading">
        <Translate contentKey="eCompanyApp.product.home.title">Products</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.product.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.product.home.createLabel">Create new Product</Translate>
          </Link>
        </div>
      </h2>
      <Row>
        <Col sm="12">
          <Form onSubmit={startSearching}>
            <FormGroup>
              <InputGroup>
                <Input
                  type="text"
                  name="search"
                  defaultValue={search}
                  onChange={handleSearch}
                  placeholder={translate('eCompanyApp.product.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </FormGroup>
          </Form>
        </Col>
      </Row>
      {/* <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.product.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('productName')}>
                  <Translate contentKey="eCompanyApp.product.productName">Product Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productName')} />
                </th>
                <th className="hand" onClick={sort('productRemark')}>
                  <Translate contentKey="eCompanyApp.product.productRemark">Product Remark</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productRemark')} />
                </th>
                <th className="hand" onClick={sort('productOrigin')}>
                  <Translate contentKey="eCompanyApp.product.productOrigin">Product Origin</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productOrigin')} />
                </th>
                <th className="hand" onClick={sort('productStockStatus')}>
                  <Translate contentKey="eCompanyApp.product.productStockStatus">Product Stock Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productStockStatus')} />
                </th>
                <th className="hand" onClick={sort('productAvgDeliveryTime')}>
                  <Translate contentKey="eCompanyApp.product.productAvgDeliveryTime">Product Avg Delivery Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productAvgDeliveryTime')} />
                </th>
                <th className="hand" onClick={sort('productManufacturer')}>
                  <Translate contentKey="eCompanyApp.product.productManufacturer">Product Manufacturer</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productManufacturer')} />
                </th>
                <th className="hand" onClick={sort('productImage')}>
                  <Translate contentKey="eCompanyApp.product.productImage">Product Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productImage')} />
                </th>
                <th className="hand" onClick={sort('productAttachments')}>
                  <Translate contentKey="eCompanyApp.product.productAttachments">Product Attachments</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('productAttachments')} />
                </th>
                <th>
                  <Translate contentKey="eCompanyApp.product.vendorsName">Vendors Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.productName}</td>
                  <td>
                    {product.productRemark.substring(0, 20)}
                    {product.productRemark.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Country.${product.productOrigin}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.StockStatus.${product.productStockStatus}`} />
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.TimeTaken.${product.productAvgDeliveryTime}`} />
                  </td>
                  <td>{product.productManufacturer}</td>
                  <td>
                    {product.productImage ? (
                      <div>
                        {product.productImageContentType ? (
                          <a onClick={openFile(product.productImageContentType, product.productImage)}>
                            <img
                              src={`data:${product.productImageContentType};base64,${product.productImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {product.productImageContentType}, {byteSize(product.productImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {product.productAttachments ? (
                      <div>
                        {product.productAttachmentsContentType ? (
                          <a onClick={openFile(product.productAttachmentsContentType, product.productAttachments)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {product.productAttachmentsContentType}, {byteSize(product.productAttachments)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {product.vendorsName ? (
                      <Link to={`/vendor/${product.vendorsName.id}`}>{product.vendorsName.vendorNameEnglish}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group" role="group" aria-label="Basic example">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eCompanyApp.product.home.notFound">No Products found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={productList && productList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )} */}
      <ProductsGrid rows={productList ?? []} />
    </div>
  );
};

export default Product;
