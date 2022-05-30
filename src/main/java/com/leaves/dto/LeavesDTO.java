package com.leaves.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LeavesDTO {

	private Long id;
	@NotNull
	private String employeeId;
	private Long paidLeave;
	private Long unPaidLeave;
	private String employeeName;
	private Long submittedLeaves;
}
