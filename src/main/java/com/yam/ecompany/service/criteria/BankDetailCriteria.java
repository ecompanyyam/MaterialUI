package com.yam.ecompany.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yam.ecompany.domain.BankDetail} entity. This class is used
 * in {@link com.yam.ecompany.web.rest.BankDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bank-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter bankAccount;

    private StringFilter bankName;

    private StringFilter branchSwiftCode;

    private StringFilter ibanNo;

    private StringFilter accountNumber;

    private LongFilter vendorsNameId;

    private Boolean distinct;

    public BankDetailCriteria() {}

    public BankDetailCriteria(BankDetailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchSwiftCode = other.branchSwiftCode == null ? null : other.branchSwiftCode.copy();
        this.ibanNo = other.ibanNo == null ? null : other.ibanNo.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.vendorsNameId = other.vendorsNameId == null ? null : other.vendorsNameId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BankDetailCriteria copy() {
        return new BankDetailCriteria(this);
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

    public BooleanFilter getBankAccount() {
        return bankAccount;
    }

    public BooleanFilter bankAccount() {
        if (bankAccount == null) {
            bankAccount = new BooleanFilter();
        }
        return bankAccount;
    }

    public void setBankAccount(BooleanFilter bankAccount) {
        this.bankAccount = bankAccount;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBranchSwiftCode() {
        return branchSwiftCode;
    }

    public StringFilter branchSwiftCode() {
        if (branchSwiftCode == null) {
            branchSwiftCode = new StringFilter();
        }
        return branchSwiftCode;
    }

    public void setBranchSwiftCode(StringFilter branchSwiftCode) {
        this.branchSwiftCode = branchSwiftCode;
    }

    public StringFilter getIbanNo() {
        return ibanNo;
    }

    public StringFilter ibanNo() {
        if (ibanNo == null) {
            ibanNo = new StringFilter();
        }
        return ibanNo;
    }

    public void setIbanNo(StringFilter ibanNo) {
        this.ibanNo = ibanNo;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
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
        final BankDetailCriteria that = (BankDetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchSwiftCode, that.branchSwiftCode) &&
            Objects.equals(ibanNo, that.ibanNo) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(vendorsNameId, that.vendorsNameId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccount, bankName, branchSwiftCode, ibanNo, accountNumber, vendorsNameId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetailCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (branchSwiftCode != null ? "branchSwiftCode=" + branchSwiftCode + ", " : "") +
            (ibanNo != null ? "ibanNo=" + ibanNo + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (vendorsNameId != null ? "vendorsNameId=" + vendorsNameId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
