package com.appointment.common.dto.audit_log;

import com.appointment.common.dto.AuditInfoDto;
import com.appointment.common.enums.EventType;
import com.appointment.common.enums.ResourceType;
import com.appointment.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditLogDto extends AuditInfoDto {

    private Long id;
    private EventType eventType;
    private ResourceType resourceType;
    private String resourceName;
    private Long resourceId;
    private Long userId;
    private String username;
    private Object resource;
    private UserDto user;

}