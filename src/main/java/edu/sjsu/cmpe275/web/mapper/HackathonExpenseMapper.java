package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonExpense;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonExpenseRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonExpenseResponseDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationMembershipResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class HackathonExpenseMapper {
    public HackathonExpense map(
            final CreateHackathonExpenseRequestDto toCreateExpense,
            final Hackathon hackathon
    ) {
        return HackathonExpense.builder()
                .title(toCreateExpense.getTitle())
                .description(toCreateExpense.getDescription())
                .amount(toCreateExpense.getAmount())
                .date(toCreateExpense.getDate())
                .hackathon(hackathon)
                .build();
    }

    public HackathonExpenseResponseDto map(
            final HackathonExpense createdExpense
    ) {
        return HackathonExpenseResponseDto.builder()
                .id(createdExpense.getId())
                .title(createdExpense.getTitle())
                .description(createdExpense.getDescription())
                .date(createdExpense.getDate())
                .amount(createdExpense.getAmount())
                .build();
    }

    public List<HackathonExpenseResponseDto> map(
            final List<HackathonExpense> hackathonExpenseList
    ) {
        List<HackathonExpenseResponseDto> hackathonExpenseResponseDtoList =
                Objects.nonNull(hackathonExpenseList)
                        ? hackathonExpenseList
                        .stream()
                        .map(hackathonExpense -> map(hackathonExpense))
                        .collect(Collectors.toList()) : new ArrayList<>();
        return hackathonExpenseResponseDtoList;
    }
}
