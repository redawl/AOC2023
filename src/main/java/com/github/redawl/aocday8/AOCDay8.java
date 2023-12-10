package com.github.redawl.aocday8;

import com.github.redawl.util.TreeNode;
import com.sun.source.tree.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOCDay8 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay8.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final Pattern TREE_PATTERN = Pattern.compile("([0-9A-Z]{3})\\s=\\s\\(([0-9A-Z]{3}),\\s([0-9A-Z]{3})\\)");

    public static int challenge1(List<String> lines){
        String instructions = lines.get(0);

        List<String> mappings = lines.subList(1, lines.size());

        Map<String, MappingData> map = generateMapping(mappings);

        int count = 0;
        String currData = "AAA";
        while(true){
            char c = instructions.charAt(count % instructions.length());
            if(c == 'R'){
                currData = map.get(currData).right;
            } else if (c == 'L'){
                currData = map.get(currData).left;
            } else {
                throw new RuntimeException("Failed to find ZZZ");
            }

            if(currData.equals("ZZZ")){
                break;
            }
            count++;
        }

        return count + 1;
    }

    private static Map<String, MappingData> generateMapping(List<String> mappings){
        Map<String, MappingData> map = new HashMap<>();

        for(String mapping: mappings){
            Matcher matcher = TREE_PATTERN.matcher(mapping);
            if(matcher.find()){
                map.put(matcher.group(1), MappingData.of(matcher.group(2), matcher.group(3)));
            } else {
                throw new RuntimeException("Failed to match: " + mapping);
            }
        }

        return map;
    }

    public static long challenge2(List<String> lines){
        String instructions = lines.get(0);
        List<String> mappings = lines.subList(1, lines.size());

        Map<String, MappingData> map = generateMapping(mappings);


        List<String> startPoints = new ArrayList<>();
        List<String> endPoints = new ArrayList<>();
        for(String mapping: map.keySet()){
            if(mapping.charAt(mapping.length() - 1) == 'A'){
                startPoints.add(mapping);
            } else if(mapping.charAt(mapping.length() - 1) == 'Z'){
                endPoints.add(mapping);
            }
        }

        List<Long> moves = new ArrayList<>();

        for(String startPoint: startPoints){
            Set<VisitedData> visited = new HashSet<>();

            int count = 0;
            String currPoint = startPoint;
            while(true){
                int instructionIndex = count % instructions.length();
                char c = instructions.charAt(instructionIndex);

                if(c == 'R'){
                    currPoint = map.get(currPoint).right;
                } else if(c == 'L'){
                    currPoint = map.get(currPoint).left;
                } else {
                    throw new RuntimeException("What is this??");
                }
                count++;

                if(endPoints.contains(currPoint)){
                    moves.add((long)count);
                    break;
                } else if(visited.contains(VisitedData.of(currPoint, instructionIndex))){
                    break;
                }

                visited.add(VisitedData.of(currPoint, instructionIndex));
            }
        }

        return lcm(moves);
    }

    private static long gcd(long x, long y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

    private static class VisitedData {
        String name;

        Integer instructionPosition;

        private VisitedData(String name, int instructionPosition){
            this.name = name;
            this.instructionPosition = instructionPosition;
        }

        public static VisitedData of(String name, int instructionPosition){
            return new VisitedData(name, instructionPosition);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name + "-" + instructionPosition);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof VisitedData data)) return false;

            return data.name.equals(this.name) && data.instructionPosition.equals(this.instructionPosition);
        }
    }

    private static class MappingData {
        String left;
        String right;

        public static MappingData of(String left, String right){
            MappingData data = new MappingData();
            data.left = left;
            data.right = right;
            return data;
        }
    }

    public static void main(String [] args){
        List<String> lines = fileUtils.getFileContents("challenge8.txt").toList();

        int answer1 = challenge1(lines);

        logger.info("Answer 1: {}", answer1);
        long answer2 = challenge2(lines);

        logger.info("Answer 2: {}", answer2);
    }
}