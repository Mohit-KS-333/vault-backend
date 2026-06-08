package com.mkcricket.vault_backend.repository;

import com.mkcricket.vault_backend.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findByEra(String era);
    List<Coin> findByType(String type);
    List<Coin> findByNameContainingIgnoreCase(String name);
}