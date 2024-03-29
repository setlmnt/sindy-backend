package br.com.sindy.api.controller;

import br.com.sindy.domain.dto.associate.AssociateSimplifiedResponseDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficePostDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficePutDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficeResponseDto;
import br.com.sindy.domain.service.LocalOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Local Offices", description = "Local Offices API")
@RestController
@RequestMapping("/api/v1/local-offices")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@RequiredArgsConstructor
public class LocalOfficesController {
    private final LocalOfficeService localOfficeService;

    @Operation(summary = "Find all local offices")
    @GetMapping
    public Page<LocalOfficeResponseDto> listLocalOffices(Pageable pageable) {
        return localOfficeService.listAll(pageable);
    }

    @Operation(summary = "Find local office by id")
    @GetMapping(path = "/{id}")
    public LocalOfficeResponseDto findLocalOfficeById(@PathVariable Long id) {
        return localOfficeService.findLocalOffice(id);
    }

    @Operation(summary = "Find all associates by local office id")
    @GetMapping(path = "/{id}/associates")
    public Page<AssociateSimplifiedResponseDto> findAssociatesByLocalOfficeId(@PathVariable Long id, Pageable pageable) {
        return (localOfficeService.listAllAssociates(id, pageable));
    }

    @Operation(summary = "Save local office")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocalOfficeResponseDto save(@RequestBody @Valid LocalOfficePostDto dto) {
        return localOfficeService.save(dto);
    }

    @Operation(summary = "Update local office")
    @PutMapping(path = "/{id}")
    public LocalOfficeResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid LocalOfficePutDto dto
    ) {
        return localOfficeService.update(id, dto);
    }

    @Operation(summary = "Delete local office")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        localOfficeService.delete(id);
    }
}
