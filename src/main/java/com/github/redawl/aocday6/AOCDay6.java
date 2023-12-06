package com.github.redawl.aocday6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AOCDay6 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay6.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    public static long challenge1(List<String> races){
        List<Long> times = Arrays.stream(races.get(0)
                .split("\\s"))
                .filter(x -> !"Time:".equals(x) && !x.isBlank())
                .map(Long::parseLong)
                .toList();

        List<Long> distances = Arrays.stream(races.get(1).split("\\s"))
                .filter(x -> !"Distance:".equals(x) && !x.isBlank())
                .map(Long::parseLong)
                .toList();

        assert distances.size() == times.size();

        List<Long> numWinnings = new ArrayList<>();

        for(int i = 0; i < distances.size(); i++){
            numWinnings.add(computeNumWinnings(distances.get(i), times.get(i)));
        }

        return numWinnings.stream().reduce(1L, (total, x) -> total * x);
    }

    private static long computeNumWinnings(long distance, long time){
        // scan from the front to find lower bound
        long lowerBound = 0;
        for(long j = 1; j < time; j++){
            if((time - j) * j > distance){
                lowerBound = j;
                break;
            }
        }

        // scan from back to find upper bound
        long upperBound = 0;
        for(long j = time - 1; j > 0; j--){
            if((time - j) * j > distance){
                upperBound = j;
                break;
            }
        }

        return upperBound - lowerBound + 1;
    }

    public static long challenge2(List<String> races){
        long distance = Long.parseLong(races.get(1).replaceAll("\\D", ""));
        long time = Long.parseLong(races.get(0).replaceAll("\\D", ""));

        return computeNumWinnings(distance, time);
    }

    public static void main(String [] args){
        List<String> races = fileUtils.getFileContents("challenge6.txt").toList();

        long start = System.currentTimeMillis();
        long answer1 = challenge1(races);
        long end = System.currentTimeMillis();

        logger.info("Answer1: {} - {} ms", answer1, end - start);

        start = System.currentTimeMillis();
        long answer2 = challenge2(races);
        end = System.currentTimeMillis();

        logger.info("Answer2: {} - {} ms", answer2, end - start);
    }
}