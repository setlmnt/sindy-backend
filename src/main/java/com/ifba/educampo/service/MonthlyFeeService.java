package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.MonthlyFeeDto;
import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.repository.MonthlyFeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyFeeService {
    private final GenericMapper<MonthlyFeeDto, MonthlyFee> modelMapper;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyFeeService.class);

    public Page<MonthlyFee> listAll(Pageable pageable) {
        try {
            LOGGER.info("Listing all monthly fees.");
            return monthlyFeeRepository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing monthly fees.", e);
            throw new NotFoundException("An error occurred while listing monthly fees.");
        }
    }

    public Page<MonthlyFee> listAllByMonthAndYear(int paymentMonth, int paymentYear, Pageable pageable) {
        LOGGER.info("Listing all monthly fees by month and year.");
        return monthlyFeeRepository.findAllByPaymentYearAndPaymentMonth(paymentYear, paymentMonth, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by month {} and year {} not found.", paymentMonth, paymentYear);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Page<MonthlyFee> listAllByYear(int paymentYear, Pageable pageable) {
        LOGGER.info("Listing all monthly fees by year.");
        return monthlyFeeRepository.findAllByPaymentYear(paymentYear, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by year {} not found.", paymentYear);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Page<MonthlyFee> listAllByMonth(int paymentMonth, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentMonth(paymentMonth, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by month {} not found.", paymentMonth);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public MonthlyFee findMonthlyFee(long id) {
        return monthlyFeeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fee with ID {} not found.", id);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Page<MonthlyFee> listAllByAssociateId(long associateId, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateId(associateId, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by associate ID {} not found.", associateId);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Optional<MonthlyFee> findMonthlyFeeByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear) {
        try {
            LOGGER.info("Finding monthly fee by associate ID {} and month {} and year {}.", associateId, paymentMonth, paymentYear);
            return monthlyFeeRepository.findByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear);
        } catch (Exception e) {
            LOGGER.error("An error occurred while finding monthly fee by associate ID {} and month {} and year {}.", associateId, paymentMonth, paymentYear, e);
            throw new NotFoundException("An error occurred while finding monthly fee by associate ID {} and month {} and year {}.");
        }
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear, Pageable pageable) {
        LOGGER.info("Listing all monthly fees by associate ID {} and month {} and year {}.", associateId, paymentMonth, paymentYear);
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by associate ID {} and month {} and year {} not found.", associateId, paymentMonth, paymentYear);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Page<MonthlyFee> listAllByAssociateIdAndYear(long associateId, int paymentYear, Pageable pageable) {
        LOGGER.info("Listing all monthly fees by associate ID {} and year {}.", associateId, paymentYear);
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentYear(associateId, paymentYear, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by associate ID {} and year {} not found.", associateId, paymentYear);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonth(long associateId, int paymentMonth, Pageable pageable) {
        LOGGER.info("Listing all monthly fees by associate ID {} and month {}.", associateId, paymentMonth);
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonth(associateId, paymentMonth, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fees by associate ID {} and month {} not found.", associateId, paymentMonth);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting monthly fee with ID: {}", id);
            monthlyFeeRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing monthly fees.", e);
            throw new NotFoundException("An error occurred while deleting monthly fee.");
        }
    }

    @Transactional
    public MonthlyFee save(MonthlyFeeDto monthlyFeeDto) {
        try {
            LOGGER.info("Saving monthly fee.");
            MonthlyFee monthlyFee = modelMapper.mapDtoToModel(monthlyFeeDto, MonthlyFee.class);
            monthlyFee.setAssociate(associateService.findAssociate(monthlyFeeDto.getAssociateId()));
            return monthlyFeeRepository.save(monthlyFee);
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving monthly fee.", e);
            throw new NotFoundException("An error occurred while saving monthly fee.");
        }
    }

    @Transactional
    public void replace(MonthlyFeeDto monthlyFeeDto) {
        try {
            LOGGER.info("Replacing monthly fee with ID: {}", monthlyFeeDto.getId());
            MonthlyFee savedMonthlyFee = findMonthlyFee(monthlyFeeDto.getId());
            monthlyFeeDto.setId(savedMonthlyFee.getId());

            monthlyFeeRepository.save(modelMapper.mapDtoToModel(monthlyFeeDto, MonthlyFee.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing monthly fee.", e);
            throw new NotFoundException("An error occurred while replacing monthly fee.");
        }
    }
}
