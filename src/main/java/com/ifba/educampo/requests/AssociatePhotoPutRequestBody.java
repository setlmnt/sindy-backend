package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class AssociatePhotoPutRequestBody {
	private Long id;
	private String archiveName;
	private String contentType;
	private Long size;
}
