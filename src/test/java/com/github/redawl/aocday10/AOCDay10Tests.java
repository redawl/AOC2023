package com.github.redawl.aocday10;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay10Tests {
    private static final List<String> challenge1TestData = List.of(
            ".....",
                    ".S-7.",
                    ".|.|.",
                    ".L-J.",
                    "....."
    );

    private static final List<String> challenge2TestData = List.of(
            ".F----7F7F7F7F-7....",
                    ".|F--7||||||||FJ....",
                    ".||.FJ||||||||L7....",
                    "FJL7L7LJLJ||LJ.L-7..",
                    "L--J.L7...LJS7F-7L7.",
                    "....F-J..F7FJ|L7L7L7",
                    "....L7.F7||L7|.L7L7|",
                    ".....|FJLJ|FJ|F7|.LJ",
                    "....FJL-7.||.||||...",
                    "....L---J.LJ.LJLJ..."
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 4;
        int actual = AOCDay10.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 8;
        int actual = AOCDay10.challenge2(challenge2TestData);

        Assert.assertEquals(expected, actual);
    }
}