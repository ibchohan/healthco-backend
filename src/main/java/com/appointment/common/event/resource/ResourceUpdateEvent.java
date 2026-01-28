package com.appointment.common.event.resource;

import com.appointment.common.enums.EventType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ResourceUpdateEvent extends ResourceEvent {

    @Override
    public EventType getEventType() {
        return EventType.UPDATE;
    }
}