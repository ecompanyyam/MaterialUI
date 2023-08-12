package com.yam.ecompany.domain;

import com.yam.ecompany.domain.enumeration.ApprovalStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "taken")
    private Instant taken;

    @Column(name = "uploaded")
    private Instant uploaded;

    @Column(name = "date_of_submittal")
    private Instant dateOfSubmittal;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "date_of_registration")
    private Instant dateOfRegistration;

    @Column(name = "date_of_expiry")
    private Instant dateOfExpiry;

    @Lob
    @Column(name = "approval_document")
    private byte[] approvalDocument;

    @Column(name = "approval_document_content_type")
    private String approvalDocumentContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Client clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Client logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Client logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Client height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Client width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Instant getTaken() {
        return this.taken;
    }

    public Client taken(Instant taken) {
        this.setTaken(taken);
        return this;
    }

    public void setTaken(Instant taken) {
        this.taken = taken;
    }

    public Instant getUploaded() {
        return this.uploaded;
    }

    public Client uploaded(Instant uploaded) {
        this.setUploaded(uploaded);
        return this;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }

    public Instant getDateOfSubmittal() {
        return this.dateOfSubmittal;
    }

    public Client dateOfSubmittal(Instant dateOfSubmittal) {
        this.setDateOfSubmittal(dateOfSubmittal);
        return this;
    }

    public void setDateOfSubmittal(Instant dateOfSubmittal) {
        this.dateOfSubmittal = dateOfSubmittal;
    }

    public ApprovalStatus getApprovalStatus() {
        return this.approvalStatus;
    }

    public Client approvalStatus(ApprovalStatus approvalStatus) {
        this.setApprovalStatus(approvalStatus);
        return this;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Client registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Instant getDateOfRegistration() {
        return this.dateOfRegistration;
    }

    public Client dateOfRegistration(Instant dateOfRegistration) {
        this.setDateOfRegistration(dateOfRegistration);
        return this;
    }

    public void setDateOfRegistration(Instant dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Instant getDateOfExpiry() {
        return this.dateOfExpiry;
    }

    public Client dateOfExpiry(Instant dateOfExpiry) {
        this.setDateOfExpiry(dateOfExpiry);
        return this;
    }

    public void setDateOfExpiry(Instant dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public byte[] getApprovalDocument() {
        return this.approvalDocument;
    }

    public Client approvalDocument(byte[] approvalDocument) {
        this.setApprovalDocument(approvalDocument);
        return this;
    }

    public void setApprovalDocument(byte[] approvalDocument) {
        this.approvalDocument = approvalDocument;
    }

    public String getApprovalDocumentContentType() {
        return this.approvalDocumentContentType;
    }

    public Client approvalDocumentContentType(String approvalDocumentContentType) {
        this.approvalDocumentContentType = approvalDocumentContentType;
        return this;
    }

    public void setApprovalDocumentContentType(String approvalDocumentContentType) {
        this.approvalDocumentContentType = approvalDocumentContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", taken='" + getTaken() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            ", dateOfSubmittal='" + getDateOfSubmittal() + "'" +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", dateOfRegistration='" + getDateOfRegistration() + "'" +
            ", dateOfExpiry='" + getDateOfExpiry() + "'" +
            ", approvalDocument='" + getApprovalDocument() + "'" +
            ", approvalDocumentContentType='" + getApprovalDocumentContentType() + "'" +
            "}";
    }
}
