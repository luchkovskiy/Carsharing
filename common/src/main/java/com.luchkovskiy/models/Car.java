package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.sql.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "sessions", "carRentInfo"
})
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "is_visible")
    private Boolean visible;

    @Column(name = "max_speed")
    private Float maxSpeed;

    @Column
    private String color;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "gearbox_type")
    private String gearboxType;

    @Column(name = "amount_of_sits")
    private Integer sitsAmount;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "gas_consumption")
    private Float gasConsumption;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToStringExclude
    private Set<Session> sessions = Collections.emptySet();

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @ToStringExclude
    private CarRentInfo carRentInfo;

}
