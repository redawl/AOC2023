package com.github.redawl.aocday6;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay6Tests {
    private static final List<String> challenge1TestData = List.of(
        "Time:      7  15   30",
                "Distance:  9  40  200"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        long expected = 288;

        long actual = AOCDay6.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        long expected = 71503;

        long actual = AOCDay6.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}