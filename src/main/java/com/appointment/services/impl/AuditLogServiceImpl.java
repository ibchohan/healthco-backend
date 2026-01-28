package com.appointment.services.impl;

import com.appointment.common.dto.PaginationAndSortingDto;
import com.appointment.common.enums.EventType;
import com.appointment.common.enums.SortField;
import com.appointment.common.event.AbstractEvent;
import com.appointment.common.event.Event;
import com.appointment.common.event.resource.ResourceEvent;
import com.appointment.common.utils.PaginationAndSortingHandler;
import com.appointment.common.dto.audit_log.GetAuditLogRequestDto;
import com.appointment.common.dto.reports.LoginReportDto;
import com.appointment.common.dto.reports.TopActiveResourcesDto;
import com.appointment.common.dto.reports.TopActiveUsersDto;
import com.appointment.common.utils.UserContext;
import com.appointment.entities.AuditLog;
import com.appointment.factory.AuditLogFactory;
import com.appointment.repositories.AuditLogRepository;
import com.appointment.repositories.specifications.AuditLogSpecification;
import com.appointment.services.AuditLogService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogFactory auditLogFactory;

    private volatile Queue<AuditLog> auditLogs = new ConcurrentLinkedQueue<>();

    @Transactional
    private AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    @Override
    public Page<AuditLog> findAuditLogs(GetAuditLogRequestDto getAuditLogRequestDto) {

        Specification<AuditLog> auditLogSpecification = Specification.where(
                AuditLogSpecification.eventTypeEquals(getAuditLogRequestDto.getEventType())
        ).and(
                AuditLogSpecification.resourceTypeEquals(getAuditLogRequestDto.getResourceType())
        ).and(
                AuditLogSpecification.usernamePartialMatch(getAuditLogRequestDto.getUsername())
        ).and(
                AuditLogSpecification.dateRangeWithin(getAuditLogRequestDto.getStartDateEpoch(), getAuditLogRequestDto.getEndDateEpoch())
        );

        Pageable pageable = PaginationAndSortingHandler.getPage(getAuditLogRequestDto);
        return auditLogRepository.findAll(auditLogSpecification, pageable);
    }

    @Override
    public List<LoginReportDto> findLastXLogins(int lastLoginReportCountFactor) {
        Specification<AuditLog> auditLogSpecification = Specification.where(
                AuditLogSpecification.eventTypeEquals(EventType.LOGIN)
        );
        Pageable pageable = PaginationAndSortingHandler.getPage(
                PaginationAndSortingDto.builder()
                        .pageNumber(BigDecimal.ONE.intValue())
                        .pageSize(lastLoginReportCountFactor)
                        .sortFields(new ArrayList<>(List.of(SortField.CREATED_AT)))
                        .sortDirection(Sort.Direction.DESC)
                        .build()
        );
        return auditLogRepository.findAll(auditLogSpecification, pageable)
                .getContent()
                .stream()
                .map(auditLogFactory::buildLoginReportDto)
                .toList();
    }

    @Override
    public List<TopActiveUsersDto> findTopXMostActiveUsers(int mostActiveUsersCountFactor) {
        PageRequest pageRequest = PageRequest.of(BigDecimal.ZERO.intValue(), mostActiveUsersCountFactor);
        return auditLogRepository.findTopMostActiveUsers(pageRequest);
    }

    @Override
    public List<TopActiveResourcesDto> findTopXMostActiveResources(int mostActiveResourcesCountFactor) {
        PageRequest pageRequest = PageRequest.of(BigDecimal.ZERO.intValue(), mostActiveResourcesCountFactor);
        return auditLogRepository.findTopMostActiveResources(pageRequest);
    }

    @EventListener(classes = Event.class)
    public void consumeAuditLogEvent(Event event) {
        log.info("received event: {}", event.getEventType());

        switch (event.getEventType()) {
            case LOGIN, LOGOUT, TOKEN_REFRESH -> handleUserAuthEvent((AbstractEvent) event);
            case CREATE, UPDATE, DELETE -> handleResourceEvent((ResourceEvent) event);
        }
    }

    private void handleUserAuthEvent(AbstractEvent event) {
        AuditLog auditLog = getBaseAuditLogData(event);
        auditLogs.add(auditLog);
    }

    private void handleResourceEvent(ResourceEvent resourceCreationEvent) {
        AuditLog auditLog = getBaseAuditLogData(resourceCreationEvent);
        auditLog.setResourceId(resourceCreationEvent.getResourceId());
        auditLog.setResourceType(resourceCreationEvent.getResourceType().getId());
        auditLog.setResourceName(resourceCreationEvent.getResourceName());
        auditLogs.add(auditLog);
    }


    private AuditLog getBaseAuditLogData(AbstractEvent event) {
        return AuditLog.builder()
                .eventType(event.getEventType().getId())
                .username(event.getUsername())
                .userId(event.getUserId())
                .build();
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    void saveAuditLogs() {
        if (auditLogs.isEmpty()) {
            return;
        }
        var tmp = auditLogs;
        auditLogs = new ConcurrentLinkedQueue<>();
        auditLogRepository.saveAll(tmp);
    }

}
