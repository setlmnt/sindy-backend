package br.com.sindy.api.controller;

import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePostDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePutDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeeResponseDto;
import br.com.sindy.domain.service.MonthlyFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Monthly Fees", description = "Monthly Fees API")
@RestController
@RequestMapping("/api/v1/monthly-fees")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@RequiredArgsConstructor
public class MonthlyFeesController {
    private final MonthlyFeeService monthlyFeeService;

    @Operation(summary = "Find all monthly fees")
    @GetMapping
    public Page<MonthlyFeeResponseDto> listMonthlyFees(
            @RequestParam(required = false) LocalDate initialDate,
            @RequestParam(required = false) LocalDate finalDate,
            Pageable pageable
    ) {
        return monthlyFeeService.findAll(initialDate, finalDate, pageable);
    }

    @Operation(summary = "Find monthly fee by id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonthlyFeeResponseDto findMonthlyFeeById(
            @PathVariable Long id
    ) {
        return monthlyFeeService.findById(id);
    }

    @Operation(summary = "Export monthly fee to pdf")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] exportMonthlyFeeToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return monthlyFeeService.exportToPdf(id, response);
    }

    @Operation(summary = "Find all monthly fees by associate id")
    @GetMapping(path = "/associates/{associateId}")
    public Page<MonthlyFeeResponseDto> findMonthlyFeeByAssociateId(
            @RequestParam(required = false) LocalDate initialDate,
            @RequestParam(required = false) LocalDate finalDate,
            @PathVariable Long associateId,
            Pageable pageable
    ) {
        return monthlyFeeService
                .findAllByAssociateIdAndMonthAndYear(associateId, initialDate, finalDate, pageable);
    }

    @Operation(summary = "Save monthly fee")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonthlyFeeResponseDto save(@RequestBody @Valid MonthlyFeePostDto dto) {
        return monthlyFeeService.save(dto);
    }

    @Operation(summary = "Update monthly fee")
    @PutMapping(path = "/{id}")
    public MonthlyFeeResponseDto update(
            @RequestBody @Valid MonthlyFeePutDto dto,
            @PathVariable Long id
    ) {
        return monthlyFeeService.update(id, dto);
    }

    @Operation(summary = "Delete monthly fee")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        monthlyFeeService.delete(id);
    }
}
