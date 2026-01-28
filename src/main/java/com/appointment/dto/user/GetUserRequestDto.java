package com.appointment.dto.user;

import com.appointment.common.dto.PaginationAndSortingDto;
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
public class GetUserRequestDto extends PaginationAndSortingDto {

    private String searchQuery;

    private Boolean isDeactivated;

}