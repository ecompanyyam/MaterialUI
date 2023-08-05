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
import { IVendorAssessment } from 'app/shared/model/vendor-assessment.model';
import { Assessment } from 'app/shared/model/enumerations/assessment.model';
import { getEntity, updateEntity, createEntity, reset } from './vendor-assessment.reducer';

export const VendorAssessmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendors = useAppSelector(state => state.vendor.entities);
  const vendorAssessmentEntity = useAppSelector(state => state.vendorAssessment.entity);
  const loading = useAppSelector(state => state.vendorAssessment.loading);
  const updating = useAppSelector(state => state.vendorAssessment.updating);
  const updateSuccess = useAppSelector(state => state.vendorAssessment.updateSuccess);
  const assessmentValues = Object.keys(Assessment);

  const handleClose = () => {
    navigate('/vendor-assessment' + location.search);
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
    values.assessmentDate = convertDateTimeToServer(values.assessmentDate);

    const entity = {
      ...vendorAssessmentEntity,
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
          assessmentDate: displayDefaultDateTime(),
        }
      : {
          jobKnowledge: 'POOR',
          workQuality: 'POOR',
          attendancePunctuality: 'POOR',
          initiative: 'POOR',
          communicationListeningSkills: 'POOR',
          dependability: 'POOR',
          ...vendorAssessmentEntity,
          assessmentDate: convertDateTimeFromServer(vendorAssessmentEntity.assessmentDate),
          vendorsName: vendorAssessmentEntity?.vendorsName?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCompanyApp.vendorAssessment.home.createOrEditLabel" data-cy="VendorAssessmentCreateUpdateHeading">
            <Translate contentKey="eCompanyApp.vendorAssessment.home.createOrEditLabel">Create or edit a VendorAssessment</Translate>
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
                  id="vendor-assessment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="vendor-assessment-vendorsName"
                name="vendorsName"
                className="col-lg-4"
                data-cy="vendorsName"
                label={translate('eCompanyApp.vendorAssessment.vendorsName')}
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
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.assessmentDate')}
                id="vendor-assessment-assessmentDate"
                name="assessmentDate"
                className="col-lg-4 "
                data-cy="assessmentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.assessedBY')}
                id="vendor-assessment-assessedBY"
                name="assessedBY"
                className="col-lg-4"
                data-cy="assessedBY"
                type="text"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.jobKnowledge')}
                id="vendor-assessment-jobKnowledge"
                name="jobKnowledge"
                className="col-lg-3"
                data-cy="jobKnowledge"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.jobKnowledgeComment')}
                id="vendor-assessment-jobKnowledgeComment"
                name="jobKnowledgeComment"
                className="col-lg-3"
                data-cy="jobKnowledgeComment"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.workQuality')}
                id="vendor-assessment-workQuality"
                name="workQuality"
                className="col-lg-3"
                data-cy="workQuality"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.workQualityComment')}
                id="vendor-assessment-workQualityComment"
                name="workQualityComment"
                className="col-lg-3"
                data-cy="workQualityComment"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.attendancePunctuality')}
                id="vendor-assessment-attendancePunctuality"
                name="attendancePunctuality"
                className="col-lg-3"
                data-cy="attendancePunctuality"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.attendancePunctualityComment')}
                id="vendor-assessment-attendancePunctualityComment"
                name="attendancePunctualityComment"
                className="col-lg-3"
                data-cy="attendancePunctualityComment"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.initiative')}
                id="vendor-assessment-initiative"
                name="initiative"
                className="col-lg-3"
                data-cy="initiative"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.initiativeComment')}
                id="vendor-assessment-initiativeComment"
                name="initiativeComment"
                className="col-lg-3"
                data-cy="initiativeComment"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.communicationListeningSkills')}
                id="vendor-assessment-communicationListeningSkills"
                name="communicationListeningSkills"
                className="col-lg-3"
                data-cy="communicationListeningSkills"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.communicationListeningSkillsComment')}
                id="vendor-assessment-communicationListeningSkillsComment"
                name="communicationListeningSkillsComment"
                className="col-lg-3"
                data-cy="communicationListeningSkillsComment"
                type="textarea"
              />
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.dependability')}
                id="vendor-assessment-dependability"
                name="dependability"
                className="col-lg-3"
                data-cy="dependability"
                type="select"
              >
                {assessmentValues.map(assessment => (
                  <option value={assessment} key={assessment}>
                    {translate('eCompanyApp.Assessment.' + assessment)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('eCompanyApp.vendorAssessment.dependabilityComment')}
                id="vendor-assessment-dependabilityComment"
                name="dependabilityComment"
                data-cy="dependabilityComment"
                className="col-lg-3"
                type="textarea"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/vendor-assessment"
                replace
                color="info mt-4 back_btn"
              >
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

export default VendorAssessmentUpdate;
