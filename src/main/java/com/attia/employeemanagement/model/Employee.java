package com.attia.employeemanagement.model;

import java.io.Serializable;
import java.time.OffsetDateTime;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 849343719056973592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "modified_at")
    private OffsetDateTime modifiedAt;
}
