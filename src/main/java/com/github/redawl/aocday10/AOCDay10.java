package com.github.redawl.aocday10;

import com.github.redawl.util.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.redawl.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class AOCDay10 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay10.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    public static int challenge1(List<String> pipeRows){
        List<char []> pieces = new ArrayList<>();

        for(String pipeRow: pipeRows){
            pieces.add(pipeRow.toCharArray());
        }

        // Find start
        int startX = 0, startY = 0;
        boolean startFound = false;
        for(int i = 0; i < pieces.get(0).length; i++){
            for(int j = 0; j < pieces.size(); j++){
                if(pieces.get(j)[i] == 'S') {
                    startX = i;
                    startY = j;
                    startFound = true;
                    break;
                }
            }
            if(startFound) break;
        }

        AtomicInteger total = new AtomicInteger();

        // Up
        getLoopLength(startX, startY, 0, -1, pieces)
                .ifPresent(length -> total.set(length / 2));

        // Down
        getLoopLength(startX, startY, 0, 1, pieces)
                .ifPresent(length -> total.set(length / 2));

        // Left
        getLoopLength(startX, startY, -1, 0, pieces)
                .ifPresent(length -> total.set(length / 2));

        // Right
        getLoopLength(startX, startY, 1, 0, pieces)
                .ifPresent(length -> total.set(length / 2));

        return total.get();
    }

    private static Optional<Integer> getLoopLength(int startX, int startY, int startMoveX, int startMoveY, List<char []> pieces){
        List<Coordinate> visited = new ArrayList<>();
        Coordinate curr = Coordinate.of(startX + startMoveX, startY + startMoveY);
        Coordinate move = Coordinate.of(startMoveX, startMoveY);
        boolean done = false;
        int count = 1;
        while(!done){
            Coordinate newCoordinates = getNextMove(pieces.get(curr.getY())[curr.getX()], move)
                    .orElse(null);

            if(newCoordinates != null){
                move = newCoordinates;
            } else {
                done = true;
            }

            curr = Coordinate.of(curr.getX() + move.getX(), curr.getY() + move.getY());
            if(visited.contains(curr)){
                return Optional.empty();
            }
            visited.add(curr);

            count++;

            if(curr.getX() < 0 || curr.getX() == pieces.get(0).length || curr.getY() < 0 || curr.getY() == pieces.size()){
                return Optional.empty();
            }

            if(pieces.get(curr.getY())[curr.getX()] == 'S'){
                return Optional.of(count);
            }
        }

        return Optional.empty();
    }

    private static Optional<Coordinate> getNextMove(char piece, Coordinate oldMove){
        switch(piece){
            case '.':
                return Optional.empty();
            case '-':
                if(oldMove.equals(Coordinate.of(0, 1)) || oldMove.equals(Coordinate.of(0, -1))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(1,0))){
                    return Optional.of(Coordinate.of(1, 0));
                }
                return Optional.of(Coordinate.of(-1, 0));
            case 'L':
                if(oldMove.equals(Coordinate.of(1, 0)) || oldMove.equals(Coordinate.of(0, -1))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(0, 1))){
                    return Optional.of(Coordinate.of(1, 0));
                }
                return Optional.of(Coordinate.of(0, -1));
            case 'J':
                if(oldMove.equals(Coordinate.of(0, -1)) || oldMove.equals(Coordinate.of(-1, 0))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(0, 1))){
                    return Optional.of(Coordinate.of(-1, 0));
                }
                return Optional.of(Coordinate.of(0, -1));
            case '7':
                if(oldMove.equals(Coordinate.of(-1, 0)) || oldMove.equals(Coordinate.of(0, 1))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(0, -1))){
                    return Optional.of(Coordinate.of(-1, 0));
                }
                return Optional.of(Coordinate.of(0, 1));
            case 'F':
                if(oldMove.equals(Coordinate.of(1, 0)) || oldMove.equals(Coordinate.of(0, 1))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(-1, 0))){
                    return Optional.of(Coordinate.of(0, 1));
                }
                return Optional.of(Coordinate.of(1, 0));
            case '|':
                if(oldMove.equals(Coordinate.of(1, 0)) || oldMove.equals(Coordinate.of(-1, 0))){
                    return Optional.empty();
                }

                if(oldMove.equals(Coordinate.of(0, -1))){
                    return Optional.of(Coordinate.of(0, -1));
                }
                return Optional.of(Coordinate.of(0, 1));
            default:
                logger.error("Unknown control code {}", piece);
        }

        throw new RuntimeException("Did not match a valid control code");
    }

    public static int challenge2(List<String> pipeRows){
        // TODO: Implement
        List<char []> pieces = new ArrayList<>();

        for(String pipeRow: pipeRows){
            pieces.add(pipeRow.toCharArray());
        }

        // Find start
        int startX = 0, startY = 0;
        boolean startFound = false;
        for(int i = 0; i < pieces.get(0).length; i++){
            for(int j = 0; j < pieces.size(); j++){
                if(pieces.get(j)[i] == 'S') {
                    startX = i;
                    startY = j;
                    startFound = true;
                    break;
                }
            }
            if(startFound) break;
        }

        AtomicReference<List<Coordinate>> list = new AtomicReference<>();
        // Up
        getVisited(startX, startY, 0, -1, pieces)
                .ifPresent(list::set);

        // Down
        getVisited(startX, startY, 0, 1, pieces)
                .ifPresent(list::set);

        // Left
        getVisited(startX, startY, -1, 0, pieces)
                .ifPresent(list::set);

        // Right
        getVisited(startX, startY, 1, 0, pieces)
                .ifPresent(list::set);

        List<Coordinate> visitedCorners = list.get().stream().filter(c -> isCornerPiece(pieces.get(c.getY())[c.getX()])).toList();
        print(list.get(), pieces);
        int numerator = 0;

        for(int i = 0; i < visitedCorners.size(); i++){

            Coordinate first = visitedCorners.get(i);
            Coordinate second = visitedCorners.get(i % visitedCorners.size());
            numerator += (first.getX() * second.getY()) - (first.getY() * second.getX());
        }

        return numerator / 2;
    }

    private static boolean isCornerPiece(char piece){
        return piece == 'L' || piece == 'F' || piece == 'J' || piece == '7';
    }

    private static void print(List<Coordinate> visited, List<char []> pieces){
        for(int j = pieces.size() - 1; j >= 0; j--){
            for(int i = pieces.get(0).length - 1; i >= 0; i--){
                if(visited.contains(Coordinate.of(i, j))){
                    System.out.print(pieces.get(j)[i]);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private static boolean oneIsMultipleOfOther(int x, int y){
        if(x < 0 || y < 0) throw new RuntimeException("x and y cannot be negative");

        if(x == y) return true;

        if(x > y){
            int multiple = y;
            while(x > multiple){
                multiple += y;
            }

            return x == multiple;
        }

        int multiple = x;
        while(y > multiple){
            multiple += x;
        }

        return y == multiple;
    }

    private static Optional<List<Coordinate>> getVisited(int startX, int startY, int startMoveX, int startMoveY, List<char []> pieces){
        List<Coordinate> visited = new ArrayList<>();
        Coordinate curr = Coordinate.of(startX + startMoveX, startY + startMoveY);
        Coordinate move = Coordinate.of(startMoveX, startMoveY);
        boolean done = false;
        while(!done){
            Coordinate newCoordinates = getNextMove(pieces.get(curr.getY())[curr.getX()], move)
                    .orElse(null);

            if(newCoordinates != null){
                move = newCoordinates;
            } else {
                done = true;
            }

            curr = Coordinate.of(curr.getX() + move.getX(), curr.getY() + move.getY());
            if(visited.contains(curr)){
                return Optional.empty();
            }
            visited.add(curr);

            if(curr.getX() < 0 || curr.getX() == pieces.get(0).length || curr.getY() < 0 || curr.getY() == pieces.size()){
                return Optional.empty();
            }

            if(pieces.get(curr.getY())[curr.getX()] == 'S'){
                return Optional.of(visited);
            }
        }

        return Optional.empty();
    }

    public static void main(String [] args){
        // TODO: Set up initial state
        List<String> pipes = fileUtils.getFileContents("challenge10.txt").toList();

        int answer1 = challenge1(pipes);

        logger.info("Answer 1: {}", answer1);
        int answer2 = challenge2(pipes);

        logger.info("Answer 2: {}", answer2);
    }
}