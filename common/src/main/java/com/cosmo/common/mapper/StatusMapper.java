package com.cosmo.common.mapper;

import com.cosmo.common.entity.Status;
import com.cosmo.common.model.StatusDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusDto entityToStatusDto(Status status);

}
