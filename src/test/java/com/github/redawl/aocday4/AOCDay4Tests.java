package com.github.redawl.aocday4;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay4Tests {
    private static final List<String> challenge1TestData = List.of(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 13;

        int actual = AOCDay4.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 30;

        int actual = AOCDay4.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}