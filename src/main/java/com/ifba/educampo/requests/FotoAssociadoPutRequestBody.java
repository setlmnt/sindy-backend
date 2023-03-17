package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class FotoAssociadoPutRequestBody {
	private Long id;
	private String nomeArquivo;
	private String contentType;
	private Long tamanho;
}
