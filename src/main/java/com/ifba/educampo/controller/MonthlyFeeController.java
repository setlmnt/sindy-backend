package com.ifba.educampo.controller;

import com.ifba.educampo.domain.Associate;
import com.ifba.educampo.domain.MonthlyFee;
import com.ifba.educampo.requests.MonthlyFeePostRequestBody;
import com.ifba.educampo.requests.MonthlyFeePutRequestBody;
import com.ifba.educampo.service.AssociateService;
import com.ifba.educampo.service.MonthlyFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/monthly-fee")
@RequiredArgsConstructor
public class MonthlyFeeController { // Classe de controle para as Mensalidades
    private final MonthlyFeeService monthlyFeeService;
    private final AssociateService associateService;

    @GetMapping
    public ResponseEntity<Page<MonthlyFee>> listMonthlyFees(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            Pageable pageable
    ){
        if (paymentYear != null && paymentMonth != null) {
            return ResponseEntity.ok(monthlyFeeService.listAllByMonthAndYear(paymentMonth, paymentYear,  pageable));
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
    ){
        return ResponseEntity.ok(monthlyFeeService.findMonthlyFee(id));
    }

    @GetMapping(path = "/{associateId}/associate")
    public ResponseEntity<Page<MonthlyFee>> findMonthlyFeeByAssociateId(
            @RequestParam(required = false) Integer paymentMonth,
            @RequestParam(required = false) Integer paymentYear,
            @PathVariable long associateId,
            Pageable pageable
    ){
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
    public ResponseEntity<MonthlyFee> save(@RequestBody MonthlyFeePostRequestBody monthlyFeePostRequestBody){
        // Garantir que o associado existe
        Associate associate = associateService.findAssociate(monthlyFeePostRequestBody.getAssociateId());
        if (associate == null) return ResponseEntity.badRequest().build();

        // Garantir que a data de associacao é anterior a data de pagamento
        LocalDate associationDate = associate.getAssociationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthlyFeeDate = LocalDate.of(monthlyFeePostRequestBody.getPaymentYear(), monthlyFeePostRequestBody.getPaymentMonth(), 1);
        if (monthlyFeeDate.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(monthlyFeeDate.getMonth()) ||
                                associationDate.getYear() != monthlyFeeDate.getYear()
                )
        ) return ResponseEntity.badRequest().build();

        // Garantir que a mensalidade não existe
        Optional<MonthlyFee> monthlyFee = monthlyFeeService
                .findMonthlyFeeByAssociateIdAndMonthAndYear(
                        monthlyFeePostRequestBody.getAssociateId(),
                        monthlyFeePostRequestBody.getPaymentMonth(),
                        monthlyFeePostRequestBody.getPaymentYear()
                );
        if (monthlyFee.isPresent()) return ResponseEntity.badRequest().build();

        return new ResponseEntity<>(monthlyFeeService.save(monthlyFeePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        monthlyFeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody MonthlyFeePutRequestBody monthlyFeePutRequestBody){
        // Garantir que o associado existe
        Associate associate = associateService.findAssociate(monthlyFeePutRequestBody.getAssociateId());
        if (associate == null) return ResponseEntity.badRequest().build();

        // Garantir que a data de associacao é anterior a data de pagamento
        LocalDate associationDate = associate.getAssociationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthlyFeeDate = LocalDate.of(monthlyFeePutRequestBody.getPaymentYear(), monthlyFeePutRequestBody.getPaymentMonth(), 1);
        if (monthlyFeeDate.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(monthlyFeeDate.getMonth()) ||
                        associationDate.getYear() != monthlyFeeDate.getYear()
                )
            ) return ResponseEntity.badRequest().build();

        // Garantir que a mensalidade não existe e que a mensalidade não é a mesma
        Optional<MonthlyFee> monthlyFee = monthlyFeeService
                .findMonthlyFeeByAssociateIdAndMonthAndYear(
                        monthlyFeePutRequestBody.getAssociateId(),
                        monthlyFeePutRequestBody.getPaymentMonth(),
                        monthlyFeePutRequestBody.getPaymentYear()
                );

        if (monthlyFee.isPresent() && !monthlyFee.get().getId().equals(monthlyFeePutRequestBody.getId())) return ResponseEntity.badRequest().build();

        monthlyFeeService.replace(monthlyFeePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
        monthlyFeeService.updateByFields(id, fields);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
