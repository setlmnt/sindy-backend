package com.ifba.educampo.controller;

import com.ifba.educampo.dto.MonthlyFeeDto;
import com.ifba.educampo.service.MonthlyFeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monthly-fees")
@RequiredArgsConstructor
public class MonthlyFeesController { // Classe de controle para as Mensalidades
    private final MonthlyFeeService monthlyFeeService;
    @GetMapping
    public Page<MonthlyFeeDto> listMonthlyFees(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            Pageable pageable
    ) {
        return monthlyFeeService.listAll(paymentMonth, paymentYear, pageable);
    }

    @GetMapping(path = "/associates/{associateId}")
    public Page<MonthlyFeeDto> findMonthlyFeeByAssociateId(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            @PathVariable Long associateId,
            Pageable pageable
    ) {
        return monthlyFeeService
                .listAllByAssociateIdAndMonthAndYear(associateId, paymentMonth, paymentYear, pageable);
    }

    @GetMapping(path = "/{id}")
    public MonthlyFeeDto findMonthlyFeeById(
            @PathVariable Long id
    ) {
        return monthlyFeeService.findMonthlyFee(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonthlyFeeDto save(@RequestBody @Valid MonthlyFeeDto monthlyFeeDto) {
        return monthlyFeeService.save(monthlyFeeDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        monthlyFeeService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public MonthlyFeeDto update(
            @RequestBody @Valid MonthlyFeeDto monthlyFeeDto,
            @PathVariable Long id
    ) {
        return monthlyFeeService.update(id, monthlyFeeDto);
    }

    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportMonthlyFeeToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return monthlyFeeService.exportToPdf(id, response);
    }
}
