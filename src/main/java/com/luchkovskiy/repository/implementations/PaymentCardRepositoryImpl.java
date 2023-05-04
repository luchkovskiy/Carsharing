package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.repository.PaymentCardRepository;
import com.luchkovskiy.repository.implementations.rowmappers.PaymentCardRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentCardRepositoryImpl implements PaymentCardRepository {

    private final NamedParameterJdbcTemplate template;
    private final PaymentCardRowMapper paymentCardRowMapper;

    @Override
    public PaymentCard read(Long id) {
        return template.queryForObject("SELECT * FROM payment_cards WHERE id = :id", new MapSqlParameterSource("id", id), paymentCardRowMapper);
    }

    @Override
    public List<PaymentCard> readAll() {
        return template.query("SELECT * FROM payment_cards", paymentCardRowMapper);
    }

    @Override
    public PaymentCard create(PaymentCard paymentCard) {
        paymentCard.setId(template.queryForObject("INSERT INTO payment_cards (card_number, expiration_date, cvv, changed, cardholder)" +
                        " VALUES (card_number = :card_number, expiration_date = :expiration_date, cvv = :cvv, changed = :changed, cardholder = :cardholder) RETURNING id",
                getParameterSource(paymentCard), Long.class));
        return paymentCard;
    }

    @Override
    public PaymentCard update(PaymentCard paymentCard) {
        template.update("UPDATE payment_cards SET card_number = :card_number, expiration_date = :expiration_date, cvv = :cvv, changed = :changed," +
                " cardholder = :cardholder WHERE id = :id", getParameterSource(paymentCard));
        return paymentCard;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM payment_cards WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    private SqlParameterSource getParameterSource(PaymentCard paymentCard) {
        return new MapSqlParameterSource()
                .addValue("id", paymentCard.getId())
                .addValue("card_number", paymentCard.getCardNumber())
                .addValue("expiration_date", paymentCard.getExpirationDate())
                .addValue("cvv", paymentCard.getCvv())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("cardholder", paymentCard.getCardholder());

    }
}
