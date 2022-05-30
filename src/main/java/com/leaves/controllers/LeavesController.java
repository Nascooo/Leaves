package com.leaves.controllers;

import com.leaves.dto.LeavesDTO;
import com.leaves.services.LeavesService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves/employee")
public class LeavesController {

	private LeavesService leavesService;

	public LeavesController(LeavesService leavesService) {
		this.leavesService = leavesService;
	}

	@GetMapping("{id}")
	public LeavesDTO getLeavesForEmployee(@PathVariable("id") Long employeeId) {
		return leavesService.getLeaves(employeeId);
	}

	@PostMapping
	public LeavesDTO createLeavesBalance(@RequestBody LeavesDTO leavesDTO) {
		return leavesService.createLeaveBalance(leavesDTO);
	}

	@PutMapping("{id}")
	public LeavesDTO updateBalance(@RequestBody @Valid LeavesDTO leavesDTO, @PathVariable("id") Long employeeId) {
		return leavesService.updateBalance(employeeId, leavesDTO);
	}

	@GetMapping
	public List<LeavesDTO> findAll() {
		return leavesService.findAllEmployeesLeaves();
	}
}
