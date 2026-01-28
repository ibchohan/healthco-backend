package com.appointment.factory;


import com.appointment.common.dto.AuditInfoDto;
import com.appointment.common.entity.HasIdAndAuditing;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseFactory<ENTITY extends HasIdAndAuditing, DTO extends AuditInfoDto> {

    protected abstract DTO buildDto(ENTITY input);

    protected abstract ENTITY buildEntity(DTO input);

    protected void setAuditInfo(ENTITY entity, DTO dto) {
        dto.setCreatedAt(entity.getCreatedAt().toEpochMilli());
        dto.setUpdatedAt(entity.getUpdatedAt().toEpochMilli());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
    }

    public List<DTO> buildDtoList(List<ENTITY> input) {
        return input.stream()
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    public List<ENTITY> buildEntityList(List<DTO> input) {
        return input.stream()
                .map(this::buildEntity)
                .collect(Collectors.toList());
    }

}
