package com.yam.ecompany.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yam.ecompany.domain.enumeration.FeedBackCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeedBackFromEmployee.
 */
@Entity
@Table(name = "feed_back_from_employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedBackFromEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ref_contract_po_number")
    private String refContractPONumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "feed_back_category")
    private FeedBackCategory feedBackCategory;

    @Lob
    @Column(name = "comment")
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor vendorsName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "vendorsName" }, allowSetters = true)
    private Product productName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedBackFromEmployee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefContractPONumber() {
        return this.refContractPONumber;
    }

    public FeedBackFromEmployee refContractPONumber(String refContractPONumber) {
        this.setRefContractPONumber(refContractPONumber);
        return this;
    }

    public void setRefContractPONumber(String refContractPONumber) {
        this.refContractPONumber = refContractPONumber;
    }

    public FeedBackCategory getFeedBackCategory() {
        return this.feedBackCategory;
    }

    public FeedBackFromEmployee feedBackCategory(FeedBackCategory feedBackCategory) {
        this.setFeedBackCategory(feedBackCategory);
        return this;
    }

    public void setFeedBackCategory(FeedBackCategory feedBackCategory) {
        this.feedBackCategory = feedBackCategory;
    }

    public String getComment() {
        return this.comment;
    }

    public FeedBackFromEmployee comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Vendor getVendorsName() {
        return this.vendorsName;
    }

    public void setVendorsName(Vendor vendor) {
        this.vendorsName = vendor;
    }

    public FeedBackFromEmployee vendorsName(Vendor vendor) {
        this.setVendorsName(vendor);
        return this;
    }

    public Product getProductName() {
        return this.productName;
    }

    public void setProductName(Product product) {
        this.productName = product;
    }

    public FeedBackFromEmployee productName(Product product) {
        this.setProductName(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedBackFromEmployee)) {
            return false;
        }
        return id != null && id.equals(((FeedBackFromEmployee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackFromEmployee{" +
            "id=" + getId() +
            ", refContractPONumber='" + getRefContractPONumber() + "'" +
            ", feedBackCategory='" + getFeedBackCategory() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
