package com.leaves.services;

import com.leaves.dto.EmployeeDTO;
import com.leaves.dto.LeavesDTO;
import com.leaves.entities.Leaves;
import com.leaves.mappers.LeavesMapper;
import com.leaves.reposiotories.LeavesRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.Validate.notNull;

@Service
@Transactional
public class LeavesService {

	private LeavesRepository leavesRepository;

	private LeavesMapper leavesMapper;

	private RestTemplate restTemplate;

	public LeavesService(LeavesRepository leavesRepository, LeavesMapper leavesMapper, RestTemplate restTemplate) {
		this.leavesRepository = leavesRepository;
		this.leavesMapper = leavesMapper;
		this.restTemplate = restTemplate;
	}

	public LeavesDTO getLeaves(Long employeeId) {
		Assert.notNull(employeeId);

		EmployeeDTO employeeDTO = restTemplate.getForObject("http://employee/api/v1/employee/" + employeeId, EmployeeDTO.class);
		Optional<Leaves> optionalLeaves = leavesRepository.findByEmployeeId(String.valueOf(employeeId));
		Leaves leaves = optionalLeaves.orElseThrow(() -> new NoSuchElementException("Leaves Not Exists"));
		LeavesDTO response = leavesMapper.entityToDTO(leaves);
		response.setEmployeeName(employeeDTO.getName());
		response.setSubmittedLeaves(Math.addExact(response.getPaidLeave(), response.getUnPaidLeave()));
		return response;
	}

	public LeavesDTO createLeaveBalance(LeavesDTO leavesDTO) {
		Leaves leaves = leavesMapper.dtoToEntity(leavesDTO);
		Optional<Leaves> employeeOptional = leavesRepository.findByEmployeeId(leaves.getEmployeeId());
		if (employeeOptional.isPresent()) {
			throw new EntityExistsException("Employee Already Have a Balance");
		}
		Leaves createdLeave = leavesRepository.save(leaves);
		return leavesMapper.entityToDTO(createdLeave);
	}

	public LeavesDTO updateBalance(Long employeeId, LeavesDTO leavesDTO) {
		notNull(employeeId);

		Leaves leaves = leavesMapper.dtoToEntity(leavesDTO);
		Leaves updatedLeaves = leavesRepository.save(leaves);
		return leavesMapper.entityToDTO(updatedLeaves);
	}

	public List<LeavesDTO> findAllEmployeesLeaves() {
		List<Leaves> all = leavesRepository.findAll();
		return all.stream().map(leaves -> getLeaves(Long.valueOf(leaves.getEmployeeId()))).collect(Collectors.toList());
	}
}
