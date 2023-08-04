package com.yam.ecompany.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.SalesRepresentative} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.SalesRepresentativeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-representatives?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesRepresentativeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter jobTitle;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter officeLocation;

    private LongFilter vendorsNameId;

    private Boolean distinct;

    public SalesRepresentativeCriteria() {}

    public SalesRepresentativeCriteria(SalesRepresentativeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.jobTitle = other.jobTitle == null ? null : other.jobTitle.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.officeLocation = other.officeLocation == null ? null : other.officeLocation.copy();
        this.vendorsNameId = other.vendorsNameId == null ? null : other.vendorsNameId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SalesRepresentativeCriteria copy() {
        return new SalesRepresentativeCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getJobTitle() {
        return jobTitle;
    }

    public StringFilter jobTitle() {
        if (jobTitle == null) {
            jobTitle = new StringFilter();
        }
        return jobTitle;
    }

    public void setJobTitle(StringFilter jobTitle) {
        this.jobTitle = jobTitle;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getOfficeLocation() {
        return officeLocation;
    }

    public StringFilter officeLocation() {
        if (officeLocation == null) {
            officeLocation = new StringFilter();
        }
        return officeLocation;
    }

    public void setOfficeLocation(StringFilter officeLocation) {
        this.officeLocation = officeLocation;
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
        final SalesRepresentativeCriteria that = (SalesRepresentativeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(jobTitle, that.jobTitle) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(officeLocation, that.officeLocation) &&
            Objects.equals(vendorsNameId, that.vendorsNameId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, jobTitle, email, phone, officeLocation, vendorsNameId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesRepresentativeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (jobTitle != null ? "jobTitle=" + jobTitle + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (officeLocation != null ? "officeLocation=" + officeLocation + ", " : "") +
            (vendorsNameId != null ? "vendorsNameId=" + vendorsNameId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
