package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder // Building a constructor or a bean
@Data // Getter and Setter
@Table(name = "hackathon")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    private Date start_date;

    @Column(name = "end_date", nullable = false)
    private Date end_date;

    @Size(min = 10)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "fee", nullable = false)
    private Float fee;

    @Column(name = "min_size", nullable = false)
    private int min_size;

    @Column(name = "max_size", nullable = false)
    private int max_size;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="hackathon_judge",
               joinColumns={@JoinColumn(name="hackathon_id", referencedColumnName="id", table = "hackathon")}
               ,inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id", table = "user")})
    private Set<User> judges;









//    @OneToMany(cascade=CascadeType.ALL)
//    @JoinTable(name="hackathon_sponsors",
//
//            joinColumns={@JoinColumn(name="hackathon_id", referencedColumnName="id", table = "hackathon")}
//            , inverseJoinColumns={@JoinColumn(name="sponsor_id", referencedColumnName="id", table = "organization")})
//    private Set<Organization> sponsors;


}
