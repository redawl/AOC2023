package com.github.redawl.aocday10;

import com.github.redawl.util.Coordinate;
import com.github.redawl.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

        int count = 0;
        for(int j = 0; j < pieces.size(); j++){
            for(int i = 0; i < pieces.get(0).length; i++){
                if(!list.get().contains(Coordinate.of(i, j)) && isPointInPolygon(Coordinate.of(i, j), visitedCorners)){
                    count++;
                }
            }
        }


        return count;
    }

    // Adapted from https://stackoverflow.com/a/16391873
    private static boolean isPointInPolygon( Coordinate p, List<Coordinate> polygon )
    {
        double minX = polygon.get(0).getX();
        double maxX = polygon.get(0).getX();
        double minY = polygon.get(0).getY();
        double maxY = polygon.get(0).getY();
        for ( int i = 1 ; i < polygon.size() ; i++ )
        {
            Coordinate q = polygon.get(i);
            minX = Math.min( q.getX(), minX );
            maxX = Math.max( q.getX(), maxX );
            minY = Math.min( q.getY(), minY );
            maxY = Math.max( q.getY(), maxY );
        }

        if ( p.getX() < minX || p.getX() > maxX || p.getY() < minY || p.getY() > maxY )
        {
            return false;
        }

        // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html
        boolean inside = false;
        for ( int i = 0, j = polygon.size() - 1 ; i < polygon.size() ; j = i++ )
        {
            if ( ( polygon.get(i).getY() > p.getY() ) != ( polygon.get(j).getY() > p.getY() ) &&
                    p.getX() < ( polygon.get(j).getX() - polygon.get(i).getX() ) * ( p.getY() - polygon.get(i).getY() ) / ( polygon.get(j).getY() - polygon.get(i).getY() ) + polygon.get(i).getX() )
            {
                inside = !inside;
            }
        }

        return inside;
    }

    private static boolean isCornerPiece(char piece){
        return piece == 'L' || piece == 'F' || piece == 'J' || piece == '7';
    }

    @SuppressWarnings("unused")
    private static void print(List<Coordinate> visited, List<char []> pieces){
        for(int j = 0; j < pieces.size(); j++){
            for(int i = 0; i < pieces.get(0).length; i++){
                if(visited.contains(Coordinate.of(i, j))){
                    System.out.print(pieces.get(j)[i]);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private static Optional<List<Coordinate>> getVisited(int startX, int startY, int startMoveX, int startMoveY, List<char []> pieces){
        List<Coordinate> visited = new ArrayList<>();
        Coordinate curr = Coordinate.of(startX + startMoveX, startY + startMoveY);
        Coordinate move = Coordinate.of(startMoveX, startMoveY);
        visited.add(Coordinate.of(curr.getX(), curr.getY()));
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
        List<String> pipes = fileUtils.getFileContents("challenge10.txt").toList();

        int answer1 = challenge1(pipes);

        logger.info("Answer 1: {}", answer1);
        int answer2 = challenge2(pipes);

        logger.info("Answer 2: {}", answer2);
    }
}