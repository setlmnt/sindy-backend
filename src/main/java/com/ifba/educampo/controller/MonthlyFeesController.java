package com.ifba.educampo.controller;

import com.ifba.educampo.dto.monthlyFee.MonthlyFeePostDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePutDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeeResponseDto;
import com.ifba.educampo.service.MonthlyFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Monthly Fees", description = "Monthly Fees API")
@RestController
@RequestMapping("/api/v1/monthly-fees")
@RequiredArgsConstructor
public class MonthlyFeesController { // Classe de controle para as Mensalidades
    private final MonthlyFeeService monthlyFeeService;

    @Operation(summary = "Find all monthly fees")
    @GetMapping
    public Page<MonthlyFeeResponseDto> listMonthlyFees(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            Pageable pageable
    ) {
        return monthlyFeeService.listAll(paymentMonth, paymentYear, pageable);
    }

    @Operation(summary = "Find all monthly fees by associate id")
    @GetMapping(path = "/associates/{associateId}")
    public Page<MonthlyFeeResponseDto> findMonthlyFeeByAssociateId(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            @PathVariable Long associateId,
            Pageable pageable
    ) {
        return monthlyFeeService
                .listAllByAssociateIdAndMonthAndYear(associateId, paymentMonth, paymentYear, pageable);
    }

    @Operation(summary = "Find monthly fee by id")
    @GetMapping(path = "/{id}")
    public MonthlyFeeResponseDto findMonthlyFeeById(
            @PathVariable Long id
    ) {
        return monthlyFeeService.findMonthlyFee(id);
    }

    @Operation(summary = "Save monthly fee")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonthlyFeeResponseDto save(@RequestBody @Valid MonthlyFeePostDto dto) {
        return monthlyFeeService.save(dto);
    }

    @Operation(summary = "Update monthly fee")
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @RequestBody @Valid MonthlyFeePutDto dto,
            @PathVariable Long id
    ) {
        monthlyFeeService.update(id, dto);
    }

    @Operation(summary = "Delete monthly fee")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
