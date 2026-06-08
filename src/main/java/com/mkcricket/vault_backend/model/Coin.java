package com.mkcricket.vault_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coins")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String era;
    private String denomination;
    private String year;
    private String imageUrl;
    private String description;
    private String rarity;
}