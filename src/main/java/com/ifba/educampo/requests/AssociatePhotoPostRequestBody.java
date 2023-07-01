package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class AssociatePhotoPostRequestBody {
	private String archiveName;
	private String contentType;
	private Long size;
}
