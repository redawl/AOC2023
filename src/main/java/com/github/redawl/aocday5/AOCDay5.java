package com.github.redawl.aocday5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOCDay5 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay5.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    private static final Pattern SEED_RANGE_PATTERN = Pattern.compile("(\\d+\\s\\d+)");

    public static long challenge1(List<String> maps){
        List<AlmanacMap> almanacMaps = generateAlmanac(maps);

        List<Long> seeds = Arrays.stream(maps.get(0).split("[\\s:]"))
                .filter(s -> !s.contains("seeds") && !s.isBlank())
                .map(Long::parseLong)
                .toList();

        return seeds.stream().mapToLong( seed -> {
            long mutableSeed = seed;

            for (AlmanacMap map : almanacMaps) {
                mutableSeed = map.map(mutableSeed);
            }

            return mutableSeed;
        }).min().orElseThrow();
    }

    private static List<AlmanacMap> generateAlmanac(List<String> maps){
        List<AlmanacMap> almanacMaps = new ArrayList<>();

        maps.subList(1, maps.size())
                .forEach(mapString -> {
                    AlmanacMap newMap = AlmanacMap.getInstance();

                    // List of mappings
                    List<String> mappings = Arrays.stream(mapString.split("\n"))
                            .filter(x -> !x.contains(":"))
                            .toList();

                    mappings.forEach( mapping -> {
                        String [] mappingInits = mapping.split("\\s");

                        newMap.addMapping(Long.parseLong(mappingInits[0]), Long.parseLong(mappingInits[1]), Long.parseLong(mappingInits[2]));
                    });

                    almanacMaps.add(newMap);
                });

        return almanacMaps;
    }

    public static long challenge2(List<String> maps){
        List<AlmanacMap> almanacMaps = generateAlmanac(maps);

        Matcher matcher = SEED_RANGE_PATTERN.matcher(maps.get(0));

        List<String> seedRanges = new ArrayList<>();

        while(matcher.find()){
            seedRanges.add(matcher.group(1));
        }

        long min = -1;

        for(String seedRange: seedRanges){
            String [] split = seedRange.split("\\s");
            long start = Long.parseLong(split[0]);
            long range = Long.parseLong(split[1]);
            for(long i = start; i < start + range; i++){
                long mutable = i;
                for(AlmanacMap map: almanacMaps){
                    mutable = map.map(mutable);
                }

                if(min == -1 || mutable < min) min = mutable;
            }
        }

        return min;
    }

    public static void main(String [] args){
        List<String> maps = fileUtils.getFileContents("challenge5.txt", "\n\n").toList();

        long start = System.currentTimeMillis();
        long answer1 = challenge1(maps);
        long end = System.currentTimeMillis();

        logger.info("Answer 1: {} - {} ms", answer1, end - start);

        start = System.currentTimeMillis();
        long answer2 = challenge2(maps);
        end = System.currentTimeMillis();

        logger.info("Answer 2: {} - {} ms", answer2, end - start);
    }
}