package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HackathonEarningReportResponseDto {

    @JsonProperty("paidRegistrationFee")
    private Float paidRegistrationFee;

    @JsonProperty("unpaidRegistrationFee")
    private Float unpaidRegistrationFee;

    @JsonProperty("sponsorRevenue")
    private Float sponsorRevenue;

    @JsonProperty("expense")
    private Float expense;

    @JsonProperty("profit")
    private Float profit;

}
