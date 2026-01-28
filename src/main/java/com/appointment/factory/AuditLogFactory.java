package com.appointment.factory;

import com.appointment.common.enums.EventType;
import com.appointment.common.enums.ResourceType;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.common.dto.audit_log.AuditLogDto;
import com.appointment.common.dto.reports.LoginReportDto;
import com.appointment.dto.user.UserDto;
import com.appointment.entities.AuditLog;
import com.appointment.factory.audit.AuditResourceResolver;
import com.appointment.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class AuditLogFactory extends BaseFactory<AuditLog, AuditLogDto> {

    private final UserFactory userFactory;
    private final UserService userService;

    private final List<AuditResourceResolver> resourceResolvers;

    @Override
    public AuditLogDto buildDto(AuditLog input) {
        AuditLogDto dto = AuditLogDto.builder()
                .id(input.getId())
                .eventType(EventType.getById(input.getEventType()))
                .userId(input.getUserId())
                .username(input.getUsername())
                .resourceId(input.getResourceId())
                .resourceName(input.getResourceName())
                .resourceType(ResourceType.getById(input.getResourceType()))
                .build();
        setAuditInfo(input, dto);
        dto.setResource(resolveResource(dto));
        return dto;
    }

    // Enriched DTO builders
    public AuditLogDto buildDtoWithUser(AuditLog input) {
        AuditLogDto dto = buildDto(input);
        try {
            var user = userService.findUser(input.getUserId());
            if (input.getUserId() != null) {
                dto.setUser(userFactory.buildDto(user));
            }
        } catch (Exception e) {
            log.warn("User not found for id {}: {}", input.getUserId(), e.getMessage());
        }
        return dto;
    }

    @Override
    public AuditLog buildEntity(AuditLogDto input) {
        return null;
    }

    public LoginReportDto buildLoginReportDto(AuditLog auditLog) {
        return LoginReportDto.builder()
                .user(UserDto.builder()
                        .id(auditLog.getUserId())
                        .username(auditLog.getUsername())
                        .build()
                )
                .loginEpoch(auditLog.getCreatedAt().toEpochMilli())
                .build();
    }

    private Object resolveResource(AuditLogDto dto) {
        if (dto.getResourceType() == null || dto.getResourceId() == null) {
            return null;
        }
        try {
            return resourceResolvers.stream()
                    .filter(resolver -> resolver.supports(dto.getResourceType()))
                    .map(resolver -> {
                        try {
                            return resolver.resolve(dto.getResourceType(), dto.getResourceId());
                        } catch (EntityNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.warn("Resource not found for type {} and id {}: {}", dto.getResourceType(), dto.getResourceId(), e.getMessage());
            return null;
        }
    }

    @Override
    public List<AuditLogDto> buildDtoList(List<AuditLog> inputList) {
        if (inputList == null) return null;
        return inputList.stream().map(this::buildDtoWithUser).toList();
    }
}
