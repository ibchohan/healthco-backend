package com.appointment.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    LOGIN(1, "LOGIN"),
    LOGOUT(2, "LOGOUT"),
    TOKEN_REFRESH(3, "TOKEN_REFRESH"),
    CREATE(4, "CREATE"),
    UPDATE(5, "UPDATE"),
    DELETE(6, "DELETE");

    private final Integer id;
    private final String label;

    public static EventType getById(Integer id) {
        for (EventType type : EventType.values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }

    public static EventType getByLabel(String label) {
        for (EventType type : EventType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }

    public boolean isCreateEvent() {
        return this.id.equals(CREATE.id);
    }

}