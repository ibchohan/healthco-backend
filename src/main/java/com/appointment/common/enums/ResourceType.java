package com.appointment.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {

    USER(1, "USER");

    private final Integer id;
    private final String label;

    public static ResourceType getById(Integer id) {
        for (ResourceType type : ResourceType.values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }

    public static ResourceType getByLabel(String label) {
        for (ResourceType type : ResourceType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }

}