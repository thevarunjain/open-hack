package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "hackathon")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private java.util.Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private java.util.Date endDate;

    @Size(min = 10)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "fee", nullable = false)
    private Float fee;

    @Column(name = "min_size", nullable = false)
    private int minSize;

    @Column(name = "max_size", nullable = false)
    private int maxSize;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="hackathon_judge",
               joinColumns={@JoinColumn(name="hackathon_id", referencedColumnName="id", table = "hackathon")}
               ,inverseJoinColumns={@JoinColumn(name="judge_id", referencedColumnName="id", table = "user")})
    private Set<User> judges;

}
