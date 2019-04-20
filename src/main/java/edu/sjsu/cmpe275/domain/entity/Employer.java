package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlRootElement
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    public void update(Employer fromEmployer) {
        if (Objects.nonNull(fromEmployer.getName())) {
            this.setName(fromEmployer.getName());
        }
        if (Objects.nonNull(fromEmployer.getDescription())) {
            this.setDescription(fromEmployer.getDescription());
        }
        if (Objects.nonNull(fromEmployer.getAddress())) {
            Address newAddress = Address.builder()
                    .street(fromEmployer.getAddress().getStreet())
                    .city(fromEmployer.getAddress().getCity())
                    .state(fromEmployer.getAddress().getState())
                    .zip(fromEmployer.getAddress().getZip())
                    .build();
            this.setAddress(newAddress);
        }
    }
}
