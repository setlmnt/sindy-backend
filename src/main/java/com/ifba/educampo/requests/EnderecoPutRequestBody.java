package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class EnderecoPutRequestBody {
	private Long id;
	private String rua;
	private int numero;
	private String complemento;
	private String bairro;
	private String cep;
}
