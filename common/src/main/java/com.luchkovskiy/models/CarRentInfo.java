package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.sql.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "car",
})
@Entity
@Table(name = "cars_rent_info")
public class CarRentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    @ToStringExclude
    private Car car;

    @Column(name = "gas_remaining")
    private Float gasRemaining;

    @Column(name = "is_repairing")
    private Boolean repairing;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "is_available")
    private Boolean available;

    @Column
    private Float condition;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;


}
