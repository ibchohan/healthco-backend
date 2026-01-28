package com.appointment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuditInfoDto {

    private String uuid;
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String modifiedBy;
}