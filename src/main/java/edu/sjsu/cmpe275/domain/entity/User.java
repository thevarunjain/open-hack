package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "password", nullable = false)
    @Size(max = 255)
    private String password;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void update(final User fromRequest) {
        if (Objects.nonNull(fromRequest.getFirstName())) {
            this.setFirstName(fromRequest.getFirstName());
        }
        if (Objects.nonNull(fromRequest.getLastName())) {
            this.setLastName(fromRequest.getLastName());
        }
        if (Objects.nonNull(fromRequest.getPortraitURL())) {
            this.setPortraitURL(fromRequest.getPortraitURL());
        }
        if (Objects.nonNull(fromRequest.getBusinessTitle())) {
            this.setBusinessTitle(fromRequest.getBusinessTitle());
        }
        if (Objects.nonNull(fromRequest.getAboutMe())) {
            this.setAboutMe(fromRequest.getAboutMe());
        }
        if (Objects.nonNull(fromRequest.getAddress())) {
            Address newAddress = Address.builder()
                    .street(
                            Objects.nonNull(fromRequest.getAddress().getStreet())
                                    ? fromRequest.getAddress().getStreet()
                                    : this.getAddress().getStreet()
                    )
                    .city(
                            Objects.nonNull(fromRequest.getAddress().getCity())
                                    ? fromRequest.getAddress().getCity()
                                    : this.getAddress().getCity()
                    )
                    .state(
                            Objects.nonNull(fromRequest.getAddress().getState())
                                    ? fromRequest.getAddress().getState()
                                    : this.getAddress().getState()
                    )
                    .zip(
                            Objects.nonNull(fromRequest.getAddress().getZip())
                                    ? fromRequest.getAddress().getZip()
                                    : this.getAddress().getZip()
                    )
                    .build();
            this.setAddress(newAddress);
        }
    }
}


