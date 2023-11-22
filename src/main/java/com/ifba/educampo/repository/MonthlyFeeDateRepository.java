package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.model.entity.MonthlyFeeDate;
import com.ifba.educampo.model.entity.associate.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyFeeDateRepository extends JpaRepository<MonthlyFeeDate, Long> {
}
