package com.estudo.service;

import com.estudo.client.CreditCardDTO;
import com.estudo.client.ListAllCreditCards;
import com.estudo.domain.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final ListAllCreditCards listAllCreditCards;

    public List<CreditCard> listAllCreditCardsFromUser(final int userId) {
        return listAllCreditCards.getAllCreditCards(userId)
                .stream()
                .map(this::convert)
                .toList();
    }

    CreditCard convert(final CreditCardDTO dto) {
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
