package com.github.redawl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;

public class AnswerSubmitter {

    public static final Predicate<Integer> IS_NOT_NOT_NEGATIVE = (x) -> x >= 0;

    private static final Logger logger = LoggerFactory.getLogger(AnswerSubmitter.class);

    private static final Properties properties;
    static{
        properties = new Properties();
        try {
            properties.load(new FileInputStream("/aoc.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AnswerSubmitter (){}

    public enum LEVEL {
        LEVEL_ONE,
        LEVEL_TWO
    }

    public static <T> void submitAnswer(T answer, LEVEL level, Predicate<T> criteria){
        if(criteria.test(answer)){
            submit(answer, level);
        }
    }

    private static <T> void submit(T answer, LEVEL level){
        URI uri = null;

        try {
            uri = new URI("https://adventofcode.com/2023/day/3/answer");
        } catch (URISyntaxException ex){
            logger.error("????");
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(
                    "Cookie", properties.getProperty("answersubmitter.cookie"),
                        "Content-Type", "application/x-www-form-urlencoded"
                )
                .POST(HttpRequest.BodyPublishers.ofString(String.format("level=\"%d\"&answer=%s", level.ordinal() + 1, answer.toString())))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex){
            logger.error("Wtf?");
        }

        Objects.requireNonNull(response);

        if(response.statusCode() == 200 && response.body().contains("That's the right answer!")){
            logger.info("{} is the correct answer!", answer);
        } else {
            logger.error("{} is not the correct answer :(", answer);
            logger.error("Response Code: {}", response.statusCode());
            logger.error("Response Body: {}", response.body());
        }

    }
}
