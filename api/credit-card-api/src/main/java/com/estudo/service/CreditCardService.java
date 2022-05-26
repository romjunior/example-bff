package com.estudo.service;

import com.estudo.client.ICreditCardDTO;
import com.estudo.client.ListAllCreditCards;
import com.estudo.domain.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final ListAllCreditCards listAllCreditCards;

    public Flux<CreditCard> listAllCreditCardsFromUser(final int userId) {
        return listAllCreditCards.getAllCreditCards(userId)
                .map(this::convert);
    }

    CreditCard convert(final ICreditCardDTO dto) {
        return new CreditCard(
                dto.getId(),
                dto.getCardNumber(),
                dto.getName(),
                dto.getExpirationDate(),
                dto.getAddress(),
                dto.getSteps().stream().map(creditCardStep -> new CreditCard.Step(creditCardStep.address(), creditCardStep.date())).toList()
        );
    }
}
