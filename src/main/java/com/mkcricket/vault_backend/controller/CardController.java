package com.mkcricket.vault_backend.controller;

import com.mkcricket.vault_backend.model.Card;
import com.mkcricket.vault_backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @GetMapping
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @GetMapping("/edition/{edition}")
    public List<Card> getByEdition(@PathVariable String edition) {
        return cardRepository.findByEdition(edition);
    }

    @GetMapping("/edition/{edition}/team/{team}")
    public List<Card> getByEditionAndTeam(
            @PathVariable String edition,
            @PathVariable String team) {
        return cardRepository.findByEditionAndTeam(edition, team);
    }

    @GetMapping("/search")
    public List<Card> search(@RequestParam String name) {
        return cardRepository.findByPlayerNameContainingIgnoreCase(name);
    }

    @PostMapping
    public Card addCard(@RequestBody Card card) {
        return cardRepository.save(card);
    }
}