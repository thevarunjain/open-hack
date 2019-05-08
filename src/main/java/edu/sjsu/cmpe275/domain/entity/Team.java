package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "submission_url")
    private String submissionURL;

    @Column(name = "grades")
    private Float grades;

    @OneToOne
    @JoinColumn(name = "hackathon_id", nullable = false, updatable = false)
    private Hackathon hackathon;

    @Column(name = "is_Finalized")
    private Boolean isFinalized;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;
}
