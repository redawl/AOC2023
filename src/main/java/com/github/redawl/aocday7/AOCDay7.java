package com.github.redawl.aocday7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.*;

public class AOCDay7 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay7.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();


    private static final String VALID_CARDS = "23456789TJQKA";
    private static final String VALID_CARDS_V2 = "J23456789TQKA";

    public static int challenge1(List<String> hands){
        TreeSet<CardHand> cardHands = new TreeSet<>(CardComparator);

        for(String hand: hands){
            String [] parts = hand.split("\\s");
            cardHands.add(new CardHand(parts[0], parts[1], false));
        }

        int total = 0;
        List<CardHand> list = cardHands.stream().toList();

        for(int i = 1; i <= cardHands.size(); i++){
            total += list.get(i - 1).getBid() * i;
        }

        return total;
    }

    private static final Comparator<CardHand> CardComparator = (c1, c2) -> {
        if(c1.equals(c2)){
            return 0;
        }

        int compare = c1.getType().compareTo(c2.getType());
        if(compare != 0){
            return compare;
        }

        List<String> cards1 = c1.getCards();
        List<String> cards2 = c2.getCards();
        for(int i = 0; i < cards1.size(); i++){
            int s1 = VALID_CARDS.indexOf(cards1.get(i));
            int s2 = VALID_CARDS.indexOf(cards2.get(i));

            if(s1 != s2){
                return s1 > s2 ? 1 : -1;
            }
        }

        return 0;
    };

    public static int challenge2(List<String> hands){
        TreeSet<CardHand> cardHands = new TreeSet<>(CardComparatorV2);

        for(String hand: hands){
            String [] parts = hand.split("\\s");
            cardHands.add(new CardHand(parts[0], parts[1], true));
        }

        int total = 0;
        List<CardHand> list = cardHands.stream().toList();

        for(int i = 1; i <= cardHands.size(); i++){
            total += list.get(i - 1).getBid() * i;
        }

        return total;
    }

    private static final Comparator<CardHand> CardComparatorV2 = (c1, c2) -> {
        if(c1.equals(c2)){
            return 0;
        }

        int compare = c1.getType().compareTo(c2.getType());
        if(compare != 0){
            return compare;
        }

        List<String> cards1 = c1.getCards();
        List<String> cards2 = c2.getCards();
        for(int i = 0; i < cards1.size(); i++){
            int s1 = VALID_CARDS_V2.indexOf(cards1.get(i));
            int s2 = VALID_CARDS_V2.indexOf(cards2.get(i));

            if(s1 != s2){
                return s1 > s2 ? 1 : -1;
            }
        }

        return 0;
    };

    public static void main(String [] args){
        List<String> hands = fileUtils.getFileContents("challenge7.txt").toList();

        int answer1 = challenge1(hands);

        logger.info("Answer 1: {}", answer1);

        int answer2 = challenge2(hands);

        logger.info("Answer 2: {}", answer2);
    }
}