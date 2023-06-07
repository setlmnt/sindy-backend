package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class PlaceOfBirthPutRequestBody {
	private Long id;
	private String city;
	private String state;
}
