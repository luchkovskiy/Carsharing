package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.Builder;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "accidents", "user", "car"
})
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @ToStringExclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    @ToStringExclude
    private Car car;

    @Column (name = "start_time")
    private Timestamp startTime;

    @Column (name = "end_time")
    private Timestamp endTime;

    @Column (name = "total_price")
    private Float totalPrice;

    @Column
    private String status;

    @Column (name = "distance_passed")
    private Float distancePassed;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToStringExclude
    Set<Accident> accidents = Collections.emptySet();

}
