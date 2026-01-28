package com.appointment.common.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class AbstractEvent implements Event {

    protected final Long userId;
    protected final String username;

}