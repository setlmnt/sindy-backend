package com.ifba.educampo.requests;

import lombok.Data;

@Data
public class DependentsPostRequestBody {
	private String wifeName;
	private int minorChildren;
	private int maleChildren;
	private int femaleChildren;
	private int otherDependents;
}
