package com.mkcricket.vault_backend.controller;

import com.mkcricket.vault_backend.model.Coin;
import com.mkcricket.vault_backend.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @GetMapping
    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }

    @GetMapping("/era/{era}")
    public List<Coin> getByEra(@PathVariable String era) {
        return coinRepository.findByEra(era);
    }

    @GetMapping("/search")
    public List<Coin> search(@RequestParam String name) {
        return coinRepository.findByNameContainingIgnoreCase(name);
    }

    @PostMapping
    public Coin addCoin(@RequestBody Coin coin) {
        return coinRepository.save(coin);
    }
}