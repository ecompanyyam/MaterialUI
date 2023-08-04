package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.FeedBackCategory;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.FeedBackFromEmployee} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.FeedBackFromEmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feed-back-from-employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedBackFromEmployeeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FeedBackCategory
     */
    public static class FeedBackCategoryFilter extends Filter<FeedBackCategory> {

        public FeedBackCategoryFilter() {}

        public FeedBackCategoryFilter(FeedBackCategoryFilter filter) {
            super(filter);
        }

        @Override
        public FeedBackCategoryFilter copy() {
            return new FeedBackCategoryFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter refContractPONumber;

    private FeedBackCategoryFilter feedBackCategory;

    private LongFilter vendorsNameId;

    private LongFilter productNameId;

    private Boolean distinct;

    public FeedBackFromEmployeeCriteria() {}

    public FeedBackFromEmployeeCriteria(FeedBackFromEmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.refContractPONumber = other.refContractPONumber == null ? null : other.refContractPONumber.copy();
        this.feedBackCategory = other.feedBackCategory == null ? null : other.feedBackCategory.copy();
        this.vendorsNameId = other.vendorsNameId == null ? null : other.vendorsNameId.copy();
        this.productNameId = other.productNameId == null ? null : other.productNameId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FeedBackFromEmployeeCriteria copy() {
        return new FeedBackFromEmployeeCriteria(this);
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

    public StringFilter getRefContractPONumber() {
        return refContractPONumber;
    }

    public StringFilter refContractPONumber() {
        if (refContractPONumber == null) {
            refContractPONumber = new StringFilter();
        }
        return refContractPONumber;
    }

    public void setRefContractPONumber(StringFilter refContractPONumber) {
        this.refContractPONumber = refContractPONumber;
    }

    public FeedBackCategoryFilter getFeedBackCategory() {
        return feedBackCategory;
    }

    public FeedBackCategoryFilter feedBackCategory() {
        if (feedBackCategory == null) {
            feedBackCategory = new FeedBackCategoryFilter();
        }
        return feedBackCategory;
    }

    public void setFeedBackCategory(FeedBackCategoryFilter feedBackCategory) {
        this.feedBackCategory = feedBackCategory;
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

    public LongFilter getProductNameId() {
        return productNameId;
    }

    public LongFilter productNameId() {
        if (productNameId == null) {
            productNameId = new LongFilter();
        }
        return productNameId;
    }

    public void setProductNameId(LongFilter productNameId) {
        this.productNameId = productNameId;
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
        final FeedBackFromEmployeeCriteria that = (FeedBackFromEmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(refContractPONumber, that.refContractPONumber) &&
            Objects.equals(feedBackCategory, that.feedBackCategory) &&
            Objects.equals(vendorsNameId, that.vendorsNameId) &&
            Objects.equals(productNameId, that.productNameId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refContractPONumber, feedBackCategory, vendorsNameId, productNameId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackFromEmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (refContractPONumber != null ? "refContractPONumber=" + refContractPONumber + ", " : "") +
            (feedBackCategory != null ? "feedBackCategory=" + feedBackCategory + ", " : "") +
            (vendorsNameId != null ? "vendorsNameId=" + vendorsNameId + ", " : "") +
            (productNameId != null ? "productNameId=" + productNameId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
