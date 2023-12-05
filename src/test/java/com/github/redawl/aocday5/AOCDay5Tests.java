package com.github.redawl.aocday5;

import com.github.redawl.aocday4.AOCDay4;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay5Tests {
    List<String> challenge1TestData = List.of(
            "seeds: 79 14 55 13",
                    "seed-to-soil map:\n" +
                    "50 98 2\n" +
                    "52 50 48",
                    "soil-to-fertilizer map:\n" +
                    "0 15 37\n" +
                    "37 52 2\n" +
                    "39 0 15",
                    "fertilizer-to-water map:\n" +
                    "49 53 8\n" +
                    "0 11 42\n" +
                    "42 0 7\n" +
                    "57 7 4",
                    "water-to-light map:\n" +
                    "88 18 7\n" +
                    "18 25 70",
                    "light-to-temperature map:\n" +
                    "45 77 23\n" +
                    "81 45 19\n" +
                    "68 64 13",
                    "temperature-to-humidity map:\n" +
                    "0 69 1\n" +
                    "1 0 69",
                    "humidity-to-location map:\n" +
                    "60 56 37\n" +
                    "56 93 4\n"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        long expected = 35;

        long actual = AOCDay5.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        long expected = 46;

        long actual = AOCDay5.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}