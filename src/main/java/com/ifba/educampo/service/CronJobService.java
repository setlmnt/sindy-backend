package com.ifba.educampo.service;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.repository.AssociateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class CronJobService {
    private final AssociateRepository associateRepository;

    @Scheduled(cron = "1 0 0 * * *") // Every day at 12:00:01 AM
    public void setAssociateIsPaidToFalseWhenMonthlyFeeAsAlreadyExpired() {
        List<Long> associatesWithExpiredMonthlyFee = associateRepository.findAllAssociatesWithExpiredMonthlyFee();
        if (associatesWithExpiredMonthlyFee.isEmpty()) return;

        associateRepository.updateAssociatesIsPaidByIds(associatesWithExpiredMonthlyFee, false);
    }
}
