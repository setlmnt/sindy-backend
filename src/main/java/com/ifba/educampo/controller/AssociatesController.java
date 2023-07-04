package com.ifba.educampo.controller;

import java.util.Map;

import com.ifba.educampo.domain.MaritalStatus;
import com.ifba.educampo.utils.PdfUtil;
import org.thymeleaf.context.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifba.educampo.domain.Associate;
import com.ifba.educampo.requests.AssociatePostRequestBody;
import com.ifba.educampo.requests.AssociatePutRequestBody;
import com.ifba.educampo.service.AssociateService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/associates")
@RequiredArgsConstructor
public class AssociatesController { // Classe de controle para o Associado
	private final AssociateService associateService;

	@GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
	public byte[] exportAssociateToPdf(
			@PathVariable long id,
			HttpServletResponse response
	){
		Associate associate = associateService.findAssociate(id);

		if (associate == null) return null;

		response.setContentType("application/pdf");
		response.setHeader(
				"Content-Disposition",
				"attachment; filename=" + associate.getName() + "-" + associate.getUnionCard() + ".pdf"
		);

		Context context = new Context();
		context.setVariable("associate", associate);
		context.setVariable("divorced", MaritalStatus.DIVORCED);
		context.setVariable("never_married", MaritalStatus.NEVER_MARRIED);
		context.setVariable("married", MaritalStatus.MARRIED);
		context.setVariable("widowed", MaritalStatus.WIDOWED);

		PdfUtil pdfUtil = new PdfUtil();
		return pdfUtil.generatePdf("associate", context);
	}
	
	@GetMapping
	public ResponseEntity<Page<Associate>> listAssociate(@RequestParam(required = false) String query, Pageable pageable){
		if (query == null) return ResponseEntity.ok(associateService.listAll(pageable));
		return ResponseEntity.ok(associateService.findAssociateByNameOrCpfOrUnionCard(query, pageable));
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Associate> findAssociateById(@PathVariable long id){
        return ResponseEntity.ok(associateService.findAssociate(id));
    }
	
	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable long id){
		associateService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Associate> save(@RequestBody AssociatePostRequestBody associatePostRequestBody){
		return new ResponseEntity<>(associateService.save(associatePostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<Void> replace(@RequestBody AssociatePutRequestBody associatePutRequestBody){
		associateService.replace(associatePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		associateService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
