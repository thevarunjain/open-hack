package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.BooleanLiteral;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "hackathon_sponsor")
public class HackathonSponsor{

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private HackathonSponsorId id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", insertable = false, updatable = false)
    private Hackathon hackathonId;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", insertable = false, updatable = false)
    private Organization organizationId;

    @Column(name = "discount")
    private int discount;

    @Embeddable
    public static class HackathonSponsorId implements Serializable {

        @Column(name = "hackathon_id", columnDefinition = "int unsigned")
        private Long hackathonId;

        @Column(name = "sponsor_id", columnDefinition = "int unsigned")
        private Long sponsorId;

    }
}
