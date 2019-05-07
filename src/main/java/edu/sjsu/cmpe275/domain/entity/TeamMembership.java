package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "team_membership")
public class TeamMembership {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private TeamMembershipId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private Team teamId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private User memberId;

    @Column(name = "role")
    private String role;

    @Column(name = "fee_paid")
    private Boolean fee_paid;

    @Column(name = "amount")
    private Float amount;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeamMembershipId implements Serializable {

        @Column(name = "team_id", columnDefinition = "int unsigned")
        protected long teamId;

        @Column(name = "member_id", columnDefinition = "int unsigned")
        protected long memberId;
    }
}
