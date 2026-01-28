package com.appointment.common.event.resource;

import com.appointment.common.enums.ResourceType;
import com.appointment.common.event.AbstractEvent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ResourceEvent extends AbstractEvent {

    private final Long resourceId;
    private final String resourceName;
    private final ResourceType resourceType;

}