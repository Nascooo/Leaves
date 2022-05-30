package com.leaves.reposiotories;

import com.leaves.entities.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeavesRepository extends JpaRepository<Leaves, Long> {

	Optional<Leaves> findByEmployeeId(String employeeId);
}
