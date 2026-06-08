package com.mkcricket.vault_backend.repository;

import com.mkcricket.vault_backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByEdition(String edition);
    List<Card> findByEditionAndTeam(String edition, String team);
    List<Card> findByEditionAndType(String edition, String type);
    List<Card> findByPlayerNameContainingIgnoreCase(String name);
    Optional<Card> findByEditionAndAbbrIgnoreCaseAndCardNumberAndType(
        String edition, String abbr, Integer cardNumber, String type);
}
