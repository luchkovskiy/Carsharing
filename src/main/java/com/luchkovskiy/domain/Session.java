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
public class Session {

    private Long id;
    private User user;
    private Car car;
    private Timestamp start_time;
    private Timestamp end_time;
    private Float total_price;
    private String status;
    private Float distance_passed;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
