import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faLongArrowUp, faLongArrowDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './feed-back-from-employee.reducer';

export const FeedBackFromEmployee = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const feedBackFromEmployeeList = useAppSelector(state => state.feedBackFromEmployee.entities);
  const loading = useAppSelector(state => state.feedBackFromEmployee.loading);
  const totalItems = useAppSelector(state => state.feedBackFromEmployee.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

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
      <h2 id="feed-back-from-employee-heading" data-cy="FeedBackFromEmployeeHeading">
        <Translate contentKey="eCompanyApp.feedBackFromEmployee.home.title">Feed Back From Employees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.feedBackFromEmployee.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/feed-back-from-employee/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.feedBackFromEmployee.home.createLabel">Create new Feed Back From Employee</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {feedBackFromEmployeeList && feedBackFromEmployeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('refContractPONumber')}>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.refContractPONumber">Ref Contract PO Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('refContractPONumber')} />
                </th>
                <th className="hand" onClick={sort('feedBackCategory')}>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.feedBackCategory">Feed Back Category</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('feedBackCategory')} />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.comment">Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('comment')} />
                </th>
                <th>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.vendorsName">Vendors Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="eCompanyApp.feedBackFromEmployee.productName">Product Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {feedBackFromEmployeeList.map((feedBackFromEmployee, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/feed-back-from-employee/${feedBackFromEmployee.id}`} color="link" size="sm">
                      {feedBackFromEmployee.id}
                    </Button>
                  </td>
                  <td>{feedBackFromEmployee.refContractPONumber}</td>
                  <td>
                    <Translate contentKey={`eCompanyApp.FeedBackCategory.${feedBackFromEmployee.feedBackCategory}`} />
                  </td>
                  <td>
                    {feedBackFromEmployee.comment.substring(0, 20)}
                    {feedBackFromEmployee.comment.length >= 20 && '...'}
                  </td>
                  <td>
                    {feedBackFromEmployee.vendorsName ? (
                      <Link to={`/vendor/${feedBackFromEmployee.vendorsName.id}`}>
                        {feedBackFromEmployee.vendorsName.vendorNameEnglish}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {feedBackFromEmployee.productName ? (
                      <Link to={`/product/${feedBackFromEmployee.productName.id}`}>{feedBackFromEmployee.productName.productName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/feed-back-from-employee/${feedBackFromEmployee.id}`}
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
                        to={`/feed-back-from-employee/${feedBackFromEmployee.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/feed-back-from-employee/${feedBackFromEmployee.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="eCompanyApp.feedBackFromEmployee.home.notFound">No Feed Back From Employees found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={feedBackFromEmployeeList && feedBackFromEmployeeList.length > 0 ? '' : 'd-none'}>
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

export default FeedBackFromEmployee;
