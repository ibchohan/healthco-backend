package com.appointment.common.utils;

import com.appointment.common.dto.PaginationAndSortingDto;
import com.appointment.common.enums.SortField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.function.Function;

@Slf4j(topic = "PaginationAndSortingHandler")
public class PaginationAndSortingHandler {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE_NUMBER = 1;

    public static Pageable getPage(PaginationAndSortingDto paginationAndSortingDto) {

        Integer pageNumber = paginationAndSortingDto.getPageNumber();
        Integer pageSize = paginationAndSortingDto.getPageSize();
        List<SortField> sortFields = paginationAndSortingDto.getSortFields();
        Sort.Direction sortDirection = paginationAndSortingDto.getSortDirection();

        if (CommonUtils.isNull(pageNumber) || CommonUtils.isNull(pageSize)) {
            pageSize = DEFAULT_PAGE_SIZE;
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        return getPageableWithSortHandling(pageNumber, pageSize, sortFields, sortDirection);
    }

    private static Pageable getPageableWithSortHandling(Integer pageNumber, Integer pageSize, List<SortField> sortFields, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, SortField.ID.getLabel());
        if (!CommonUtils.isNull(sortDirection) && !CommonUtils.isNull(sortFields) && !sortFields.isEmpty()) {
            sortFields.add(SortField.ID);
            String[] sortFieldsArray = sortFields.stream().map(SortField::getLabel).toArray(String[]::new);
            pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortFieldsArray);
        }
        return pageable;
    }

    public static <T, R> PageImpl<R> buildPaginatedResponse(Page<T> page, Function<List<T>, List<R>> listBuilderFunction) {
        return new PageImpl<>(
                listBuilderFunction.apply(page.getContent()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

}