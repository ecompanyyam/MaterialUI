package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import com.yam.ecompany.domain.enumeration.Assessment;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.VendorAssessment} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.VendorAssessmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendor-assessments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorAssessmentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Assessment
     */
    public static class AssessmentFilter extends Filter<Assessment> {

        public AssessmentFilter() {}

        public AssessmentFilter(AssessmentFilter filter) {
            super(filter);
        }

        @Override
        public AssessmentFilter copy() {
            return new AssessmentFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter assessmentDate;

    private StringFilter assessedBY;

    private AssessmentFilter jobKnowledge;

    private AssessmentFilter workQuality;

    private AssessmentFilter attendancePunctuality;

    private AssessmentFilter initiative;

    private AssessmentFilter communicationListeningSkills;

    private AssessmentFilter dependability;

    private LongFilter vendorsNameId;

    private Boolean distinct;

    public VendorAssessmentCriteria() {}

    public VendorAssessmentCriteria(VendorAssessmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assessmentDate = other.assessmentDate == null ? null : other.assessmentDate.copy();
        this.assessedBY = other.assessedBY == null ? null : other.assessedBY.copy();
        this.jobKnowledge = other.jobKnowledge == null ? null : other.jobKnowledge.copy();
        this.workQuality = other.workQuality == null ? null : other.workQuality.copy();
        this.attendancePunctuality = other.attendancePunctuality == null ? null : other.attendancePunctuality.copy();
        this.initiative = other.initiative == null ? null : other.initiative.copy();
        this.communicationListeningSkills = other.communicationListeningSkills == null ? null : other.communicationListeningSkills.copy();
        this.dependability = other.dependability == null ? null : other.dependability.copy();
        this.vendorsNameId = other.vendorsNameId == null ? null : other.vendorsNameId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VendorAssessmentCriteria copy() {
        return new VendorAssessmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getAssessmentDate() {
        return assessmentDate;
    }

    public InstantFilter assessmentDate() {
        if (assessmentDate == null) {
            assessmentDate = new InstantFilter();
        }
        return assessmentDate;
    }

    public void setAssessmentDate(InstantFilter assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public StringFilter getAssessedBY() {
        return assessedBY;
    }

    public StringFilter assessedBY() {
        if (assessedBY == null) {
            assessedBY = new StringFilter();
        }
        return assessedBY;
    }

    public void setAssessedBY(StringFilter assessedBY) {
        this.assessedBY = assessedBY;
    }

    public AssessmentFilter getJobKnowledge() {
        return jobKnowledge;
    }

    public AssessmentFilter jobKnowledge() {
        if (jobKnowledge == null) {
            jobKnowledge = new AssessmentFilter();
        }
        return jobKnowledge;
    }

    public void setJobKnowledge(AssessmentFilter jobKnowledge) {
        this.jobKnowledge = jobKnowledge;
    }

    public AssessmentFilter getWorkQuality() {
        return workQuality;
    }

    public AssessmentFilter workQuality() {
        if (workQuality == null) {
            workQuality = new AssessmentFilter();
        }
        return workQuality;
    }

    public void setWorkQuality(AssessmentFilter workQuality) {
        this.workQuality = workQuality;
    }

    public AssessmentFilter getAttendancePunctuality() {
        return attendancePunctuality;
    }

    public AssessmentFilter attendancePunctuality() {
        if (attendancePunctuality == null) {
            attendancePunctuality = new AssessmentFilter();
        }
        return attendancePunctuality;
    }

    public void setAttendancePunctuality(AssessmentFilter attendancePunctuality) {
        this.attendancePunctuality = attendancePunctuality;
    }

    public AssessmentFilter getInitiative() {
        return initiative;
    }

    public AssessmentFilter initiative() {
        if (initiative == null) {
            initiative = new AssessmentFilter();
        }
        return initiative;
    }

    public void setInitiative(AssessmentFilter initiative) {
        this.initiative = initiative;
    }

    public AssessmentFilter getCommunicationListeningSkills() {
        return communicationListeningSkills;
    }

    public AssessmentFilter communicationListeningSkills() {
        if (communicationListeningSkills == null) {
            communicationListeningSkills = new AssessmentFilter();
        }
        return communicationListeningSkills;
    }

    public void setCommunicationListeningSkills(AssessmentFilter communicationListeningSkills) {
        this.communicationListeningSkills = communicationListeningSkills;
    }

    public AssessmentFilter getDependability() {
        return dependability;
    }

    public AssessmentFilter dependability() {
        if (dependability == null) {
            dependability = new AssessmentFilter();
        }
        return dependability;
    }

    public void setDependability(AssessmentFilter dependability) {
        this.dependability = dependability;
    }

    public LongFilter getVendorsNameId() {
        return vendorsNameId;
    }

    public LongFilter vendorsNameId() {
        if (vendorsNameId == null) {
            vendorsNameId = new LongFilter();
        }
        return vendorsNameId;
    }

    public void setVendorsNameId(LongFilter vendorsNameId) {
        this.vendorsNameId = vendorsNameId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendorAssessmentCriteria that = (VendorAssessmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assessmentDate, that.assessmentDate) &&
            Objects.equals(assessedBY, that.assessedBY) &&
            Objects.equals(jobKnowledge, that.jobKnowledge) &&
            Objects.equals(workQuality, that.workQuality) &&
            Objects.equals(attendancePunctuality, that.attendancePunctuality) &&
            Objects.equals(initiative, that.initiative) &&
            Objects.equals(communicationListeningSkills, that.communicationListeningSkills) &&
            Objects.equals(dependability, that.dependability) &&
            Objects.equals(vendorsNameId, that.vendorsNameId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assessmentDate,
            assessedBY,
            jobKnowledge,
            workQuality,
            attendancePunctuality,
            initiative,
            communicationListeningSkills,
            dependability,
            vendorsNameId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorAssessmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assessmentDate != null ? "assessmentDate=" + assessmentDate + ", " : "") +
            (assessedBY != null ? "assessedBY=" + assessedBY + ", " : "") +
            (jobKnowledge != null ? "jobKnowledge=" + jobKnowledge + ", " : "") +
            (workQuality != null ? "workQuality=" + workQuality + ", " : "") +
            (attendancePunctuality != null ? "attendancePunctuality=" + attendancePunctuality + ", " : "") +
            (initiative != null ? "initiative=" + initiative + ", " : "") +
            (communicationListeningSkills != null ? "communicationListeningSkills=" + communicationListeningSkills + ", " : "") +
            (dependability != null ? "dependability=" + dependability + ", " : "") +
            (vendorsNameId != null ? "vendorsNameId=" + vendorsNameId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
