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
        "user"
})
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @ToStringExclude
    private User user;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column
    private String status;

    @Column(name = "amount_of_trips")
    private Integer tripsAmount;

    @Column(name = "days_total")
    private Integer daysTotal;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "level_id")
    private Integer levelId;

}
