package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "hackathon_sponsor")
public class HackathonSponsor {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private HackathonSponsorId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hackathon_id", insertable = false, updatable = false)
    private Hackathon hackathonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_id", insertable = false, updatable = false)
    private Organization organizationId;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HackathonSponsorId implements Serializable {

        @Column(name = "hackathon_id", columnDefinition = "int unsigned")
        private Long hackathonId;

        @Column(name = "sponsor_id", columnDefinition = "int unsigned")
        private Long sponsorId;

    }
}
