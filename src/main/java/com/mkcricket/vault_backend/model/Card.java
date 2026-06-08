package com.mkcricket.vault_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String team;
    private String abbr;
    private Integer cardNumber;
    private String type;
    private String edition;
    private String imageUrl;
    private String backImageUrl;
    private String playerName;
}