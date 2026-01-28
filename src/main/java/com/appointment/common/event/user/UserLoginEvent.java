package com.appointment.common.event.user;

import com.appointment.common.enums.EventType;
import com.appointment.common.event.AbstractEvent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserLoginEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.LOGIN;
    }
}