package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class DependentesPutRequestBody {
	private Long id;
	private String nomeEsposa;
	private int filhosMenores;
	private int filhosHomens;
	private int filhosMulheres;
	private int outrosDependentes;
}
