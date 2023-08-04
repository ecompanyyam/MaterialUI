package com.yam.ecompany.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankDetail.
 */
@Entity
@Table(name = "bank_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_account")
    private Boolean bankAccount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_swift_code")
    private String branchSwiftCode;

    @Column(name = "iban_no")
    private String ibanNo;

    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor vendorsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBankAccount() {
        return this.bankAccount;
    }

    public BankDetail bankAccount(Boolean bankAccount) {
        this.setBankAccount(bankAccount);
        return this;
    }

    public void setBankAccount(Boolean bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public BankDetail bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchSwiftCode() {
        return this.branchSwiftCode;
    }

    public BankDetail branchSwiftCode(String branchSwiftCode) {
        this.setBranchSwiftCode(branchSwiftCode);
        return this;
    }

    public void setBranchSwiftCode(String branchSwiftCode) {
        this.branchSwiftCode = branchSwiftCode;
    }

    public String getIbanNo() {
        return this.ibanNo;
    }

    public BankDetail ibanNo(String ibanNo) {
        this.setIbanNo(ibanNo);
        return this;
    }

    public void setIbanNo(String ibanNo) {
        this.ibanNo = ibanNo;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BankDetail accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Vendor getVendorsName() {
        return this.vendorsName;
    }

    public void setVendorsName(Vendor vendor) {
        this.vendorsName = vendor;
    }

    public BankDetail vendorsName(Vendor vendor) {
        this.setVendorsName(vendor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankDetail)) {
            return false;
        }
        return id != null && id.equals(((BankDetail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetail{" +
            "id=" + getId() +
            ", bankAccount='" + getBankAccount() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchSwiftCode='" + getBranchSwiftCode() + "'" +
            ", ibanNo='" + getIbanNo() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            "}";
    }
}
