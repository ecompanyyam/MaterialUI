package com.yam.ecompany.domain;

import com.yam.ecompany.domain.enumeration.Country;
import com.yam.ecompany.domain.enumeration.StockStatus;
import com.yam.ecompany.domain.enumeration.TimeTaken;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Lob
    @Column(name = "product_remark")
    private String productRemark;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_origin")
    private Country productOrigin;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_stock_status")
    private StockStatus productStockStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_avg_delivery_time")
    private TimeTaken productAvgDeliveryTime;

    @Size(max = 50)
    @Column(name = "product_manufacturer", length = 50)
    private String productManufacturer;

    @Lob
    @Column(name = "product_image")
    private byte[] productImage;

    @Column(name = "product_image_content_type")
    private String productImageContentType;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "taken")
    private Instant taken;

    @Column(name = "uploaded")
    private Instant uploaded;

    @Lob
    @Column(name = "product_attachments")
    private byte[] productAttachments;

    @Column(name = "product_attachments_content_type")
    private String productAttachmentsContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor vendorsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductRemark() {
        return this.productRemark;
    }

    public Product productRemark(String productRemark) {
        this.setProductRemark(productRemark);
        return this;
    }

    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark;
    }

    public Country getProductOrigin() {
        return this.productOrigin;
    }

    public Product productOrigin(Country productOrigin) {
        this.setProductOrigin(productOrigin);
        return this;
    }

    public void setProductOrigin(Country productOrigin) {
        this.productOrigin = productOrigin;
    }

    public StockStatus getProductStockStatus() {
        return this.productStockStatus;
    }

    public Product productStockStatus(StockStatus productStockStatus) {
        this.setProductStockStatus(productStockStatus);
        return this;
    }

    public void setProductStockStatus(StockStatus productStockStatus) {
        this.productStockStatus = productStockStatus;
    }

    public TimeTaken getProductAvgDeliveryTime() {
        return this.productAvgDeliveryTime;
    }

    public Product productAvgDeliveryTime(TimeTaken productAvgDeliveryTime) {
        this.setProductAvgDeliveryTime(productAvgDeliveryTime);
        return this;
    }

    public void setProductAvgDeliveryTime(TimeTaken productAvgDeliveryTime) {
        this.productAvgDeliveryTime = productAvgDeliveryTime;
    }

    public String getProductManufacturer() {
        return this.productManufacturer;
    }

    public Product productManufacturer(String productManufacturer) {
        this.setProductManufacturer(productManufacturer);
        return this;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public byte[] getProductImage() {
        return this.productImage;
    }

    public Product productImage(byte[] productImage) {
        this.setProductImage(productImage);
        return this;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public String getProductImageContentType() {
        return this.productImageContentType;
    }

    public Product productImageContentType(String productImageContentType) {
        this.productImageContentType = productImageContentType;
        return this;
    }

    public void setProductImageContentType(String productImageContentType) {
        this.productImageContentType = productImageContentType;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Product height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Product width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Instant getTaken() {
        return this.taken;
    }

    public Product taken(Instant taken) {
        this.setTaken(taken);
        return this;
    }

    public void setTaken(Instant taken) {
        this.taken = taken;
    }

    public Instant getUploaded() {
        return this.uploaded;
    }

    public Product uploaded(Instant uploaded) {
        this.setUploaded(uploaded);
        return this;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }

    public byte[] getProductAttachments() {
        return this.productAttachments;
    }

    public Product productAttachments(byte[] productAttachments) {
        this.setProductAttachments(productAttachments);
        return this;
    }

    public void setProductAttachments(byte[] productAttachments) {
        this.productAttachments = productAttachments;
    }

    public String getProductAttachmentsContentType() {
        return this.productAttachmentsContentType;
    }

    public Product productAttachmentsContentType(String productAttachmentsContentType) {
        this.productAttachmentsContentType = productAttachmentsContentType;
        return this;
    }

    public void setProductAttachmentsContentType(String productAttachmentsContentType) {
        this.productAttachmentsContentType = productAttachmentsContentType;
    }

    public Vendor getVendorsName() {
        return this.vendorsName;
    }

    public void setVendorsName(Vendor vendor) {
        this.vendorsName = vendor;
    }

    public Product vendorsName(Vendor vendor) {
        this.setVendorsName(vendor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productRemark='" + getProductRemark() + "'" +
            ", productOrigin='" + getProductOrigin() + "'" +
            ", productStockStatus='" + getProductStockStatus() + "'" +
            ", productAvgDeliveryTime='" + getProductAvgDeliveryTime() + "'" +
            ", productManufacturer='" + getProductManufacturer() + "'" +
            ", productImage='" + getProductImage() + "'" +
            ", productImageContentType='" + getProductImageContentType() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", taken='" + getTaken() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            ", productAttachments='" + getProductAttachments() + "'" +
            ", productAttachmentsContentType='" + getProductAttachmentsContentType() + "'" +
            "}";
    }
}
