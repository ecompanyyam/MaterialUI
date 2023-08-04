import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faLongArrowUp, faLongArrowDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vendor-assessment.reducer';

export const VendorAssessment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const vendorAssessmentList = useAppSelector(state => state.vendorAssessment.entities);
  const loading = useAppSelector(state => state.vendorAssessment.loading);
  const totalItems = useAppSelector(state => state.vendorAssessment.totalItems);

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
      <h2 id="vendor-assessment-heading" data-cy="VendorAssessmentHeading">
        <Translate contentKey="eCompanyApp.vendorAssessment.home.title">Vendor Assessments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eCompanyApp.vendorAssessment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vendor-assessment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eCompanyApp.vendorAssessment.home.createLabel">Create new Vendor Assessment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vendorAssessmentList && vendorAssessmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('assessmentDate')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.assessmentDate">Assessment Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('assessmentDate')} />
                </th>
                <th className="hand" onClick={sort('assessedBY')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.assessedBY">Assessed BY</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('assessedBY')} />
                </th>
                <th className="hand" onClick={sort('jobKnowledge')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.jobKnowledge">Job Knowledge</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobKnowledge')} />
                </th>
                <th className="hand" onClick={sort('jobKnowledgeComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.jobKnowledgeComment">Job Knowledge Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobKnowledgeComment')} />
                </th>
                <th className="hand" onClick={sort('workQuality')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.workQuality">Work Quality</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('workQuality')} />
                </th>
                <th className="hand" onClick={sort('workQualityComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.workQualityComment">Work Quality Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('workQualityComment')} />
                </th>
                <th className="hand" onClick={sort('attendancePunctuality')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.attendancePunctuality">Attendance Punctuality</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('attendancePunctuality')} />
                </th>
                <th className="hand" onClick={sort('attendancePunctualityComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.attendancePunctualityComment">
                    Attendance Punctuality Comment
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('attendancePunctualityComment')} />
                </th>
                <th className="hand" onClick={sort('initiative')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.initiative">Initiative</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('initiative')} />
                </th>
                <th className="hand" onClick={sort('initiativeComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.initiativeComment">Initiative Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('initiativeComment')} />
                </th>
                <th className="hand" onClick={sort('communicationListeningSkills')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.communicationListeningSkills">
                    Communication Listening Skills
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('communicationListeningSkills')} />
                </th>
                <th className="hand" onClick={sort('communicationListeningSkillsComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.communicationListeningSkillsComment">
                    Communication Listening Skills Comment
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('communicationListeningSkillsComment')} />
                </th>
                <th className="hand" onClick={sort('dependability')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.dependability">Dependability</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dependability')} />
                </th>
                <th className="hand" onClick={sort('dependabilityComment')}>
                  <Translate contentKey="eCompanyApp.vendorAssessment.dependabilityComment">Dependability Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dependabilityComment')} />
                </th>
                <th>
                  <Translate contentKey="eCompanyApp.vendorAssessment.vendorsName">Vendors Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vendorAssessmentList.map((vendorAssessment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vendor-assessment/${vendorAssessment.id}`} color="link" size="sm">
                      {vendorAssessment.id}
                    </Button>
                  </td>
                  <td>
                    {vendorAssessment.assessmentDate ? (
                      <TextFormat type="date" value={vendorAssessment.assessmentDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vendorAssessment.assessedBY}</td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.jobKnowledge}`} />
                  </td>
                  <td>
                    {vendorAssessment.jobKnowledgeComment.substring(0, 20)}
                    {vendorAssessment.jobKnowledgeComment.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.workQuality}`} />
                  </td>
                  <td>
                    {vendorAssessment.workQualityComment.substring(0, 20)}
                    {vendorAssessment.workQualityComment.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.attendancePunctuality}`} />
                  </td>
                  <td>
                    {vendorAssessment.attendancePunctualityComment.substring(0, 20)}
                    {vendorAssessment.attendancePunctualityComment.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.initiative}`} />
                  </td>
                  <td>
                    {vendorAssessment.initiativeComment.substring(0, 20)}
                    {vendorAssessment.initiativeComment.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.communicationListeningSkills}`} />
                  </td>
                  <td>
                    {vendorAssessment.communicationListeningSkillsComment.substring(0, 20)}
                    {vendorAssessment.communicationListeningSkillsComment.length >= 20 && '...'}
                  </td>
                  <td>
                    <Translate contentKey={`eCompanyApp.Assessment.${vendorAssessment.dependability}`} />
                  </td>
                  <td>
                    {vendorAssessment.dependabilityComment.substring(0, 20)}
                    {vendorAssessment.dependabilityComment.length >= 20 && '...'}
                  </td>
                  <td>
                    {vendorAssessment.vendorsName ? (
                      <Link to={`/vendor/${vendorAssessment.vendorsName.id}`}>{vendorAssessment.vendorsName.vendorNameEnglish}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/vendor-assessment/${vendorAssessment.id}`}
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
                        to={`/vendor-assessment/${vendorAssessment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/vendor-assessment/${vendorAssessment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="eCompanyApp.vendorAssessment.home.notFound">No Vendor Assessments found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={vendorAssessmentList && vendorAssessmentList.length > 0 ? '' : 'd-none'}>
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

export default VendorAssessment;
