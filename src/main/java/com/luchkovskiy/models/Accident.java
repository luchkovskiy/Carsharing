package com.luchkovskiy.models;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Accident {

    private Long id;
    private Session session;
    private String name;
    private Float fine;
    private Timestamp time;
    private Float ratingSubtraction;
    private Integer damageLevel;
    private Boolean critical;
    private Timestamp created;
    private Timestamp changed;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
