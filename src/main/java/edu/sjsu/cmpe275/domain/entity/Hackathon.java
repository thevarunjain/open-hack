package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
    @Column(name = "id",columnDefinition = "int unsigned")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @Size(max = 50)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private java.util.Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private java.util.Date endDate;

    @Column(name = "description", nullable = false)
    @Size(min = 10,max = 255)
    private String description;

    @Column(name = "fee", nullable = false)
    private Float fee;

    @Column(name = "min_size", nullable = false)
    @Max(value = 11)
    private int minSize;

    @Column(name = "max_size", nullable = false)
    @Max(value = 11)
    private int maxSize;

    @Column(name = "status", columnDefinition = "enum")
    private String status;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="hackathon_judge",
               joinColumns={@JoinColumn(name="hackathon_id", referencedColumnName="id", table = "hackathon")}
               ,inverseJoinColumns={@JoinColumn(name="judge_id", referencedColumnName="id", table = "user")})
    private Set<User> judges;

}
