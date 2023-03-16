package com.luchkovskiy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subscription {

    private Long id;
    private User user;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer accessLevel;
    private Float dayPrice;
    private String status;
    private Integer tripsAmount;
    private Integer daysTotal;
    private Timestamp created;
    private Timestamp changed;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
