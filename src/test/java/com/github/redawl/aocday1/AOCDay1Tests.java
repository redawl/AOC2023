package com.github.redawl.aocday1;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AOCDay1Tests {

    private static final List<String> challenge1TestData = List.of("1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet");

    private static final List<String> challenge2TestData = List.of("two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen");

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 142;

        int actual = AOCDay1.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 281;

        int actual = AOCDay1.challenge2(challenge2TestData);

        Assert.assertEquals(expected, actual);
    }
}