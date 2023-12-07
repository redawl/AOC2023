package com.github.redawl.aocday7;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay7Tests {
    List<String> challenge1TestData = List.of(
            "32T3K 765",
                    "T55J5 684",
                    "KK677 28",
                    "KTJJT 220",
                    "QQQJA 483"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 6440;
        int actual = AOCDay7.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 5905;
        int actual = AOCDay7.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}