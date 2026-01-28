package com.appointment.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {

    ID("id"),
    CREATED_AT("createdAt"),
    ORDER_NUMBER("orderNumber"),
    ENTRY_DATE("entryDate"),
    ACCOUNT_CODE("code");

    private final String label;

    public static SortField getByLabel(String label) {
        for (SortField type : SortField.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }
}