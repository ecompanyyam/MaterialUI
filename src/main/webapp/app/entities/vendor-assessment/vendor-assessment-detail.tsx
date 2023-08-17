import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vendor-assessment.reducer';

export const VendorAssessmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vendorAssessmentEntity = useAppSelector(state => state.vendorAssessment.entity);
  return (
    <Row className="justify-content-center align-items-center">
      <Col md="8">
        <h2 data-cy="vendorAssessmentDetailsHeading">
          <Translate contentKey="eCompanyApp.vendorAssessment.detail.title">VendorAssessment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.id}</dd>
          <dt>
            <span id="assessmentDate">
              <Translate contentKey="eCompanyApp.vendorAssessment.assessmentDate">Assessment Date</Translate>
            </span>
          </dt>
          <dd>
            {vendorAssessmentEntity.assessmentDate ? (
              <TextFormat value={vendorAssessmentEntity.assessmentDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="assessedBY">
              <Translate contentKey="eCompanyApp.vendorAssessment.assessedBY">Assessed BY</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.assessedBY}</dd>
          <dt>
            <span id="jobKnowledge">
              <Translate contentKey="eCompanyApp.vendorAssessment.jobKnowledge">Job Knowledge</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.jobKnowledge}</dd>
          <dt>
            <span id="jobKnowledgeComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.jobKnowledgeComment">Job Knowledge Comment</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.jobKnowledgeComment}</dd>
          <dt>
            <span id="workQuality">
              <Translate contentKey="eCompanyApp.vendorAssessment.workQuality">Work Quality</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.workQuality}</dd>
          <dt>
            <span id="workQualityComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.workQualityComment">Work Quality Comment</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.workQualityComment}</dd>
          <dt>
            <span id="attendancePunctuality">
              <Translate contentKey="eCompanyApp.vendorAssessment.attendancePunctuality">Attendance Punctuality</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.attendancePunctuality}</dd>
          <dt>
            <span id="attendancePunctualityComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.attendancePunctualityComment">Attendance Punctuality Comment</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.attendancePunctualityComment}</dd>
          <dt>
            <span id="initiative">
              <Translate contentKey="eCompanyApp.vendorAssessment.initiative">Initiative</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.initiative}</dd>
          <dt>
            <span id="initiativeComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.initiativeComment">Initiative Comment</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.initiativeComment}</dd>
          <dt>
            <span id="communicationListeningSkills">
              <Translate contentKey="eCompanyApp.vendorAssessment.communicationListeningSkills">Communication Listening Skills</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.communicationListeningSkills}</dd>
          <dt>
            <span id="communicationListeningSkillsComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.communicationListeningSkillsComment">
                Communication Listening Skills Comment
              </Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.communicationListeningSkillsComment}</dd>
          <dt>
            <span id="dependability">
              <Translate contentKey="eCompanyApp.vendorAssessment.dependability">Dependability</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.dependability}</dd>
          <dt>
            <span id="dependabilityComment">
              <Translate contentKey="eCompanyApp.vendorAssessment.dependabilityComment">Dependability Comment</Translate>
            </span>
          </dt>
          <dd>{vendorAssessmentEntity.dependabilityComment}</dd>
          <dt>
            <Translate contentKey="eCompanyApp.vendorAssessment.vendorsName">Vendors Name</Translate>
          </dt>
          <dd>{vendorAssessmentEntity.vendorsName ? vendorAssessmentEntity.vendorsName.vendorNameEnglish : ''}</dd>
        </dl>
        <Button tag={Link} to="/vendor-assessment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vendor-assessment/${vendorAssessmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VendorAssessmentDetail;
