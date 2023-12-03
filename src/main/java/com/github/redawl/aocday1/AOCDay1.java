package com.github.redawl.aocday1;

import com.github.redawl.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AOCDay1 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay1.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";
    private static final String FOUR = "four";
    private static final String FIVE = "five";
    private static final String SIX = "six";
    private static final String SEVEN = "seven";
    private static final String EIGHT = "eight";
    private static final String NINE = "nine";

    private static final String STRING_NUMBER_PATTERN = String.format("(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)", ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);
    private static final Pattern INT_PATTERN_V2 =
            Pattern.compile("^.*?(\\d|" + STRING_NUMBER_PATTERN + ").*(\\d|" + STRING_NUMBER_PATTERN + ").*?$");
    private static final Pattern INT_PATTERN_2_V2 = Pattern.compile("(\\d)|" + STRING_NUMBER_PATTERN);
    private static final Pattern INT_PATTERN = Pattern.compile("^\\D*(\\d)?.*(\\d)\\D*$");

    private static final Map<String, Integer> STRING_TO_INT;
    static {
        STRING_TO_INT = new HashMap<>();
        STRING_TO_INT.put(ONE, 1);
        STRING_TO_INT.put(TWO, 2);
        STRING_TO_INT.put(THREE, 3);
        STRING_TO_INT.put(FOUR, 4);
        STRING_TO_INT.put(FIVE, 5);
        STRING_TO_INT.put(SIX, 6);
        STRING_TO_INT.put(SEVEN, 7);
        STRING_TO_INT.put(EIGHT, 8);
        STRING_TO_INT.put(NINE, 9);
    }
    private static Integer getIntsOrdered(String str){
        Matcher matcher = INT_PATTERN.matcher(str);
        if(matcher.find()){
            String beginning = matcher.group(1);
            String end = matcher.group(2);

            if(beginning == null){
                return Integer.parseInt(end + end);
            }
            return Integer.parseInt(beginning + end);
        }

        throw new RuntimeException("Not enough matches!");
    }

    private static Integer getIntsOrderedV2(String str){
        Matcher matcher = INT_PATTERN_V2.matcher(str);
        if(matcher.find()){
            int beginning = mapToInt( matcher.group(1)).orElse(-1);
            int end = mapToInt(matcher.group(2)).orElseThrow();

            if(beginning < 0){
                return Integer.parseInt(String.valueOf(end) + end);
            }
            return Integer.parseInt(String.valueOf(beginning) + end);
        }

        Matcher matcher1 = INT_PATTERN_2_V2.matcher(str);
        if(matcher1.find()){
            int only = mapToInt(matcher1.group(1)).orElseThrow();

            return Integer.parseInt(String.valueOf(only) + only);
        }

        throw new RuntimeException("Not enough matches!");
    }

    private static Optional<Integer> mapToInt(String str){
        if(str == null || str.isBlank()){
            return Optional.empty();
        }

        int ret;

        try{
            ret = Integer.parseInt(str);
        } catch (NumberFormatException ex){
            ret = STRING_TO_INT.get(str);
        }

        return Optional.of(ret);
    }

    public static int challenge1(List<String> strings){
        return strings.stream()
                .mapToInt(AOCDay1::getIntsOrdered)
                .sum();
    }

    public static int challenge2(List<String> strings){

        return strings.stream()
                .mapToInt(AOCDay1::getIntsOrderedV2)
                .sum();
    }

    public static void main(String [] args){
        List<String> challenge1 = fileUtils.getFileContents("challenge1.txt").toList();

        int answer1 = challenge1(challenge1);

        logger.info("Answer 1: {}", answer1);

        int answer2 = challenge2(challenge1);

        logger.info("Answer 2: {}", answer2);
    }
}