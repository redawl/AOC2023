package com.github.redawl.aocday9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AOCDay9 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay9.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    public static int challenge1(List<String> rows){
        List<List<Integer>> numbers = new ArrayList<>();
        for(String row: rows){
            int [] ints = Arrays.stream(row.split("\\s")).mapToInt(Integer::parseInt).toArray();
            numbers.add(new ArrayList<>());
            for(int i: ints){
                numbers.get(numbers.size() - 1) .add(i);
            }
        }


        return getSumOfNextInSequences(numbers);
    }

    public static int challenge2(List<String> rows){
        List<List<Integer>> numbers = new ArrayList<>();
        for(String row: rows){
            int [] ints = Arrays.stream(row.split("\\s")).mapToInt(Integer::parseInt).toArray();
            List<Integer> intList = new ArrayList<>();
            for(int i: ints){
                intList.add(i);
            }
            Collections.reverse(intList);
            numbers.add(intList);
        }

        return getSumOfNextInSequences(numbers);
    }

    private static int getSumOfNextInSequences(List<List<Integer>> numbers){
        List<Integer> totals = new ArrayList<>();

        for(List<Integer> row: numbers) {
            List<List<Integer>> pyramid = new ArrayList<>();
            pyramid.add(row);

            while (!pyramid.get(pyramid.size() - 1).stream().filter(x -> x != 0).toList().isEmpty()) {
                List<Integer> lastRow = pyramid.get(pyramid.size() - 1);
                List<Integer> newRow = new ArrayList<>();
                for (int i = 0; i < lastRow.size() - 1; i++) {
                    newRow.add(lastRow.get(i + 1) - lastRow.get(i));
                }
                pyramid.add(newRow);
            }

            for (int i = pyramid.size() - 1; i > 0; i--) {
                int end = pyramid.get(i).get(pyramid.get(i).size() - 1);
                List<Integer> pyramidRow = pyramid.get(i - 1);
                pyramidRow.add(pyramidRow.get(pyramidRow.size() - 1) + end);
            }

            totals.add(pyramid.get(0).get(pyramid.get(0).size() - 1));
        }

        return totals.stream().reduce(Integer::sum).orElseThrow();
    }

    public static void main(String [] args){
        List<String> rows = fileUtils.getFileContents("challenge9.txt").toList();

        int answer1 = challenge1(rows);

        logger.info("Answer 1: {}", answer1);
        int answer2 = challenge2(rows);

        logger.info("Answer 2: {}", answer2);
    }
}