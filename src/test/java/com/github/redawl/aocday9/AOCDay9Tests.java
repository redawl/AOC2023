package com.github.redawl.aocday9;

import com.github.redawl.aocday10.AOCDay10;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AOCDay9Tests {
    private static final List<String> challenge1TestData = List.of(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45"
    );

    @Test
    public void exampleCaseShouldPassChallenge1(){
        int expected = 114;

        int actual = AOCDay9.challenge1(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void exampleCaseShouldPassChallenge2(){
        int expected = 2;
        int actual = AOCDay9.challenge2(challenge1TestData);

        Assert.assertEquals(expected, actual);
    }
}