package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.UserCardBaseConverter;
import com.luchkovskiy.controllers.requests.update.UserCardUpdateRequest;
import com.luchkovskiy.models.UserCard;
import com.luchkovskiy.repository.UserCardRepository;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCardUpdateConverter extends UserCardBaseConverter<UserCardUpdateRequest, UserCard> {

    private final UserCardRepository userCardRepository;

    private final UserService userService;

    private final PaymentCardService paymentCardService;

    @Override
    public UserCard convert(UserCardUpdateRequest request) {


        UserCard userCard = userCardRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
        userCard.setUser(userService.read(request.getUserId()));
        userCard.setPaymentCard(paymentCardService.read(request.getCardId()));
        return doConvert(userCard);

    }

}
