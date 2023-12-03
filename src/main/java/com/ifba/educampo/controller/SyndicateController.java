package com.ifba.educampo.controller;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.dto.syndicate.SyndicateResponseDto;
import com.ifba.educampo.service.SyndicateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Syndicate", description = "Syndicate API")
@RestController
@RequestMapping(value = "/api/v1/syndicate")
@SecurityRequirement(name = "bearerAuth")
@Log
@RequiredArgsConstructor
public class SyndicateController {
    private final SyndicateService syndicateService;

    @Operation(summary = "Find syndicate")
    @GetMapping
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
    public SyndicateResponseDto update(
            @RequestBody @Valid SyndicatePutDto dto
    ) {
        return syndicateService.update(dto);
    }
}
