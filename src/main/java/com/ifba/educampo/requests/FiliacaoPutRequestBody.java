package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class FiliacaoPutRequestBody {
	private Long id;
	private String nomePai;
	private String nomeMae;
}
