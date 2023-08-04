package com.yam.ecompany.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesRepresentative.
 */
@Entity
@Table(name = "sales_representative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesRepresentative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "job_title")
    private String jobTitle;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "office_location")
    private String officeLocation;

    @Lob
    @Column(name = "address_line_1")
    private String addressLine1;

    @Lob
    @Column(name = "other_details")
    private String otherDetails;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor vendorsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesRepresentative id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public SalesRepresentative fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public SalesRepresentative jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return this.email;
    }

    public SalesRepresentative email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public SalesRepresentative phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficeLocation() {
        return this.officeLocation;
    }

    public SalesRepresentative officeLocation(String officeLocation) {
        this.setOfficeLocation(officeLocation);
        return this;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public SalesRepresentative addressLine1(String addressLine1) {
        this.setAddressLine1(addressLine1);
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getOtherDetails() {
        return this.otherDetails;
    }

    public SalesRepresentative otherDetails(String otherDetails) {
        this.setOtherDetails(otherDetails);
        return this;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Vendor getVendorsName() {
        return this.vendorsName;
    }

    public void setVendorsName(Vendor vendor) {
        this.vendorsName = vendor;
    }

    public SalesRepresentative vendorsName(Vendor vendor) {
        this.setVendorsName(vendor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesRepresentative)) {
            return false;
        }
        return id != null && id.equals(((SalesRepresentative) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesRepresentative{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", officeLocation='" + getOfficeLocation() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", otherDetails='" + getOtherDetails() + "'" +
            "}";
    }
}
