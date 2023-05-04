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
        "session"
})
@Entity
@Table(name = "accidents")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @JsonBackReference
    @ToStringExclude
    private Session session;

    @Column
    private String name;

    @Column
    private Float fine;

    @Column
    private Timestamp time;

    @Column(name = "rating_subtraction")
    private Float ratingSubtraction;

    @Column(name = "damage_level")
    private Integer damageLevel;

    @Column(name = "is_critical")
    private Boolean critical;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;


}
