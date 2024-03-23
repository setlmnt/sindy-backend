package br.com.sindy.api.controller;

import br.com.sindy.domain.dto.syndicate.SyndicatePostDto;
import br.com.sindy.domain.dto.syndicate.SyndicatePutDto;
import br.com.sindy.domain.dto.syndicate.SyndicateResponseDto;
import br.com.sindy.domain.service.SyndicateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
