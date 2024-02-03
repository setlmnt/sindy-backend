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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public static final String MONTHLY_FEES = "monthly-fees";
    public static final String MONTHLY_FEE = "monthly-fee";
    public static final String MONTHLY_FEES_ASSOCIATE = "monthly-fees-associate";

    private final MonthlyFeeService monthlyFeeService;

    @Operation(summary = "Find all monthly fees")
    @GetMapping
    @Cacheable(value = MONTHLY_FEES)
    public Page<MonthlyFeeResponseDto> listMonthlyFees(
            @RequestParam(required = false) LocalDate initialDate,
            @RequestParam(required = false) LocalDate finalDate,
            Pageable pageable
    ) {
        return monthlyFeeService.findAll(initialDate, finalDate, pageable);
    }

    @Operation(summary = "Find monthly fee by id")
    @GetMapping(path = "/{id}")
    @Cacheable(value = MONTHLY_FEE, key = "#id")
    public MonthlyFeeResponseDto findMonthlyFeeById(
            @PathVariable Long id
    ) {
        return monthlyFeeService.findById(id);
    }

    @Operation(summary = "Find all monthly fees by associate id")
    @GetMapping(path = "/associates/{associateId}")
    @Cacheable(value = MONTHLY_FEES_ASSOCIATE, key = "#associateId")
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
    @CacheEvict(value = {MONTHLY_FEES, MONTHLY_FEES_ASSOCIATE}, allEntries = true)
    public MonthlyFeeResponseDto save(@RequestBody @Valid MonthlyFeePostDto dto) {
        return monthlyFeeService.save(dto);
    }

    @Operation(summary = "Update monthly fee")
    @PutMapping(path = "/{id}")
    @CacheEvict(value = {MONTHLY_FEES, MONTHLY_FEE, MONTHLY_FEES_ASSOCIATE}, allEntries = true)
    public MonthlyFeeResponseDto update(
            @RequestBody @Valid MonthlyFeePutDto dto,
            @PathVariable Long id
    ) {
        return monthlyFeeService.update(id, dto);
    }

    @Operation(summary = "Delete monthly fee")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {MONTHLY_FEES, MONTHLY_FEE, MONTHLY_FEES_ASSOCIATE}, allEntries = true)
    public void delete(@PathVariable Long id) {
        monthlyFeeService.delete(id);
    }

    @Operation(summary = "Export monthly fee to pdf")
    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportMonthlyFeeToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return monthlyFeeService.exportToPdf(id, response);
    }
}
