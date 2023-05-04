package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.PaymentCard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentCardRowMapper implements RowMapper<PaymentCard> {

    @Override
    public PaymentCard mapRow(ResultSet resultSet, int rowNum) {
        PaymentCard paymentCard;
        try {
            paymentCard = PaymentCard.builder()
                    .id(resultSet.getLong("id"))
                    .cardNumber(resultSet.getString("card_number"))
                    .expirationDate(resultSet.getString("expiration_date"))
                    .cvv(resultSet.getString("cvv"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .cardholder(resultSet.getString("cardholder"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentCard;
    }
}
