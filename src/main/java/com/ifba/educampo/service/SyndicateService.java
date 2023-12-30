package com.ifba.educampo.service;

import com.ifba.educampo.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.dto.syndicate.SyndicateResponseDto;

public interface SyndicateService {
    SyndicateResponseDto find();

    SyndicateResponseDto save(SyndicatePostDto dto);

    SyndicateResponseDto update(SyndicatePutDto dto);
}
