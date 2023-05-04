package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "cards", "sessions", "roles", "subscriptions"
})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column (name = "birthday_date")
    private Date birthdayDate;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column (name = "is_active")
    private Boolean active;

    @Column
    private String address;

    @Column (name = "passport_id")
    private String passportId;

    @Column (name = "driver_id")
    private String driverId;

    @Column (name = "driving_experience")
    private Float drivingExperience;

    @Column
    private Float rating;

    @Column(name = "account_balance")
    private Float accountBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email")),
            @AttributeOverride(name = "password", column = @Column(name = "user_password"))
    })
    private AuthenticationInfo authenticationInfo;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    @ToStringExclude
    private Set<PaymentCard> cards = Collections.emptySet();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToStringExclude
    private Set<Session> sessions = Collections.emptySet();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @ToStringExclude
    private Set<Role> roles = Collections.emptySet();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @ToStringExclude
    private Set<Subscription> subscriptions = Collections.emptySet();

}
