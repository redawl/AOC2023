package com.github.redawl.aocday3;

import com.github.redawl.util.AnswerSubmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOCDay3 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay3.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final Pattern LINE_PATTERN = Pattern.compile("(\\d+)");

    private static final Pattern INT_BEFORE = Pattern.compile("(\\d+)$");
    private static final Pattern INT_AFTER = Pattern.compile("^(\\d+)");

    private static final char BAD_CHAR = '.';

    public static int challenge1(List<String> strings){
        int finalScore = 0;

        // First check for easy wins
        for(int i = 0; i < strings.size(); i++){
            String line = strings.get(i);
            Matcher lineMatcher = LINE_PATTERN.matcher(line);
            while(lineMatcher.find()){
                String match = lineMatcher.group(1);
                int beginning = lineMatcher.start(1);
                int end = lineMatcher.end(1) - 1;
                if((beginning != 0 && line.charAt(beginning - 1) != BAD_CHAR) || (end < line.length() - 1 && line.charAt(end + 1) != BAD_CHAR)){
                    finalScore += Integer.parseInt(match);
                } else {
                    boolean found = false;
                    int subBeginning = beginning == 0 ? 0 : beginning - 1;
                    int subEnd = end < line.length() - 1 ? end  + 2 : end + 1;
                    if(i != 0){
                        String substring = strings.get(i - 1).substring(subBeginning, subEnd);
                        for(char c: substring.toCharArray()){
                            if (c != BAD_CHAR) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if(!found && i + 1 < strings.size()){
                        String substring = strings.get(i + 1).substring(subBeginning, subEnd);
                        for(char c: substring.toCharArray()){
                            if (c != BAD_CHAR) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if(found){
                        finalScore += Integer.parseInt(match);
                    }

                }
            }
        }


        return finalScore;
    }

    public static long challenge2(List<String> strings){
        long finalScore = 0;

        for(int lineIndex = 0; lineIndex < strings.size(); lineIndex++){
            String line = strings.get(lineIndex);

            for(int charIndex = 0; charIndex < line.length(); charIndex++){
                char c = line.charAt(charIndex);
                if(c == '*'){
                    List<Integer> ints = new ArrayList<>();

                    // First check before and after
                    Matcher beforeMatcher = INT_BEFORE.matcher(line.substring(0, charIndex));
                    if(beforeMatcher.find()){
                        ints.add(Integer.parseInt(beforeMatcher.group(1)));
                    }

                    Matcher afterMatcher = INT_AFTER.matcher(line.substring(charIndex + 1));
                    if(afterMatcher.find()){
                        ints.add(Integer.parseInt(afterMatcher.group(1)));
                    }

                    // Now the rest
                    int startRange = charIndex - 1;
                    int endRange = charIndex + 1;
                    if(lineIndex != 0){
                        String previousLine = strings.get(lineIndex - 1);
                        Matcher lineMatcher = LINE_PATTERN.matcher(previousLine);
                        while(lineMatcher.find()){
                            if((lineMatcher.start(1) >= startRange && lineMatcher.start(1) <= endRange)
                                    || (lineMatcher.end(1) - 1 >= startRange && lineMatcher.end(1) - 1 <= endRange)){
                                ints.add(Integer.parseInt(lineMatcher.group(1)));
                            }
                        }
                    }

                    if(lineIndex + 1 < strings.size()){
                        String nextLine = strings.get(lineIndex + 1);
                        Matcher lineMatcher = LINE_PATTERN.matcher(nextLine);
                        while(lineMatcher.find()){
                            if((lineMatcher.start(1) >= startRange && lineMatcher.start(1) <= endRange)
                                    || (lineMatcher.end(1) - 1 >= startRange && lineMatcher.end(1) - 1 <= endRange)){
                                ints.add(Integer.parseInt(lineMatcher.group(1)));
                            }
                        }
                    }

                    if(ints.size() == 2){
                        finalScore += ((long) ints.get(0)) * ((long)ints.get(1));
                    }
                }
            }
        }

        return finalScore;
    }

    public static void main(String [] args){
        List<String> strings = fileUtils.getFileContents("challenge3.txt").toList();

        int answer1 = challenge1(strings);

        logger.info("Answer 1: {}", answer1);

        long answer2 = challenge2(strings);

        logger.info("Answer 2: {}", answer2);
    }
}