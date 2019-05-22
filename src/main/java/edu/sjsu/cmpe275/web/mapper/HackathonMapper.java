package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.HackathonEarningReport;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class HackathonMapper {

    public Hackathon map(CreateHackathonRequestDto toCreateHackathon, List<User> judges, User owner) {
        return Hackathon.builder()
                .name(toCreateHackathon.getName())
                .description(toCreateHackathon.getDescription())
                .startDate(toCreateHackathon.getStartDate())
                .endDate(toCreateHackathon.getEndDate())
                .fee(toCreateHackathon.getFee())
                .maxSize(toCreateHackathon.getMaxSize())
                .minSize(toCreateHackathon.getMinSize())
                .judges(judges)
                .status(Objects.nonNull(toCreateHackathon.getStatus())
                        ? toCreateHackathon.getStatus()
                        : "Created")
                .owner(owner)
                .build();
    }


    public HackathonResponseDto map(Hackathon hackathon) {
        return HackathonResponseDto.builder()
                .id(hackathon.getId())
                .name(hackathon.getName())
                .description(hackathon.getDescription())
                .startDate(hackathon.getStartDate())
                .endDate(hackathon.getEndDate())
                .maxSize(hackathon.getMaxSize())
                .minSize(hackathon.getMinSize())
                .fee(hackathon.getFee())
                .judges(mapJudgeResponse(hackathon))
                .status(hackathon.getStatus())
                .owner(mapOwnerResponse(hackathon.getOwner()))
                .build();
    }


    public List<HackathonResponseDto> map(final List<Hackathon> hackathons) {
        List<HackathonResponseDto> hackathonResponseDtoList = new ArrayList<>();
        for (Hackathon hackathon : hackathons) {
            hackathonResponseDtoList.add(
                    map(hackathon)
            );
        }
        return hackathonResponseDtoList;
    }

    private AssociatedUserResponseDto mapOwnerResponse(final User user) {
        return AssociatedUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .screenName(user.getScreenName())
                .build();
    }


    private Set<AssociatedUserResponseDto> mapJudgeResponse(final Hackathon hackathon) {
        Set<AssociatedUserResponseDto> members = Objects.nonNull(hackathon.getJudges())
                ? hackathon.getJudges()
                .stream()
                .map(judge -> AssociatedUserResponseDto.builder()
                        .id(judge.getId())
                        .email(judge.getEmail())
                        .screenName(judge.getScreenName())
                        .build()
                )
                .collect(Collectors.toSet())
                : new HashSet<>();

        return members;
    }


    public HackathonResponseDto map(
            Hackathon hackathon,
            List<AssociatedSponsorResponseDto> sponsorResponse,
            HackathonEarningReport earningReport
    ) {
        return HackathonResponseDto.builder()
                .id(hackathon.getId())
                .name(hackathon.getName())
                .description(hackathon.getDescription())
                .startDate(hackathon.getStartDate())
                .endDate(hackathon.getEndDate())
                .maxSize(hackathon.getMaxSize())
                .minSize(hackathon.getMinSize())
                .fee(hackathon.getFee())
                .judges(mapJudgeResponse(hackathon))
                .status(hackathon.getStatus())
                .owner(mapOwnerResponse(hackathon.getOwner()))
                .sponsors(sponsorResponse)
                .earningReport(mapEarningReportResponseDto(earningReport))
                .build();
    }

    private HackathonEarningReportResponseDto mapEarningReportResponseDto(HackathonEarningReport earningReport) {
        return HackathonEarningReportResponseDto.builder()
                .paidRegistrationFee(earningReport.getPaidRegistrationFee())
                .unpaidRegistrationFee(earningReport.getUnpaidRegistrationFee())
                .sponsorRevenue(earningReport.getSponsorRevenue())
                .expense(earningReport.getExpense())
                .profit(earningReport.getProfit())
                .build();
    }

    public HackathonMemberResponseDto map(
            final List<User> judges,
            final List<User> participants
    ) {
        return HackathonMemberResponseDto.builder()
                .judges(mapMemberResponse(judges))
                .participants(mapMemberResponse(participants))
                .build();
    }

    private List<MemberResponseDto> mapMemberResponse(final List<User> users) {
        List<MemberResponseDto> userList = Objects.nonNull(users)
                ? users
                .stream()
                .map(user -> MemberResponseDto.builder()
                        .memberId(user.getId())
                        .name(
                                NameResponseDto.builder()
                                        .first(user.getFirstName())
                                        .last(user.getLastName())
                                        .build()
                        )
                        .build()
                )
                .collect(Collectors.toList())
                : new ArrayList<>();

        return userList;
    }
}

