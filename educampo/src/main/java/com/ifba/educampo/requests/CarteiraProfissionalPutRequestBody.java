package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class CarteiraProfissionalPutRequestBody {
	private Long id;
	private Long numero;
	private String serie;
}
