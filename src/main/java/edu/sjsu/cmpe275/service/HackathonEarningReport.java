package edu.sjsu.cmpe275.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HackathonEarningReport {

    private Float paidRegistrationFee;

    private Float unpaidRegistrationFee;

    private Float sponsorRevenue;

    private Float expense;

    private Float profit;
}
