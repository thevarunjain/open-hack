package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement
public class Address {

    @Column(name = "street")
    @Size(max = 100)
    private String street;

    @Column(name = "city")
    @Size(max = 100)
    private String city;

    @Column(name = "state")
    @Size(max = 25)
    private String state;

    @Column(name = "zip")
    @Size(max = 6)
    private String zip;
}
