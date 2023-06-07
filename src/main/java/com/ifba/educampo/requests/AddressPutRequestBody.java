package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class AddressPutRequestBody {
	private Long id;
	private String street;
	private String city;
	private int number;
	private String complement;
	private String neighborhood;
	private String zipCode;
}
