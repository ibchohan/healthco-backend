package com.appointment.common.dto.audit_log;

import com.appointment.common.dto.PaginationAndSortingDto;
import com.appointment.common.enums.EventType;
import com.appointment.common.enums.ResourceType;
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
public class GetAuditLogRequestDto extends PaginationAndSortingDto {

    private String username;
    private EventType eventType;
    private ResourceType resourceType;

    private Long startDateEpoch;
    private Long endDateEpoch;

}