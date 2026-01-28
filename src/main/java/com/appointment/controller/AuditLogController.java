package com.appointment.controller;

import com.appointment.common.dto.audit_log.AuditLogDto;
import com.appointment.common.dto.audit_log.GetAuditLogRequestDto;
import com.appointment.common.utils.PaginationAndSortingHandler;
import com.appointment.factory.AuditLogFactory;
import com.appointment.services.AuditLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.appointment.common.constants.Route.AUDIT_LOG_RESOURCE_URL;

@RestController
@RequestMapping(value = {AUDIT_LOG_RESOURCE_URL})
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final AuditLogFactory auditLogFactory;

    @GetMapping("/all")
    public ResponseEntity<Page<AuditLogDto>> findAuditLogs(GetAuditLogRequestDto getAuditLogRequestDto) {
        return ResponseEntity.ok(
                PaginationAndSortingHandler.buildPaginatedResponse(
                        auditLogService.findAuditLogs(getAuditLogRequestDto),
                        auditLogFactory::buildDtoList
                )
        );
    }

}
