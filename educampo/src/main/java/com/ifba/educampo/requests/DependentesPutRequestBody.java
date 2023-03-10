package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class DependentesPutRequestBody {
	private Long id;
	private int filhosMenores;
	private int filhosHomens;
	private int filhosMulheres;
	private int outrosDependentes;
}
