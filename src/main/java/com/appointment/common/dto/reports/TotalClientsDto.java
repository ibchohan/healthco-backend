package com.appointment.common.dto.reports;

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
public class TotalClientsDto {

    private Long totalClients;
    private Long operators;
    private Long vendors;
    private Long customers;
    private Long operatorsAndVendors;
    private Long operatorsAndCustomers;
    private Long vendorsAndCustomers;
    private Long operatorsAndVendorsAndCustomers;

}
