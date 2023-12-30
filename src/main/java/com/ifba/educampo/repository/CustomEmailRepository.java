package com.ifba.educampo.repository;

import com.ifba.educampo.entity.Email;
import com.ifba.educampo.enums.StatusEmailEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomEmailRepository {
    Page<Email> findAllWithFilterAndDeletedFalse(
            String owner,
            String emailTo,
            String emailFrom,
            StatusEmailEnum status,
            Pageable pageable
    );
}
