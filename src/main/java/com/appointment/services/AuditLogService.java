package com.appointment.services;

import com.appointment.common.dto.audit_log.GetAuditLogRequestDto;
import com.appointment.common.dto.reports.LoginReportDto;
import com.appointment.common.dto.reports.TopActiveResourcesDto;
import com.appointment.common.dto.reports.TopActiveUsersDto;
import com.appointment.entities.AuditLog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuditLogService {

    Page<AuditLog> findAuditLogs(GetAuditLogRequestDto getAuditLogRequestDto);

    List<LoginReportDto> findLastXLogins(int lastLoginReportCountFactor);

    List<TopActiveUsersDto> findTopXMostActiveUsers(int mostActiveUsersCountFactor);

    List<TopActiveResourcesDto> findTopXMostActiveResources(int mostActiveResourcesCountFactor);
}
