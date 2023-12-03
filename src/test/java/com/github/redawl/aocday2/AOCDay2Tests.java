package com.github.redawl.aocday2;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay2Tests {
    private static final List<String> testData = List.of(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                    "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                    "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                    "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                    "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 8;

        int actual = AOCDay2.challenge1(testData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 2286;
        int actual = AOCDay2.challenge2(testData);

        Assert.assertEquals(expected, actual);
    }
}