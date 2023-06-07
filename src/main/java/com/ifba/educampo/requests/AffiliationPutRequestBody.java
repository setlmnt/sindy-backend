package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class AffiliationPutRequestBody {
	private Long id;
	private String fatherName;
	private String motherName;
}
