package com.mkcricket.vault_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "uploads")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String teamName;
    private Integer cardNumber;
    private String cardType;
    private String edition;
    private String section;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    @Column(columnDefinition = "TEXT")
    private String backImageUrl;
    private String trackingId;
    private String rejectionReason;
    private LocalDate uploadDate;
}
