package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private Boolean repairing = false;

    @Column(name = "is_available")
    private Boolean available = true;

    @Column(name = "current_location")
    private String currentLocation;

    @Column
    private Float condition;

    @Column
    private LocalDateTime created = LocalDateTime.now();

    @Column
    private LocalDateTime changed = LocalDateTime.now();

}
