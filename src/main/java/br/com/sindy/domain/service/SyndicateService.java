package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.syndicate.SyndicatePostDto;
import br.com.sindy.domain.dto.syndicate.SyndicatePutDto;
import br.com.sindy.domain.dto.syndicate.SyndicateResponseDto;

public interface SyndicateService {
    SyndicateResponseDto find();

    SyndicateResponseDto save(SyndicatePostDto dto);

    SyndicateResponseDto update(SyndicatePutDto dto);
}
