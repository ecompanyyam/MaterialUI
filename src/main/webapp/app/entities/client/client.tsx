import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './client.reducer';

export const Client = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const clientList = useAppSelector(state => state.client.entities);
  const loading = useAppSelector(state => state.client.loading);
  const totalItems = useAppSelector(state => state.client.totalItems);

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
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="client-heading" data-cy="ClientHeading">
        <Translate contentKey="eCompanyApp.client.home.title">Clients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.client.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/client/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.client.home.createLabel">Create new Client</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clientList && clientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.client.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('clientName')}>
                  <Translate contentKey="eCompanyApp.client.clientName">Client Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('clientName')} />
                </th>
                <th className="hand" onClick={sort('logo')}>
                  <Translate contentKey="eCompanyApp.client.logo">Logo</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('logo')} />
                </th>
                <th className="hand" onClick={sort('height')}>
                  <Translate contentKey="eCompanyApp.client.height">Height</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('height')} />
                </th>
                <th className="hand" onClick={sort('width')}>
                  <Translate contentKey="eCompanyApp.client.width">Width</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('width')} />
                </th>
                <th className="hand" onClick={sort('taken')}>
                  <Translate contentKey="eCompanyApp.client.taken">Taken</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('taken')} />
                </th>
                <th className="hand" onClick={sort('uploaded')}>
                  <Translate contentKey="eCompanyApp.client.uploaded">Uploaded</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uploaded')} />
                </th>
                <th className="hand" onClick={sort('dateOfSubmittal')}>
                  <Translate contentKey="eCompanyApp.client.dateOfSubmittal">Date Of Submittal</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateOfSubmittal')} />
                </th>
                <th className="hand" onClick={sort('approvalStatus')}>
                  <Translate contentKey="eCompanyApp.client.approvalStatus">Approval Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approvalStatus')} />
                </th>
                <th className="hand" onClick={sort('registrationNumber')}>
                  <Translate contentKey="eCompanyApp.client.registrationNumber">Registration Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('registrationNumber')} />
                </th>
                <th className="hand" onClick={sort('dateOfRegistration')}>
                  <Translate contentKey="eCompanyApp.client.dateOfRegistration">Date Of Registration</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateOfRegistration')} />
                </th>
                <th className="hand" onClick={sort('dateOfExpiry')}>
                  <Translate contentKey="eCompanyApp.client.dateOfExpiry">Date Of Expiry</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateOfExpiry')} />
                </th>
                <th className="hand" onClick={sort('approvalDocument')}>
                  <Translate contentKey="eCompanyApp.client.approvalDocument">Approval Document</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approvalDocument')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clientList.map((client, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/client/${client.id}`} color="link" size="sm">
                      {client.id}
                    </Button>
                  </td>
                  <td>{client.clientName}</td>
                  <td>
                    {client.logo ? (
                      <div>
                        {client.logoContentType ? (
                          <a onClick={openFile(client.logoContentType, client.logo)}>
                            <img src={`data:${client.logoContentType};base64,${client.logo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {client.logoContentType}, {byteSize(client.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{client.height}</td>
                  <td>{client.width}</td>
                  <td>{client.taken ? <TextFormat type="date" value={client.taken} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{client.uploaded ? <TextFormat type="date" value={client.uploaded} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {client.dateOfSubmittal ? <TextFormat type="date" value={client.dateOfSubmittal} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.ApprovalStatus.${client.approvalStatus}`} />
                  </td>
                  <td>{client.registrationNumber}</td>
                  <td>
                    {client.dateOfRegistration ? (
                      <TextFormat type="date" value={client.dateOfRegistration} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{client.dateOfExpiry ? <TextFormat type="date" value={client.dateOfExpiry} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {client.approvalDocument ? (
                      <div>
                        {client.approvalDocumentContentType ? (
                          <a onClick={openFile(client.approvalDocumentContentType, client.approvalDocument)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {client.approvalDocumentContentType}, {byteSize(client.approvalDocument)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/client/${client.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/client/${client.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/client/${client.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="eCompanyApp.client.home.notFound">No Clients found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={clientList && clientList.length > 0 ? '' : 'd-none'}>
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

export default Client;
