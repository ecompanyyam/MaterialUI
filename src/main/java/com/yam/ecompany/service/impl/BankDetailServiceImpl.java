package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.BankDetail;
import com.yam.ecompany.repository.BankDetailRepository;
import com.yam.ecompany.service.BankDetailService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankDetail}.
 */
@Service
@Transactional
public class BankDetailServiceImpl implements BankDetailService {

    private final Logger log = LoggerFactory.getLogger(BankDetailServiceImpl.class);

    private final BankDetailRepository bankDetailRepository;

    public BankDetailServiceImpl(BankDetailRepository bankDetailRepository) {
        this.bankDetailRepository = bankDetailRepository;
    }

    @Override
    public BankDetail save(BankDetail bankDetail) {
        log.debug("Request to save BankDetail : {}", bankDetail);
        return bankDetailRepository.save(bankDetail);
    }

    @Override
    public BankDetail update(BankDetail bankDetail) {
        log.debug("Request to update BankDetail : {}", bankDetail);
        return bankDetailRepository.save(bankDetail);
    }

    @Override
    public Optional<BankDetail> partialUpdate(BankDetail bankDetail) {
        log.debug("Request to partially update BankDetail : {}", bankDetail);

        return bankDetailRepository
            .findById(bankDetail.getId())
            .map(existingBankDetail -> {
                if (bankDetail.getBankAccount() != null) {
                    existingBankDetail.setBankAccount(bankDetail.getBankAccount());
                }
                if (bankDetail.getBankName() != null) {
                    existingBankDetail.setBankName(bankDetail.getBankName());
                }
                if (bankDetail.getBranchSwiftCode() != null) {
                    existingBankDetail.setBranchSwiftCode(bankDetail.getBranchSwiftCode());
                }
                if (bankDetail.getIbanNo() != null) {
                    existingBankDetail.setIbanNo(bankDetail.getIbanNo());
                }
                if (bankDetail.getAccountNumber() != null) {
                    existingBankDetail.setAccountNumber(bankDetail.getAccountNumber());
                }

                return existingBankDetail;
            })
            .map(bankDetailRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankDetail> findAll(Pageable pageable) {
        log.debug("Request to get all BankDetails");
        return bankDetailRepository.findAll(pageable);
    }

    public Page<BankDetail> findAllWithEagerRelationships(Pageable pageable) {
        return bankDetailRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankDetail> findOne(Long id) {
        log.debug("Request to get BankDetail : {}", id);
        return bankDetailRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankDetail : {}", id);
        bankDetailRepository.deleteById(id);
    }
}
