package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.DocumentStatus;
import com.yam.ecompany.domain.enumeration.DocumentType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.Document} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.DocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DocumentType
     */
    public static class DocumentTypeFilter extends Filter<DocumentType> {

        public DocumentTypeFilter() {}

        public DocumentTypeFilter(DocumentTypeFilter filter) {
            super(filter);
        }

        @Override
        public DocumentTypeFilter copy() {
            return new DocumentTypeFilter(this);
        }
    }

    /**
     * Class for filtering DocumentStatus
     */
    public static class DocumentStatusFilter extends Filter<DocumentStatus> {

        public DocumentStatusFilter() {}

        public DocumentStatusFilter(DocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public DocumentStatusFilter copy() {
            return new DocumentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DocumentTypeFilter documentType;

    private StringFilter organizationName;

    private StringFilter documentName;

    private StringFilter documentNumber;

    private InstantFilter issueDate;

    private InstantFilter expiryDate;

    private DocumentStatusFilter documentStatus;

    private Boolean distinct;

    public DocumentCriteria() {}

    public DocumentCriteria(DocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentType = other.documentType == null ? null : other.documentType.copy();
        this.organizationName = other.organizationName == null ? null : other.organizationName.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.documentNumber = other.documentNumber == null ? null : other.documentNumber.copy();
        this.issueDate = other.issueDate == null ? null : other.issueDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentCriteria copy() {
        return new DocumentCriteria(this);
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

    public DocumentTypeFilter getDocumentType() {
        return documentType;
    }

    public DocumentTypeFilter documentType() {
        if (documentType == null) {
            documentType = new DocumentTypeFilter();
        }
        return documentType;
    }

    public void setDocumentType(DocumentTypeFilter documentType) {
        this.documentType = documentType;
    }

    public StringFilter getOrganizationName() {
        return organizationName;
    }

    public StringFilter organizationName() {
        if (organizationName == null) {
            organizationName = new StringFilter();
        }
        return organizationName;
    }

    public void setOrganizationName(StringFilter organizationName) {
        this.organizationName = organizationName;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public StringFilter documentName() {
        if (documentName == null) {
            documentName = new StringFilter();
        }
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
    }

    public StringFilter getDocumentNumber() {
        return documentNumber;
    }

    public StringFilter documentNumber() {
        if (documentNumber == null) {
            documentNumber = new StringFilter();
        }
        return documentNumber;
    }

    public void setDocumentNumber(StringFilter documentNumber) {
        this.documentNumber = documentNumber;
    }

    public InstantFilter getIssueDate() {
        return issueDate;
    }

    public InstantFilter issueDate() {
        if (issueDate == null) {
            issueDate = new InstantFilter();
        }
        return issueDate;
    }

    public void setIssueDate(InstantFilter issueDate) {
        this.issueDate = issueDate;
    }

    public InstantFilter getExpiryDate() {
        return expiryDate;
    }

    public InstantFilter expiryDate() {
        if (expiryDate == null) {
            expiryDate = new InstantFilter();
        }
        return expiryDate;
    }

    public void setExpiryDate(InstantFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DocumentStatusFilter getDocumentStatus() {
        return documentStatus;
    }

    public DocumentStatusFilter documentStatus() {
        if (documentStatus == null) {
            documentStatus = new DocumentStatusFilter();
        }
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatusFilter documentStatus) {
        this.documentStatus = documentStatus;
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
        final DocumentCriteria that = (DocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documentType, that.documentType) &&
            Objects.equals(organizationName, that.organizationName) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(documentNumber, that.documentNumber) &&
            Objects.equals(issueDate, that.issueDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            documentType,
            organizationName,
            documentName,
            documentNumber,
            issueDate,
            expiryDate,
            documentStatus,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (documentType != null ? "documentType=" + documentType + ", " : "") +
            (organizationName != null ? "organizationName=" + organizationName + ", " : "") +
            (documentName != null ? "documentName=" + documentName + ", " : "") +
            (documentNumber != null ? "documentNumber=" + documentNumber + ", " : "") +
            (issueDate != null ? "issueDate=" + issueDate + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
