package com.yam.ecompany.domain;

import com.yam.ecompany.domain.enumeration.DocumentStatus;
import com.yam.ecompany.domain.enumeration.DocumentType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "issue_date")
    private Instant issueDate;

    @Column(name = "expiry_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Instant expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_status")
    private DocumentStatus documentStatus;

    @Lob
    @Column(name = "upload_file")
    private byte[] uploadFile;

    @Column(name = "upload_file_content_type")
    private String uploadFileContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public Document documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Document organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public Document documentName(String documentName) {
        this.setDocumentName(documentName);
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public Document documentNumber(String documentNumber) {
        this.setDocumentNumber(documentNumber);
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Instant getIssueDate() {
        return this.issueDate;
    }

    public Document issueDate(Instant issueDate) {
        this.setIssueDate(issueDate);
        return this;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public Document expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DocumentStatus getDocumentStatus() {
        return this.documentStatus;
    }

    public Document documentStatus(DocumentStatus documentStatus) {
        this.setDocumentStatus(documentStatus);
        return this;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public byte[] getUploadFile() {
        return this.uploadFile;
    }

    public Document uploadFile(byte[] uploadFile) {
        this.setUploadFile(uploadFile);
        return this;
    }

    public void setUploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFileContentType() {
        return this.uploadFileContentType;
    }

    public Document uploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
        return this;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", uploadFile='" + getUploadFile() + "'" +
            ", uploadFileContentType='" + getUploadFileContentType() + "'" +
            "}";
    }
}
