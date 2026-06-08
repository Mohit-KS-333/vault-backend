package com.mkcricket.vault_backend.service;

import com.mkcricket.vault_backend.model.Card;
import com.mkcricket.vault_backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getCardsByEdition(String edition) {
        return cardRepository.findByEdition(edition);
    }

    public List<Card> searchCards(String name) {
        return cardRepository.findByPlayerNameContainingIgnoreCase(name);
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }
}