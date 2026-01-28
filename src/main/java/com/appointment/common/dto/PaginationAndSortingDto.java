package com.appointment.common.dto;

import com.appointment.common.enums.SortField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PaginationAndSortingDto {

    // for pagination
    protected Integer pageSize;
    protected Integer pageNumber;

    // for sorting
    protected Sort.Direction sortDirection;
    protected List<SortField> sortFields;

}
