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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "screen_name", nullable = false, unique = true)
    private String screenName;

    @Column(name = "portrait_url")
    private String portraitURL;

    @Column(name = "business_title")
    private String businessTitle;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "about_me")
    private String aboutMe;

    @Embedded
    private Address address;

}


