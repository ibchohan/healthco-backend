package com.appointment.entities;

import com.appointment.common.entity.HasIdAndAuditing;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "audit_log")
@JsonInclude(JsonInclude.Include. NON_NULL)
public class AuditLog extends HasIdAndAuditing {

    @Column(name = "event_type", nullable = false)
    private Integer eventType;

    @Column(name = "resource_type", nullable = false)
    private Integer resourceType;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;


}
