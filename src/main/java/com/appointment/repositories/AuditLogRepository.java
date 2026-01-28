package com.appointment.repositories;

import com.appointment.common.dto.reports.TopActiveResourcesDto;
import com.appointment.common.dto.reports.TopActiveUsersDto;
import com.appointment.entities.AuditLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

    @Query("SELECT new com.appointment.common.dto.reports.TopActiveUsersDto(" +
            "new com.appointment.dto.user.UserDto(al.userId, al.username), " +
            "COUNT(al)) " +
            "FROM audit_log al " +
            "WHERE al.userId IS NOT NULL " +
            "GROUP BY al.userId, al.username " +
            "ORDER BY COUNT(al) DESC")
    List<TopActiveUsersDto> findTopMostActiveUsers(Pageable pageable);

    @Query("SELECT new com.appointment.common.dto.reports.TopActiveResourcesDto(" +
            "al.resourceType, " +
            "COUNT(al)) " +
            "FROM audit_log al " +
            "WHERE al.resourceType IS NOT NULL " +
            "GROUP BY al.resourceType " +
            "ORDER BY COUNT(al) DESC")
    List<TopActiveResourcesDto> findTopMostActiveResources(PageRequest pageRequest);
}
