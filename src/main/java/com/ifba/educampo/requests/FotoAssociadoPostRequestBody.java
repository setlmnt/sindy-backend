package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class FotoAssociadoPostRequestBody {
	private String nomeArquivo;
	private String contentType;
	private Long tamanho;
}