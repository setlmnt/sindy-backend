package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicateResponseDto;

public interface SyndicateService {
    SyndicateResponseDto find();

    SyndicateResponseDto save(SyndicatePostDto dto);

    SyndicateResponseDto update(SyndicatePutDto dto);
}
