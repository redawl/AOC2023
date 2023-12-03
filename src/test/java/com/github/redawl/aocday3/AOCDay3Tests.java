package com.github.redawl.aocday3;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AOCDay3Tests {
    private static final List<String> challenge1TestData = List.of(
                    "467..114..",
                    "...*......",
                    "..35..633.",
                    "......#...",
                    "617*......",
                    ".....+.58.",
                    "..592.....",
                    "......755.",
                    "...$.*....",
                    ".664.598.."
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 4361;

        int actual = AOCDay3.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        long expected = 467835;

        long actual = AOCDay3.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}