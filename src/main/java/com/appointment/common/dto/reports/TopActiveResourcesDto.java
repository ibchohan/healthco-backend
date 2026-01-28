package com.appointment.common.dto.reports;

import com.appointment.common.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TopActiveResourcesDto {

    private ResourceType resource;
    private long activityCount;

    // Custom constructor for JPQL mapping
    public TopActiveResourcesDto(Integer resourceTypeId, long activityCount) {
        this.resource = ResourceType.getById(resourceTypeId);
        this.activityCount = activityCount;
    }

}
