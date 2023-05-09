package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "sessions", "carRentInfo", "carClass"
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
    private LocalDateTime created;

    @Column
    private LocalDateTime changed;

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

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference
    @ToStringExclude
    private CarClass carClass;

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
