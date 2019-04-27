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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder // Building a constructor or a bean
@Data // Getter and Setter
@Table(name = "hack")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //TODO Sponsors
    // 1 Hackathon can have multiple Sponsors
    // and that will be store in spons table

    //TODO Judges table
    // Same one to many mappoing ith judge table 





}
