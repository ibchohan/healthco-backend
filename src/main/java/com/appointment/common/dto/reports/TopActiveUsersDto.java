package com.appointment.common.dto.reports;

import com.appointment.dto.user.UserDto;
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
public class TopActiveUsersDto {

    private UserDto user;
    private long activityCount;

}
