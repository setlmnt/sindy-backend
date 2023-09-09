package com.ifba.educampo.controller;

import com.ifba.educampo.model.dto.MonthlyFeeDto;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.service.AssociateService;
import com.ifba.educampo.service.MonthlyFeeService;
import com.ifba.educampo.utils.PdfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/monthly-fees")
@RequiredArgsConstructor
public class MonthlyFeesController { // Classe de controle para as Mensalidades
    private final MonthlyFeeService monthlyFeeService;
    private final AssociateService associateService;

    @GetMapping
    public ResponseEntity<Page<MonthlyFee>> listMonthlyFees(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            Pageable pageable
    ) {
        if (paymentYear != null && paymentMonth != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByMonthAndYear(paymentMonth, paymentYear, pageable));
        } else if (paymentYear != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByYear(paymentYear, pageable));
        } else if (paymentMonth != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByMonth(paymentMonth, pageable));
        }
        return ResponseEntity.ok(monthlyFeeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MonthlyFee> findMonthlyFeeById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(monthlyFeeService.findMonthlyFee(id));
    }

    @GetMapping(path = "/{associateId}/associate")
    public ResponseEntity<Page<MonthlyFee>> findMonthlyFeeByAssociateId(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            @PathVariable long associateId,
            Pageable pageable
    ) {
        if (paymentYear != null && paymentMonth != null) {
            return ResponseEntity
                    .ok(monthlyFeeService.listAllByAssociateIdAndMonthAndYear(associateId, paymentMonth, paymentYear, pageable));
        } else if (paymentYear != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByAssociateIdAndYear(associateId, paymentYear, pageable));
        } else if (paymentMonth != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByAssociateIdAndMonth(associateId, paymentMonth, pageable));
        }
        return ResponseEntity.ok(monthlyFeeService.listAllByAssociateId(associateId, pageable));
    }

    @PostMapping
    public ResponseEntity<MonthlyFee> save(@RequestBody @Valid MonthlyFeeDto monthlyFeeDto) {
        // Garantir que o associado existe
        Associate associate = associateService.findAssociate(monthlyFeeDto.getAssociateId());
        if (associate == null) return ResponseEntity.badRequest().build();

        // Garantir que a data de associacao é anterior a data de pagamento
        LocalDate associationDate = associate.getAssociationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthlyFeeDate = LocalDate.of(monthlyFeeDto.getPaymentYear(), monthlyFeeDto.getPaymentMonth(), 1);
        if (monthlyFeeDate.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(monthlyFeeDate.getMonth()) ||
                                associationDate.getYear() != monthlyFeeDate.getYear()
                )
        ) return ResponseEntity.badRequest().build();

        // Garantir que a mensalidade não existe
        Optional<MonthlyFee> monthlyFee = monthlyFeeService
                .findMonthlyFeeByAssociateIdAndMonthAndYear(
                        monthlyFeeDto.getAssociateId(),
                        monthlyFeeDto.getPaymentMonth(),
                        monthlyFeeDto.getPaymentYear()
                );
        if (monthlyFee.isPresent()) return ResponseEntity.badRequest().build();

        return new ResponseEntity<>(monthlyFeeService.save(monthlyFeeDto), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        monthlyFeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid MonthlyFeeDto monthlyFeeDto) {
        // Garantir que o associado existe
        Associate associate = associateService.findAssociate(monthlyFeeDto.getAssociateId());
        if (associate == null) return ResponseEntity.badRequest().build();

        // Garantir que a data de associacao é anterior a data de pagamento
        LocalDate associationDate = associate.getAssociationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthlyFeeDate = LocalDate.of(monthlyFeeDto.getPaymentYear(), monthlyFeeDto.getPaymentMonth(), 1);
        if (monthlyFeeDate.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(monthlyFeeDate.getMonth()) ||
                                associationDate.getYear() != monthlyFeeDate.getYear()
                )
        ) return ResponseEntity.badRequest().build();

        // Garantir que a mensalidade não existe e que a mensalidade não é a mesma
        Optional<MonthlyFee> monthlyFee = monthlyFeeService
                .findMonthlyFeeByAssociateIdAndMonthAndYear(
                        monthlyFeeDto.getAssociateId(),
                        monthlyFeeDto.getPaymentMonth(),
                        monthlyFeeDto.getPaymentYear()
                );

        if (monthlyFee.isPresent() && !monthlyFee.get().getId().equals(monthlyFeeDto.getId()))
            return ResponseEntity.badRequest().build();

        monthlyFeeService.replace(monthlyFeeDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportMonthlyFeeToPdf(
            @PathVariable long id,
            HttpServletResponse response
    ) {
        MonthlyFee monthlyFee = monthlyFeeService.findMonthlyFee(id);

        if (monthlyFee == null) return null;

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + monthlyFee.getAssociate().getName() + "-mensalidade-" + monthlyFee.getPaymentMonth() + "-" + monthlyFee.getPaymentYear() + ".pdf"
        );

        Context context = new Context();
        context.setVariable("monthlyFee", monthlyFee);

        PdfUtil pdfUtil = new PdfUtil();
        return pdfUtil.generatePdf("monthly-fee", context);
    }
}
