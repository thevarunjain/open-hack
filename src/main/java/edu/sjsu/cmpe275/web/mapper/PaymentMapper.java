package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.web.model.response.PaymentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDto map(final Float amount, final String name) {
        return PaymentResponseDto.builder()
                .amount(amount)
                .hackathon(name)
                .build();
    }
}
