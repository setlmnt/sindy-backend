package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class WorkRecordPutRequestBody {
	private Long id;
	private Long number;
	private String series;
}
