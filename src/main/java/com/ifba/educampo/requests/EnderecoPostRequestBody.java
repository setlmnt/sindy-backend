package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class EnderecoPostRequestBody {
	private String rua;
	private int numero;
	private String complemento;
	private String bairro;
	private String cep;
}
