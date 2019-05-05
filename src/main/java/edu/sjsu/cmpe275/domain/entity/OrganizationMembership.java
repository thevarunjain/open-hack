package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "organization_membership")
public class OrganizationMembership {

    @EmbeddedId
    private OrganizationMembershipId id;

    @ManyToOne
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private User member;

    @Column(name = "status", nullable = false, columnDefinition = "enum")
    private String status;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrganizationMembershipId implements Serializable {

        @Column(name = "organization_id", columnDefinition = "int unsigned")
        protected long organizationId;

        @Column(name = "member_id", columnDefinition = "int unsigned")
        protected long memberId;
    }
}
