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
    private Timestamp start_time;
    private Timestamp end_time;
    private Integer access_level;
    private Float day_price;
    private String status;
    private Integer trips_amount;
    private Integer days_total;
    private Timestamp created;
    private Timestamp changed;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
