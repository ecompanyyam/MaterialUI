import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { openFile, byteSize, Translate, translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faLongArrowUp, faLongArrowDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { searchEntities, getEntities } from './sales-representative.reducer';

export const SalesRepresentative = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const [search, setSearch] = useState('');

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const salesRepresentativeList = useAppSelector(state => state.salesRepresentative.entities);
  const loading = useAppSelector(state => state.salesRepresentative.loading);
  const totalItems = useAppSelector(state => state.salesRepresentative.totalItems);

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
      <h2 id="sales-representative-heading" data-cy="SalesRepresentativeHeading">
        <Translate contentKey="eCompanyApp.salesRepresentative.home.title">Sales Representatives</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.salesRepresentative.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/sales-representative/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.salesRepresentative.home.createLabel">Create new Sales Representative</Translate>
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
                  placeholder={translate('eCompanyApp.salesRepresentative.home.search')}
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
      <div className="table-responsive">
        {salesRepresentativeList && salesRepresentativeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('fullName')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.fullName">Full Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fullName')} />
                </th>
                <th className="hand" onClick={sort('jobTitle')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.jobTitle">Job Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobTitle')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.phone">Phone</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                </th>
                <th className="hand" onClick={sort('officeLocation')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.officeLocation">Office Location</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('officeLocation')} />
                </th>
                <th className="hand" onClick={sort('addressLine1')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.addressLine1">Address Line 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('addressLine1')} />
                </th>
                <th className="hand" onClick={sort('otherDetails')}>
                  <Translate contentKey="eCompanyApp.salesRepresentative.otherDetails">Other Details</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('otherDetails')} />
                </th>
                <th>
                  <Translate contentKey="eCompanyApp.salesRepresentative.vendorsName">Vendors Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {salesRepresentativeList.map((salesRepresentative, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sales-representative/${salesRepresentative.id}`} color="link" size="sm">
                      {salesRepresentative.id}
                    </Button>
                  </td>
                  <td>{salesRepresentative.fullName}</td>
                  <td>{salesRepresentative.jobTitle}</td>
                  <td>{salesRepresentative.email}</td>
                  <td>{salesRepresentative.phone}</td>
                  <td>{salesRepresentative.officeLocation}</td>
                  <td>
                    {salesRepresentative.addressLine1.substring(0, 20)}
                    {salesRepresentative.addressLine1.length >= 20 && '...'}
                  </td>
                  <td>
                    {salesRepresentative.otherDetails.substring(0, 20)}
                    {salesRepresentative.otherDetails.length >= 20 && '...'}
                  </td>
                  <td>
                    {salesRepresentative.vendorsName ? (
                      <Link to={`/vendor/${salesRepresentative.vendorsName.id}`}>{salesRepresentative.vendorsName.vendorNameEnglish}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/sales-representative/${salesRepresentative.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sales-representative/${salesRepresentative.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/sales-representative/${salesRepresentative.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="eCompanyApp.salesRepresentative.home.notFound">No Sales Representatives found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={salesRepresentativeList && salesRepresentativeList.length > 0 ? '' : 'd-none'}>
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
      )}
    </div>
  );
};

export default SalesRepresentative;
