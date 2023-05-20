package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {"subscriptions"})
@Entity
@Table(name = "c_subscriptions_levels")
@Cacheable("subscriptionLevels")
public class SubscriptionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_level")
    private Integer accessLevel;

    @Column(name = "price_per_day")
    private Float pricePerDay;

    @Column
    private String name;

    @Column
    private LocalDateTime created = LocalDateTime.now();

    @Column
    private LocalDateTime changed = LocalDateTime.now();

    @OneToMany(mappedBy = "subscriptionLevel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToStringExclude
    private Set<Subscription> subscriptions = Collections.emptySet();

}
