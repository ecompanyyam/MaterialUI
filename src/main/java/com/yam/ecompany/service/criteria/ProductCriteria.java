package com.yam.ecompany.service.criteria;

import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.StockStatus;
import com.yam.ecompany.domain.enumeration.TimeTaken;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.Product} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Country
     */
    public static class CountryFilter extends Filter<Country> {

        public CountryFilter() {}

        public CountryFilter(CountryFilter filter) {
            super(filter);
        }

        @Override
        public CountryFilter copy() {
            return new CountryFilter(this);
        }
    }

    /**
     * Class for filtering StockStatus
     */
    public static class StockStatusFilter extends Filter<StockStatus> {

        public StockStatusFilter() {}

        public StockStatusFilter(StockStatusFilter filter) {
            super(filter);
        }

        @Override
        public StockStatusFilter copy() {
            return new StockStatusFilter(this);
        }
    }

    /**
     * Class for filtering TimeTaken
     */
    public static class TimeTakenFilter extends Filter<TimeTaken> {

        public TimeTakenFilter() {}

        public TimeTakenFilter(TimeTakenFilter filter) {
            super(filter);
        }

        @Override
        public TimeTakenFilter copy() {
            return new TimeTakenFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private CountryFilter productOrigin;

    private StockStatusFilter productStockStatus;

    private TimeTakenFilter productAvgDeliveryTime;

    private StringFilter productManufacturer;

    private IntegerFilter height;

    private IntegerFilter width;

    private InstantFilter taken;

    private InstantFilter uploaded;

    private LongFilter vendorsNameId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.productOrigin = other.productOrigin == null ? null : other.productOrigin.copy();
        this.productStockStatus = other.productStockStatus == null ? null : other.productStockStatus.copy();
        this.productAvgDeliveryTime = other.productAvgDeliveryTime == null ? null : other.productAvgDeliveryTime.copy();
        this.productManufacturer = other.productManufacturer == null ? null : other.productManufacturer.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.taken = other.taken == null ? null : other.taken.copy();
        this.uploaded = other.uploaded == null ? null : other.uploaded.copy();
        this.vendorsNameId = other.vendorsNameId == null ? null : other.vendorsNameId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getProductName() {
        return productName;
    }

    public StringFilter productName() {
        if (productName == null) {
            productName = new StringFilter();
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public CountryFilter getProductOrigin() {
        return productOrigin;
    }

    public CountryFilter productOrigin() {
        if (productOrigin == null) {
            productOrigin = new CountryFilter();
        }
        return productOrigin;
    }

    public void setProductOrigin(CountryFilter productOrigin) {
        this.productOrigin = productOrigin;
    }

    public StockStatusFilter getProductStockStatus() {
        return productStockStatus;
    }

    public StockStatusFilter productStockStatus() {
        if (productStockStatus == null) {
            productStockStatus = new StockStatusFilter();
        }
        return productStockStatus;
    }

    public void setProductStockStatus(StockStatusFilter productStockStatus) {
        this.productStockStatus = productStockStatus;
    }

    public TimeTakenFilter getProductAvgDeliveryTime() {
        return productAvgDeliveryTime;
    }

    public TimeTakenFilter productAvgDeliveryTime() {
        if (productAvgDeliveryTime == null) {
            productAvgDeliveryTime = new TimeTakenFilter();
        }
        return productAvgDeliveryTime;
    }

    public void setProductAvgDeliveryTime(TimeTakenFilter productAvgDeliveryTime) {
        this.productAvgDeliveryTime = productAvgDeliveryTime;
    }

    public StringFilter getProductManufacturer() {
        return productManufacturer;
    }

    public StringFilter productManufacturer() {
        if (productManufacturer == null) {
            productManufacturer = new StringFilter();
        }
        return productManufacturer;
    }

    public void setProductManufacturer(StringFilter productManufacturer) {
        this.productManufacturer = productManufacturer;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(productOrigin, that.productOrigin) &&
            Objects.equals(productStockStatus, that.productStockStatus) &&
            Objects.equals(productAvgDeliveryTime, that.productAvgDeliveryTime) &&
            Objects.equals(productManufacturer, that.productManufacturer) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(taken, that.taken) &&
            Objects.equals(uploaded, that.uploaded) &&
            Objects.equals(vendorsNameId, that.vendorsNameId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productName,
            productOrigin,
            productStockStatus,
            productAvgDeliveryTime,
            productManufacturer,
            height,
            width,
            taken,
            uploaded,
            vendorsNameId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productName != null ? "productName=" + productName + ", " : "") +
            (productOrigin != null ? "productOrigin=" + productOrigin + ", " : "") +
            (productStockStatus != null ? "productStockStatus=" + productStockStatus + ", " : "") +
            (productAvgDeliveryTime != null ? "productAvgDeliveryTime=" + productAvgDeliveryTime + ", " : "") +
            (productManufacturer != null ? "productManufacturer=" + productManufacturer + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (taken != null ? "taken=" + taken + ", " : "") +
            (uploaded != null ? "uploaded=" + uploaded + ", " : "") +
            (vendorsNameId != null ? "vendorsNameId=" + vendorsNameId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
