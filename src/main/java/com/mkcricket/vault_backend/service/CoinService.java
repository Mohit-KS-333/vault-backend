package com.mkcricket.vault_backend.service;

import com.mkcricket.vault_backend.model.Coin;
import com.mkcricket.vault_backend.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }

    public Coin saveCoin(Coin coin) {
        return coinRepository.save(coin);
    }
}