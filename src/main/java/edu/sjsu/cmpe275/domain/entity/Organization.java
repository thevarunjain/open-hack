package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Embedded
    private Address address;

    // TODO DO I keep it here OR should be handled by /orgs/{id}/members
//    @OneToMany(mappedBy = "member", targetEntity = OrganizationMembership.class)
//    private List<User> members;
}
