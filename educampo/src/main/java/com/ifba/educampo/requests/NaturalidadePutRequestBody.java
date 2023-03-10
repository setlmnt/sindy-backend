package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class NaturalidadePutRequestBody {
	private Long id;
	private String municipio;
	private String estado;
}
