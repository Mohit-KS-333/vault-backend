package com.mkcricket.vault_backend;

import com.mkcricket.vault_backend.model.Card;
import com.mkcricket.vault_backend.repository.CardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CardRepository cardRepository;

    public DataLoader(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(String... args) {
        if (cardRepository.count() > 0) return; // already seeded — skip

        String[] teams = {"CSK","RCB","MI","DD","DC","KXIP","PWI","KTK","KKR","RR"};

        // ── BASE CARDS (15 per team) ──
        for (String team : teams) {
            for (int i = 1; i <= 15; i++) {
                Card card = new Card();
                card.setTeam(getTeamName(team));
                card.setAbbr(team);
                card.setCardNumber(i);
                card.setType("base");
                card.setEdition("2011_IPL");
                card.setImageUrl("images/2011_IPL/base/" + team + "_base_" + i + ".jpg");
                card.setBackImageUrl("images/2011_IPL/base/" + team + "_base_" + i + "_back.jpg");
                cardRepository.save(card);
            }
        }

        // ── POWERPLAY CARDS (7 exist: PP_1 – PP_7) ──
        for (int i = 1; i <= 7; i++) {
            Card card = new Card();
            card.setTeam("Powerplay");
            card.setAbbr("PP");
            card.setCardNumber(i);
            card.setType("pp");
            card.setEdition("2011_IPL");
            card.setImageUrl("images/2011_IPL/Powerplay/PP_" + i + ".jpg");
            cardRepository.save(card);
        }

        // ── GOLD CARDS (10 exist: G_1,3,4,5,6,7,8,9,11,13) ──
        int[] goldNums = {1, 3, 4, 5, 6, 7, 8, 9, 11, 13};
        for (int i = 0; i < goldNums.length; i++) {
            Card card = new Card();
            card.setTeam("Gold");
            card.setAbbr("GOLD");
            card.setCardNumber(goldNums[i]);
            card.setType("gold");
            card.setEdition("2011_IPL");
            card.setImageUrl("images/2011_IPL/Golds/G_" + goldNums[i] + ".jpg");
            cardRepository.save(card);
        }

        // ── SILVER CARDS (10 exist: S_1 – S_10) ──
        for (int i = 1; i <= 10; i++) {
            Card card = new Card();
            card.setTeam("Silver");
            card.setAbbr("SILVER");
            card.setCardNumber(i);
            card.setType("silver");
            card.setEdition("2011_IPL");
            card.setImageUrl("images/2011_IPL/Silvers/S_" + i + ".jpg");
            cardRepository.save(card);
        }

        // ── AUCTION STARS (4 exist: AS_1 – AS_4) ──
        for (int i = 1; i <= 4; i++) {
            Card card = new Card();
            card.setTeam("Auction Stars");
            card.setAbbr("AS");
            card.setCardNumber(i);
            card.setType("auction");
            card.setEdition("2011_IPL");
            card.setImageUrl("images/2011_IPL/Auction stars/AS_" + i + ".jpg");
            cardRepository.save(card);
        }

        System.out.println("✅ 2011 IPL cards loaded! Base: 150, PP: 7, Gold: 10, Silver: 10, Auction: 4");
    }

    private String getTeamName(String abbr) {
        switch (abbr) {
            case "CSK": return "Chennai Super Kings";
            case "RCB": return "Royal Challengers Bangalore";
            case "MI":  return "Mumbai Indians";
            case "DD":  return "Delhi Daredevils";
            case "DC":  return "Deccan Chargers";
            case "KXIP": return "Kings XI Punjab";
            case "PWI": return "Pune Warriors India";
            case "KTK": return "Kochi Tuskers Kerala";
            case "KKR": return "Kolkata Knight Riders";
            case "RR":  return "Rajasthan Royals";
            default: return abbr;
        }
    }
}