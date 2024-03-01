package br.com.sindy.domain.repository;

import java.util.List;

public interface CustomAssociateRepository {
    List<Long> findAllAssociatesWithExpiredMonthlyFee();
}
