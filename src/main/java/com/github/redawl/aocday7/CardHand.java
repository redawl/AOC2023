package com.github.redawl.aocday7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardHand {
    private final List<String> cards = new ArrayList<>();
    private final int bid;

    private final HandType type;
    private static final Pattern HAND_PATTERN = Pattern.compile("([\\dTJQKA])");

    public enum HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }

    CardHand(String rawHand, String bid, boolean isV2){
        Matcher handMatcher = HAND_PATTERN.matcher(rawHand);

        while(handMatcher.find()){
            cards.add(handMatcher.group(1));
        }

        if(cards.size() != 5){
            throw new RuntimeException("Invalid size of hand.");
        }

        this.bid = Integer.parseInt(bid);

        // Compute card type
        Map<String, Integer> numCards = new HashMap<>();

        if(!isV2) {
            for (String c : cards) {
                int old = numCards.getOrDefault(c, 0);
                numCards.put(c, old + 1);
            }

            Collection<Integer> values = numCards.values();
            if (values.contains(5)) {
                type = HandType.FIVE_OF_A_KIND;
            } else if (values.contains(4)) {
                type = HandType.FOUR_OF_A_KIND;
            } else if (values.contains(3) && values.contains(2)) {
                type = HandType.FULL_HOUSE;
            } else if (values.contains(3)) {
                type = HandType.THREE_OF_A_KIND;
            } else if (values.stream().filter(x -> x == 2).toList().size() == 2) {
                type = HandType.TWO_PAIR;
            } else if (values.contains(2)) {
                type = HandType.ONE_PAIR;
            } else {
                type = HandType.HIGH_CARD;
            }
        } else {
            int jokerCount = 0;
            for (String c : cards) {
                if("J".equals(c)){
                    jokerCount++;
                } else {
                    int old = numCards.getOrDefault(c, 0);
                    numCards.put(c, old + 1);
                }
            }

            Collection<Integer> values = numCards.values();

            int handSize = 5 - jokerCount;
            if (jokerCount > 3 || values.contains(handSize)) {
                type = HandType.FIVE_OF_A_KIND;
            } else if (values.contains(handSize - 1)) {
                type = HandType.FOUR_OF_A_KIND;
            } else if ((jokerCount == 1 && values.stream().filter(x -> x == 2).toList().size() == 2)
                    || values.contains(3) && values.contains(2)) {
                type = HandType.FULL_HOUSE;
            } else if (jokerCount == 2 || (jokerCount == 1 && values.contains(2)) || values.contains(3)) {
                type = HandType.THREE_OF_A_KIND;
            } else if (jokerCount == 0 && values.stream().filter(x -> x == 2).toList().size() == 2) {
                type = HandType.TWO_PAIR;
            } else if (jokerCount == 1 || values.contains(2)) {
                type = HandType.ONE_PAIR;
            } else {
                type = HandType.HIGH_CARD;
            }
        }
    }

    public List<String> getCards() {
        return cards;
    }

    public int getBid() {
        return bid;
    }


    public HandType getType() {
        return type;
    }
}
