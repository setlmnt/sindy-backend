package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class DependentesPostRequestBody {
	private String nomeEsposa;
	private int filhosMenores;
	private int filhosHomens;
	private int filhosMulheres;
	private int outrosDependentes;
}
