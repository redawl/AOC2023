package com.github.redawl.aocday2;

import com.github.redawl.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOCDay2 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay2.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final Pattern GAME_ID_PATTERN = Pattern.compile("^Game (\\d+)");

    private static final Pattern CUBE_PATTERN = Pattern.compile("(\\d+) ((blue)|(red)|(green))");

    public static int challenge1(List<String> games){
        return games.stream()
                .mapToInt(game -> isGamePossible(game).orElse(0)).sum();
    }

    private static Optional<Integer> isGamePossible(String game){
        List<String> grabs = Arrays.stream(game.split(";")).toList();

        for(String grab: grabs){
            Matcher cubeMatcher = CUBE_PATTERN.matcher(grab);
            while(cubeMatcher.find()){
                String color = cubeMatcher.group(2);
                int num = Integer.parseInt(cubeMatcher.group(1));

                if(("red".equals(color) && num > 12)
                        || ("green".equals(color) && num > 13)
                        || ("blue".equals(color) && num > 14)) {
                    return Optional.empty();
                }
            }
        }

        Matcher gameMatcher = GAME_ID_PATTERN.matcher(game);
        Integer gameId = gameMatcher.find() ? Integer.parseInt(gameMatcher.group(1)): null;
        Objects.requireNonNull(gameId);
        return Optional.of(gameId);
    }

    public static int challenge2(List<String> games){
        return games.stream()
                .mapToInt(AOCDay2::powerOfMinimumCubesNeeded)
                .sum();
    }

    private static int powerOfMinimumCubesNeeded(String game){
        List<String> grabs = Arrays.stream(game.split(";")).toList();

        int minRed = 0;
        int minGreen = 0;
        int minBlue = 0;

        for(String grab: grabs){
            Matcher cubeMatcher = CUBE_PATTERN.matcher(grab);

            while(cubeMatcher.find()){
                String color = cubeMatcher.group(2);
                int num = Integer.parseInt(cubeMatcher.group(1));

                if("red".equals(color) && num > minRed) minRed = num;
                else if("green".equals(color) && num > minGreen) minGreen = num;
                else if("blue".equals(color) && num > minBlue) minBlue = num;
            }
        }

        return minRed * minGreen * minBlue;
    }

    public static void main(String [] args){
        List<String> games = fileUtils.getFileContents("challenge2.txt").toList();

        int answer1 = challenge1(games);

        logger.info("Answer 1: {}", answer1);

        int answer2 = challenge2(games);

        logger.info("Answer 2: {}", answer2);
    }
}