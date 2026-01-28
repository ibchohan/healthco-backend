package com.appointment.factory.audit;

import com.appointment.common.enums.ResourceType;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.factory.UserFactory;
import com.appointment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultAuditResourceResolver implements AuditResourceResolver {

    private final UserService userService;
    private final UserFactory userFactory;

    @Override
    public boolean supports(ResourceType resourceType) {
        return false;
    }

    @Override
    public Object resolve(ResourceType resourceType, Long resourceId) throws EntityNotFoundException {
        return switch (resourceType) {
            case USER -> userService.findUser(resourceId) != null
                    ? userFactory.buildDto(userService.findUser(resourceId))
                    : null;
            default -> null;
        };
    }
}

