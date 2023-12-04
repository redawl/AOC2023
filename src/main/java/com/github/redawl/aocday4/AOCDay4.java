package com.github.redawl.aocday4;

import com.github.redawl.util.AnswerSubmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay4 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay4.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    public static int challenge1(List<String> cards){
        return cards.stream().mapToInt(AOCDay4::computeWinningAmount).sum();
    }

    private static int computeWinningAmount(String card){
        final String [] split = card.split("[|:]");

        String winningNumbersString = split[1];
        String receivedNumbersString = split[2];

        Matcher winningMatcher = NUMBER_PATTERN.matcher(winningNumbersString);
        List<String> winningNumbers = new ArrayList<>();
        while(winningMatcher.find()){
            winningNumbers.add(winningMatcher.group(1));
        }

        Matcher receivedMatcher = NUMBER_PATTERN.matcher(receivedNumbersString);

        int ret = 0;

        while(receivedMatcher.find()){
            if(winningNumbers.contains(receivedMatcher.group(1))){
                if(ret == 0) ret = 1;
                else ret = ret << 1;
            }
        }

        return ret;
    }

    public static int challenge2(List<String> cards) {
        Map<Integer, List<String>> winningNumbersMap = new HashMap<>();
        Map<Integer, List<String>> actualNumbersMap = new HashMap<>();

        // Compute winning numbers for each card
        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i);
            String winningString = card.split("[|:]")[1];
            String actualString = card.split("[|:]")[2];
            Matcher winningMatcher = NUMBER_PATTERN.matcher(winningString);
            Matcher actualMatcher = NUMBER_PATTERN.matcher(actualString);
            List<String> winningNumbers = new ArrayList<>();
            List<String> actualNumbers = new ArrayList<>();
            while (winningMatcher.find()) {
                winningNumbers.add(winningMatcher.group(1));
            }
            while (actualMatcher.find()) {
                actualNumbers.add(actualMatcher.group(1));
            }
            winningNumbersMap.put(i, winningNumbers);
            actualNumbersMap.put(i, actualNumbers);
        }

        Map<Integer, Integer> actualToWinningCount = new HashMap<>();
        actualNumbersMap.keySet().forEach(actualNumber -> {
            int winningCount = 0;
            for(String actual: actualNumbersMap.get(actualNumber)){
                if(winningNumbersMap.get(actualNumber).contains(actual)) winningCount++;
            }
            actualToWinningCount.put(actualNumber, winningCount);
        });

        List<Integer> numCards = new ArrayList<>();

        for(Integer i: actualToWinningCount.keySet()){
            int carCount = 1;// For itself
            for (int index = 0; index < i; index++) {
                if (actualToWinningCount.get(index) >= i - index) {
                    carCount += numCards.get(index);
                }
            }

            numCards.add(carCount);
        }

        return numCards.stream().mapToInt(x -> x).sum();
    }

    public static void main(String [] args){
        List<String> cards = fileUtils.getFileContents("challenge4.txt").toList();

        int answer1 = challenge1(cards);

        logger.info("Answer 1: {}", answer1);

        //AnswerSubmitter.submitAnswer(answer1, 4, AnswerSubmitter.LEVEL.LEVEL_ONE, AnswerSubmitter.IS_NOT_NOT_NEGATIVE);

        int answer2 = challenge2(cards);

        logger.info("Answer 2: {}", answer2);

        //AnswerSubmitter.submitAnswer(answer2, 4, AnswerSubmitter.LEVEL.LEVEL_TWO, AnswerSubmitter.IS_NOT_NOT_NEGATIVE);
    }
}