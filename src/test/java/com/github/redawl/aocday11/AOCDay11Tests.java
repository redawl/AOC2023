package com.github.redawl.aocday11;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay11Tests {
    private static final List<String> challenge1TestData = List.of(
            "...#......",
                    ".......#..",
                    "#.........",
                    "..........",
                    "......#...",
                    ".#........",
                    ".........#",
                    "..........",
                    ".......#..",
                    "#...#....."
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 374;
        int actual = AOCDay11.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        // No test for part 2
    }
}