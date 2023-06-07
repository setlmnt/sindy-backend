package com.ifba.educampo.requests;

import java.sql.Date;

import com.ifba.educampo.domain.MaritalStatus;

import lombok.Data;

@Data
public class AssociatePutRequestBody {
	public Long id;
	private String name;
	private Long unionCard;
	private Long cpf;
	private Long rg;
	private String profession;
	private String workplace;
	private String phone;
	private String nationality;
	private java.util.Date birthDate;
	private boolean isLiterate;
	private boolean isVoter;
	private MaritalStatus maritalStatus;
	private java.util.Date associationDate;

	private AddressPutRequestBody address;
	private DependentsPutRequestBody dependents;
	private AffiliationPutRequestBody affiliation;
	private PlaceOfBirthPutRequestBody placeOfBirth;
	private AssociatePhotoPutRequestBody associatePhoto;
	private WorkRecordPutRequestBody workRecord;
}
