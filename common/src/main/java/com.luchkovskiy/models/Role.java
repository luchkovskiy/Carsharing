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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private SystemRoles systemRole = SystemRoles.ROLE_USER;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @ToStringExclude
    private User user;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

}
