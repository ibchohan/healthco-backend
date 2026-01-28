package com.appointment.factory.audit;


import com.appointment.common.enums.ResourceType;
import com.appointment.common.exceptions.EntityNotFoundException;

// Strategy interface allowing modules to provide resource resolution logic for audit logs
public interface AuditResourceResolver {

    boolean supports(ResourceType resourceType);

    Object resolve(ResourceType resourceType, Long resourceId) throws EntityNotFoundException;
}

