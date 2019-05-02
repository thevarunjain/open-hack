package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "hackathon_sponsor")
public class HackathonSponsor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name="hackathon_id", table = "hackathon", referencedColumnName = "id")
//    private Hackathon hackathonId;
//
//    @OneToMany
//    @JoinColumn(name = "organization_id")
//    private Organization sponsorId;
//
//    @JoinTable(name="temp", col1 = hack_id, col2 = org_id)

//    @JoinTable(name = "new", col1 = hack_id table temp1  )

        @OneToMany(cascade=CascadeType.ALL)
        @JoinTable(name="hackathon_judge",
                joinColumns={@JoinColumn(name="hackathon_id", referencedColumnName="id", table = "hackathon")}
                ,inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id", table = "user")})
        private Set<User> judges;


    @Column(name = "discount", unique = false, nullable = true)
    private int discount;
}
