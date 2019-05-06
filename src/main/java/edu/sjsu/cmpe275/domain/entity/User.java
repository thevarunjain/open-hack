package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private long id;

    @Column(name = "first_name", nullable = false)
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 255)
    private String email;

    @Column(name = "screen_name", nullable = false, unique = true)
    @Size(max = 255)
    private String screenName;

    @Column(name = "portrait_url")
    @Size(max = 255)
    private String portraitURL;

    @Column(name = "business_title")
    @Size(max = 255)
    private String businessTitle;

    @Column(name = "about_me", columnDefinition = "text")
    private String aboutMe;

    @Embedded
    private Address address;

    @Column(name = "is_validated", columnDefinition = "bit", nullable = false)
    private boolean isValidated;

    @Column(name = "is_admin", columnDefinition = "bit", nullable = false)
    private boolean isAdmin;

}


