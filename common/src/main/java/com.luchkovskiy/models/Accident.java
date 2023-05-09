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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private LocalDateTime time;

    @Column(name = "rating_subtraction")
    private Float ratingSubtraction;

    @Column(name = "damage_level")
    private Integer damageLevel;

    @Column(name = "is_critical")
    private Boolean critical;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime changed;

}
