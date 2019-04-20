package edu.sjsu.cmpe275.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "employee")
@XmlRootElement
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "title")
    private String title;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> reports;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "collaboration",
            joinColumns = @JoinColumn(name = "id1"),
            inverseJoinColumns = @JoinColumn(name = "id2"

            )
    )
    private List<Employee> collaborators;


    public void update(final Employee fromEmployee) {
        if (Objects.nonNull(fromEmployee.getName())) {
            this.setName(fromEmployee.getName());
        }
        if (Objects.nonNull(fromEmployee.getEmail())) {
            this.setEmail(fromEmployee.getEmail());
        }
        if (Objects.nonNull(fromEmployee.getTitle())) {
            this.setTitle(fromEmployee.getTitle());
        }
        if (Objects.nonNull(fromEmployee.getAddress())) {
            Address newAddress = Address.builder()
                    .street(fromEmployee.getAddress().getStreet())
                    .city(fromEmployee.getAddress().getCity())
                    .state(fromEmployee.getAddress().getState())
                    .zip(fromEmployee.getAddress().getZip())
                    .build();
            this.setAddress(newAddress);
        }
    }

    public void removeCollaborators() {

        getCollaborators().forEach(
                collaborator -> collaborator.removeCollaborator(this)
        );

        getCollaborators().clear();
    }

    public void removeCollaborator(final Employee collaborator) {
        this.getCollaborators().remove(collaborator);
    }

    public void addCollaborator(final Employee collaborator) {
        if (!this.getCollaborators().contains(collaborator))
            this.getCollaborators().add(collaborator);
    }

    public Employee(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.title = employee.getTitle();
        this.address = employee.getAddress();
        this.employer = employee.getEmployer();
        this.manager = employee.getManager();
        this.reports = employee.getReports();
        this.collaborators = new ArrayList<>();
        for (Employee emp: employee.getCollaborators()) {
            this.collaborators.add(emp);
        }
    }
}


