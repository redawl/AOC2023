package com.github.redawl.aocday8;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay8Tests {
    private static final List<String> challenge1TestData = List.of(
                    "RL",
                    "AAA = (BBB, CCC)",
                    "BBB = (DDD, EEE)",
                    "CCC = (ZZZ, GGG)",
                    "DDD = (DDD, DDD)",
                    "EEE = (EEE, EEE)",
                    "GGG = (GGG, GGG)",
                    "ZZZ = (ZZZ, ZZZ)"
    );

    private static final List<String> challenge2TestData = List.of(
      "LR",
              "11A = (11B, XXX)",
              "11B = (XXX, 11Z)",
              "11Z = (11B, XXX)",
              "22A = (22B, XXX)",
              "22B = (22C, 22C)",
              "22C = (22Z, 22Z)",
              "22Z = (22B, 22B)",
              "XXX = (XXX, XXX)"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 2;
        int actual = AOCDay8.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        long expected = 6;
        long actual = AOCDay8.challenge2(challenge2TestData);

        Assert.assertEquals(expected, actual);
    }
}