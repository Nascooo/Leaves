package com.leaves.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Leaves")
@Data
public class Leaves {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "paid_leave", columnDefinition = "int default 0")
	private Long paidLeave;

	@Column(name = "unpaid_leave", columnDefinition = "int default 0")
	private Long unPaidLeave;

	@PrePersist
	public void beforeInsert() {
		paidLeave = paidLeave == null ? 0 : paidLeave;
		unPaidLeave = unPaidLeave == null ? 0 : unPaidLeave;
	}
}
