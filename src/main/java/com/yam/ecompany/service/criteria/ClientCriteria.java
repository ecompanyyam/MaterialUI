package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.ApprovalStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.Client} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ApprovalStatus
     */
    public static class ApprovalStatusFilter extends Filter<ApprovalStatus> {

        public ApprovalStatusFilter() {}

        public ApprovalStatusFilter(ApprovalStatusFilter filter) {
            super(filter);
        }

        @Override
        public ApprovalStatusFilter copy() {
            return new ApprovalStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clientName;

    private IntegerFilter height;

    private IntegerFilter width;

    private InstantFilter taken;

    private InstantFilter uploaded;

    private InstantFilter dateOfSubmittal;

    private ApprovalStatusFilter approvalStatus;

    private StringFilter registrationNumber;

    private InstantFilter dateOfRegistration;

    private InstantFilter dateOfExpiry;

    private Boolean distinct;

    public ClientCriteria() {}

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.clientName = other.clientName == null ? null : other.clientName.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.taken = other.taken == null ? null : other.taken.copy();
        this.uploaded = other.uploaded == null ? null : other.uploaded.copy();
        this.dateOfSubmittal = other.dateOfSubmittal == null ? null : other.dateOfSubmittal.copy();
        this.approvalStatus = other.approvalStatus == null ? null : other.approvalStatus.copy();
        this.registrationNumber = other.registrationNumber == null ? null : other.registrationNumber.copy();
        this.dateOfRegistration = other.dateOfRegistration == null ? null : other.dateOfRegistration.copy();
        this.dateOfExpiry = other.dateOfExpiry == null ? null : other.dateOfExpiry.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
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

    public StringFilter getClientName() {
        return clientName;
    }

    public StringFilter clientName() {
        if (clientName == null) {
            clientName = new StringFilter();
        }
        return clientName;
    }

    public void setClientName(StringFilter clientName) {
        this.clientName = clientName;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public IntegerFilter height() {
        if (height == null) {
            height = new IntegerFilter();
        }
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public IntegerFilter getWidth() {
        return width;
    }

    public IntegerFilter width() {
        if (width == null) {
            width = new IntegerFilter();
        }
        return width;
    }

    public void setWidth(IntegerFilter width) {
        this.width = width;
    }

    public InstantFilter getTaken() {
        return taken;
    }

    public InstantFilter taken() {
        if (taken == null) {
            taken = new InstantFilter();
        }
        return taken;
    }

    public void setTaken(InstantFilter taken) {
        this.taken = taken;
    }

    public InstantFilter getUploaded() {
        return uploaded;
    }

    public InstantFilter uploaded() {
        if (uploaded == null) {
            uploaded = new InstantFilter();
        }
        return uploaded;
    }

    public void setUploaded(InstantFilter uploaded) {
        this.uploaded = uploaded;
    }

    public InstantFilter getDateOfSubmittal() {
        return dateOfSubmittal;
    }

    public InstantFilter dateOfSubmittal() {
        if (dateOfSubmittal == null) {
            dateOfSubmittal = new InstantFilter();
        }
        return dateOfSubmittal;
    }

    public void setDateOfSubmittal(InstantFilter dateOfSubmittal) {
        this.dateOfSubmittal = dateOfSubmittal;
    }

    public ApprovalStatusFilter getApprovalStatus() {
        return approvalStatus;
    }

    public ApprovalStatusFilter approvalStatus() {
        if (approvalStatus == null) {
            approvalStatus = new ApprovalStatusFilter();
        }
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatusFilter approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public StringFilter getRegistrationNumber() {
        return registrationNumber;
    }

    public StringFilter registrationNumber() {
        if (registrationNumber == null) {
            registrationNumber = new StringFilter();
        }
        return registrationNumber;
    }

    public void setRegistrationNumber(StringFilter registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public InstantFilter getDateOfRegistration() {
        return dateOfRegistration;
    }

    public InstantFilter dateOfRegistration() {
        if (dateOfRegistration == null) {
            dateOfRegistration = new InstantFilter();
        }
        return dateOfRegistration;
    }

    public void setDateOfRegistration(InstantFilter dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public InstantFilter getDateOfExpiry() {
        return dateOfExpiry;
    }

    public InstantFilter dateOfExpiry() {
        if (dateOfExpiry == null) {
            dateOfExpiry = new InstantFilter();
        }
        return dateOfExpiry;
    }

    public void setDateOfExpiry(InstantFilter dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
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
        final ClientCriteria that = (ClientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(clientName, that.clientName) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(taken, that.taken) &&
            Objects.equals(uploaded, that.uploaded) &&
            Objects.equals(dateOfSubmittal, that.dateOfSubmittal) &&
            Objects.equals(approvalStatus, that.approvalStatus) &&
            Objects.equals(registrationNumber, that.registrationNumber) &&
            Objects.equals(dateOfRegistration, that.dateOfRegistration) &&
            Objects.equals(dateOfExpiry, that.dateOfExpiry) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            clientName,
            height,
            width,
            taken,
            uploaded,
            dateOfSubmittal,
            approvalStatus,
            registrationNumber,
            dateOfRegistration,
            dateOfExpiry,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (clientName != null ? "clientName=" + clientName + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (taken != null ? "taken=" + taken + ", " : "") +
            (uploaded != null ? "uploaded=" + uploaded + ", " : "") +
            (dateOfSubmittal != null ? "dateOfSubmittal=" + dateOfSubmittal + ", " : "") +
            (approvalStatus != null ? "approvalStatus=" + approvalStatus + ", " : "") +
            (registrationNumber != null ? "registrationNumber=" + registrationNumber + ", " : "") +
            (dateOfRegistration != null ? "dateOfRegistration=" + dateOfRegistration + ", " : "") +
            (dateOfExpiry != null ? "dateOfExpiry=" + dateOfExpiry + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
