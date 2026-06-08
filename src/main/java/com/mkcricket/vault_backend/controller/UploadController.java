package com.mkcricket.vault_backend.controller;

import com.mkcricket.vault_backend.model.Card;
import com.mkcricket.vault_backend.model.Upload;
import com.mkcricket.vault_backend.repository.CardRepository;
import com.mkcricket.vault_backend.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/uploads")
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired private UploadRepository uploadRepository;
    @Autowired private CardRepository cardRepository;

    // ── GET pending ──
    @GetMapping("/pending")
    public List<Upload> getPending() {
        return uploadRepository.findByStatus("pending");
    }

    // ── POST multipart ──
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Upload submitUpload(
        @RequestParam("section")    String section,
        @RequestParam(value = "edition",    defaultValue = "2011_IPL") String edition,
        @RequestParam(value = "cardType",   defaultValue = "base")     String cardType,
        @RequestParam(value = "teamName",   required = false)          String teamName,
        @RequestParam(value = "cardNumber", required = false)          Integer cardNumber,
        @RequestParam(value = "notes",      required = false)          String notes,
        @RequestParam(value = "trackingId", required = false)          String trackingId,
        @RequestParam("frontImage")         MultipartFile frontImage,
        @RequestParam(value = "backImage",  required = false)          MultipartFile backImage
    ) throws IOException {

        Path uploadDir = Paths.get(System.getProperty("user.dir"),
                "src", "main", "resources", "static", "images", "uploads");
        Files.createDirectories(uploadDir);

        String ext = getExt(frontImage.getOriginalFilename());
        String frontName = UUID.randomUUID().toString().replace("-","").substring(0,10) + ext;
        Files.copy(frontImage.getInputStream(),
                   uploadDir.resolve(frontName),
                   StandardCopyOption.REPLACE_EXISTING);

        String backName = null;
        if (backImage != null && !backImage.isEmpty()) {
            String bExt = getExt(backImage.getOriginalFilename());
            backName = UUID.randomUUID().toString().replace("-","").substring(0,10) + "_back" + bExt;
            Files.copy(backImage.getInputStream(),
                       uploadDir.resolve(backName),
                       StandardCopyOption.REPLACE_EXISTING);
        }

        Upload u = new Upload();
        u.setName(notes != null && !notes.isBlank() ? notes : frontImage.getOriginalFilename());
        u.setTeamName(teamName != null ? teamName.toUpperCase().trim() : null);
        u.setCardNumber(cardNumber);
        u.setCardType(cardType);
        u.setEdition(edition);
        u.setSection(section);
        u.setStatus("pending");
        u.setImageUrl("images/uploads/" + frontName);
        u.setBackImageUrl(backName != null ? "images/uploads/" + backName : null);
        u.setTrackingId(trackingId);
        u.setUploadDate(LocalDate.now());
        return uploadRepository.save(u);
    }

    // ── APPROVE → fills the exact card slot ──
    @PutMapping("/{id}/approve")
    public Upload approve(@PathVariable Long id) {
        Upload u = uploadRepository.findById(id).orElseThrow();
        u.setStatus("approved");
        uploadRepository.save(u);

        if (u.getTeamName() != null && u.getCardNumber() != null) {
            cardRepository.findByEditionAndAbbrIgnoreCaseAndCardNumberAndType(
                u.getEdition(),
                u.getTeamName(),
                u.getCardNumber(),
                u.getCardType() != null ? u.getCardType() : "base"
            ).ifPresent(card -> {
                card.setImageUrl(u.getImageUrl());
                if (u.getBackImageUrl() != null)
                    card.setBackImageUrl(u.getBackImageUrl());
                if (u.getName() != null)
                    card.setPlayerName(u.getName());
                cardRepository.save(card);
            });
        }
        return u;
    }

    // ── REJECT with reason ──
    @PutMapping("/{id}/reject")
    public Upload reject(
        @PathVariable Long id,
        @RequestBody(required = false) Map<String,String> body
    ) {
        Upload u = uploadRepository.findById(id).orElseThrow();
        u.setStatus("rejected");
        if (body != null && body.containsKey("reason"))
            u.setRejectionReason(body.get("reason"));
        return uploadRepository.save(u);
    }

    // ── TRACK by token ──
    @GetMapping("/track/{token}")
    public ResponseEntity<?> track(@PathVariable String token) {
        return uploadRepository.findByTrackingId(token)
            .map(u -> ResponseEntity.ok(Map.of(
                "status",   u.getStatus(),
                "cardInfo", buildCardInfo(u),
                "reason",   u.getRejectionReason() != null ? u.getRejectionReason() : ""
            )))
            .orElse(ResponseEntity.notFound().build());
    }

    private String buildCardInfo(Upload u) {
        if (u.getTeamName() != null && u.getCardNumber() != null)
            return u.getTeamName() + " #" + u.getCardNumber() + " " + u.getCardType();
        return u.getName() != null ? u.getName() : "Card";
    }

    private String getExt(String filename) {
        if (filename != null && filename.contains("."))
            return filename.substring(filename.lastIndexOf("."));
        return ".jpg";
    }
}
