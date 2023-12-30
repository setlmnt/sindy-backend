package com.ifba.educampo.repository;

import com.ifba.educampo.entity.Email;
import com.ifba.educampo.enums.EmailStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomEmailRepository {
    Page<Email> findAllWithFilterAndDeletedFalse(
            String owner,
            String emailTo,
            String emailFrom,
            EmailStatusEnum status,
            Pageable pageable
    );
}
