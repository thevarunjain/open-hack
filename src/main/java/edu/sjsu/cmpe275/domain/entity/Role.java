package edu.sjsu.cmpe275.domain.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    @NaturalId
    private RoleName name;
}
