package com.leaves.mappers;

import com.leaves.dto.LeavesDTO;
import com.leaves.entities.Leaves;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeavesMapper {

	LeavesDTO entityToDTO(Leaves leaves);

	Leaves dtoToEntity(LeavesDTO leavesDTO);

}
