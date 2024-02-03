package com.ifba.educampo.api.controller;

import com.ifba.educampo.domain.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicateResponseDto;
import com.ifba.educampo.domain.service.SyndicateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Syndicate", description = "Syndicate API")
@RestController
@RequestMapping(value = "/api/v1/syndicate")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@RequiredArgsConstructor
public class SyndicateController {
    public static final String SYNDICATE = "syndicate";

    private final SyndicateService syndicateService;

    @Operation(summary = "Find syndicate")
    @GetMapping
    @Cacheable(value = SYNDICATE)
    public SyndicateResponseDto find() {
        return syndicateService.find();
    }

    @Operation(summary = "Save syndicate")
    @PostMapping
    public SyndicateResponseDto save(
            @RequestBody @Valid SyndicatePostDto dto
    ) {
        return syndicateService.save(dto);
    }

    @Operation(summary = "Update syndicate")
    @PutMapping
    @CacheEvict(value = SYNDICATE, allEntries = true)
    public SyndicateResponseDto update(
            @RequestBody @Valid SyndicatePutDto dto
    ) {
        return syndicateService.update(dto);
    }
}
