package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.UserCardBaseConverter;
import com.luchkovskiy.controllers.requests.create.UserCardCreateRequest;
import com.luchkovskiy.models.UserCard;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCardCreateConverter extends UserCardBaseConverter<UserCardCreateRequest, UserCard> {

    private final UserService userService;

    private final PaymentCardService paymentCardService;

    @Override
    public UserCard convert(UserCardCreateRequest request) {

        UserCard userCard = new UserCard();
        userCard.setUser(userService.read(request.getUserId()));
        userCard.setPaymentCard(paymentCardService.read(request.getCardId()));

        return doConvert(userCard);
    }


}
