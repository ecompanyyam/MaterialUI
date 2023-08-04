package com.yam.ecompany.domain;

import com.yam.ecompany.domain.enumeration.Assessment;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VendorAssessment.
 */
@Entity
@Table(name = "vendor_assessment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "assessment_date")
    private Instant assessmentDate;

    @Column(name = "assessed_by")
    private String assessedBY;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_knowledge")
    private Assessment jobKnowledge;

    @Lob
    @Column(name = "job_knowledge_comment")
    private String jobKnowledgeComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_quality")
    private Assessment workQuality;

    @Lob
    @Column(name = "work_quality_comment")
    private String workQualityComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_punctuality")
    private Assessment attendancePunctuality;

    @Lob
    @Column(name = "attendance_punctuality_comment")
    private String attendancePunctualityComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "initiative")
    private Assessment initiative;

    @Lob
    @Column(name = "initiative_comment")
    private String initiativeComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_listening_skills")
    private Assessment communicationListeningSkills;

    @Lob
    @Column(name = "communication_listening_skills_comment")
    private String communicationListeningSkillsComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependability")
    private Assessment dependability;

    @Lob
    @Column(name = "dependability_comment")
    private String dependabilityComment;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor vendorsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VendorAssessment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAssessmentDate() {
        return this.assessmentDate;
    }

    public VendorAssessment assessmentDate(Instant assessmentDate) {
        this.setAssessmentDate(assessmentDate);
        return this;
    }

    public void setAssessmentDate(Instant assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessedBY() {
        return this.assessedBY;
    }

    public VendorAssessment assessedBY(String assessedBY) {
        this.setAssessedBY(assessedBY);
        return this;
    }

    public void setAssessedBY(String assessedBY) {
        this.assessedBY = assessedBY;
    }

    public Assessment getJobKnowledge() {
        return this.jobKnowledge;
    }

    public VendorAssessment jobKnowledge(Assessment jobKnowledge) {
        this.setJobKnowledge(jobKnowledge);
        return this;
    }

    public void setJobKnowledge(Assessment jobKnowledge) {
        this.jobKnowledge = jobKnowledge;
    }

    public String getJobKnowledgeComment() {
        return this.jobKnowledgeComment;
    }

    public VendorAssessment jobKnowledgeComment(String jobKnowledgeComment) {
        this.setJobKnowledgeComment(jobKnowledgeComment);
        return this;
    }

    public void setJobKnowledgeComment(String jobKnowledgeComment) {
        this.jobKnowledgeComment = jobKnowledgeComment;
    }

    public Assessment getWorkQuality() {
        return this.workQuality;
    }

    public VendorAssessment workQuality(Assessment workQuality) {
        this.setWorkQuality(workQuality);
        return this;
    }

    public void setWorkQuality(Assessment workQuality) {
        this.workQuality = workQuality;
    }

    public String getWorkQualityComment() {
        return this.workQualityComment;
    }

    public VendorAssessment workQualityComment(String workQualityComment) {
        this.setWorkQualityComment(workQualityComment);
        return this;
    }

    public void setWorkQualityComment(String workQualityComment) {
        this.workQualityComment = workQualityComment;
    }

    public Assessment getAttendancePunctuality() {
        return this.attendancePunctuality;
    }

    public VendorAssessment attendancePunctuality(Assessment attendancePunctuality) {
        this.setAttendancePunctuality(attendancePunctuality);
        return this;
    }

    public void setAttendancePunctuality(Assessment attendancePunctuality) {
        this.attendancePunctuality = attendancePunctuality;
    }

    public String getAttendancePunctualityComment() {
        return this.attendancePunctualityComment;
    }

    public VendorAssessment attendancePunctualityComment(String attendancePunctualityComment) {
        this.setAttendancePunctualityComment(attendancePunctualityComment);
        return this;
    }

    public void setAttendancePunctualityComment(String attendancePunctualityComment) {
        this.attendancePunctualityComment = attendancePunctualityComment;
    }

    public Assessment getInitiative() {
        return this.initiative;
    }

    public VendorAssessment initiative(Assessment initiative) {
        this.setInitiative(initiative);
        return this;
    }

    public void setInitiative(Assessment initiative) {
        this.initiative = initiative;
    }

    public String getInitiativeComment() {
        return this.initiativeComment;
    }

    public VendorAssessment initiativeComment(String initiativeComment) {
        this.setInitiativeComment(initiativeComment);
        return this;
    }

    public void setInitiativeComment(String initiativeComment) {
        this.initiativeComment = initiativeComment;
    }

    public Assessment getCommunicationListeningSkills() {
        return this.communicationListeningSkills;
    }

    public VendorAssessment communicationListeningSkills(Assessment communicationListeningSkills) {
        this.setCommunicationListeningSkills(communicationListeningSkills);
        return this;
    }

    public void setCommunicationListeningSkills(Assessment communicationListeningSkills) {
        this.communicationListeningSkills = communicationListeningSkills;
    }

    public String getCommunicationListeningSkillsComment() {
        return this.communicationListeningSkillsComment;
    }

    public VendorAssessment communicationListeningSkillsComment(String communicationListeningSkillsComment) {
        this.setCommunicationListeningSkillsComment(communicationListeningSkillsComment);
        return this;
    }

    public void setCommunicationListeningSkillsComment(String communicationListeningSkillsComment) {
        this.communicationListeningSkillsComment = communicationListeningSkillsComment;
    }

    public Assessment getDependability() {
        return this.dependability;
    }

    public VendorAssessment dependability(Assessment dependability) {
        this.setDependability(dependability);
        return this;
    }

    public void setDependability(Assessment dependability) {
        this.dependability = dependability;
    }

    public String getDependabilityComment() {
        return this.dependabilityComment;
    }

    public VendorAssessment dependabilityComment(String dependabilityComment) {
        this.setDependabilityComment(dependabilityComment);
        return this;
    }

    public void setDependabilityComment(String dependabilityComment) {
        this.dependabilityComment = dependabilityComment;
    }

    public Vendor getVendorsName() {
        return this.vendorsName;
    }

    public void setVendorsName(Vendor vendor) {
        this.vendorsName = vendor;
    }

    public VendorAssessment vendorsName(Vendor vendor) {
        this.setVendorsName(vendor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorAssessment)) {
            return false;
        }
        return id != null && id.equals(((VendorAssessment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorAssessment{" +
            "id=" + getId() +
            ", assessmentDate='" + getAssessmentDate() + "'" +
            ", assessedBY='" + getAssessedBY() + "'" +
            ", jobKnowledge='" + getJobKnowledge() + "'" +
            ", jobKnowledgeComment='" + getJobKnowledgeComment() + "'" +
            ", workQuality='" + getWorkQuality() + "'" +
            ", workQualityComment='" + getWorkQualityComment() + "'" +
            ", attendancePunctuality='" + getAttendancePunctuality() + "'" +
            ", attendancePunctualityComment='" + getAttendancePunctualityComment() + "'" +
            ", initiative='" + getInitiative() + "'" +
            ", initiativeComment='" + getInitiativeComment() + "'" +
            ", communicationListeningSkills='" + getCommunicationListeningSkills() + "'" +
            ", communicationListeningSkillsComment='" + getCommunicationListeningSkillsComment() + "'" +
            ", dependability='" + getDependability() + "'" +
            ", dependabilityComment='" + getDependabilityComment() + "'" +
            "}";
    }
}
