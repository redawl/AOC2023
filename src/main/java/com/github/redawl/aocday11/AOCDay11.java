package com.github.redawl.aocday11;

import com.github.redawl.util.Coordinate;
import com.github.redawl.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AOCDay11 {
    private static final Logger logger = LoggerFactory.getLogger(AOCDay11.class);

    private static final FileUtils fileUtils = FileUtils.getFileUtils();

    public static int challenge1(List<String> universe){
        List<Integer> yWeights = getYWeights(universe, 2);
        List<Integer> xWeights = getXWeights(universe, 2);

        List<Coordinate> galaxies = new ArrayList<>();
        for(int j = 0; j < universe.size(); j++){
            for(int i = 0; i < universe.get(0).length(); i++){
                if(universe.get(j).charAt(i) == '#'){
                    galaxies.add(Coordinate.of(i, j));
                }
            }
        }

        int total = 0;

        while(!galaxies.isEmpty()){
            Coordinate currGalaxy = galaxies.get(0);
            galaxies.remove(currGalaxy);

            for(Coordinate galaxy: galaxies){
                int xSum = 0;
                int ySum = 0;

                if(!currGalaxy.getX().equals(galaxy.getX())) {
                    if(currGalaxy.getX() < galaxy.getX()) {
                        for (int i = currGalaxy.getX() + 1; i <= galaxy.getX(); i++) {
                            xSum += xWeights.get(i);
                        }
                    } else {
                        for (int i = galaxy.getX() + 1; i <= currGalaxy.getX(); i++) {
                            xSum += xWeights.get(i);
                        }
                    }
                }

                if(!currGalaxy.getY().equals(galaxy.getY())) {
                    if(currGalaxy.getY() < galaxy.getY()) {
                        for (int i = currGalaxy.getY() + 1; i <= galaxy.getY(); i++) {
                            ySum += yWeights.get(i);
                        }
                    } else {
                        for (int i = galaxy.getY() + 1; i <= currGalaxy.getY(); i++) {
                            ySum += yWeights.get(i);
                        }
                    }
                }

                total += xSum + ySum;
            }
        }

        return total;
    }

    private static List<Integer> getXWeights(List<String> galaxies, int weight){
        List<Integer> ret = new ArrayList<>();
        for(int i = 0; i < galaxies.get(0).length(); i++){
            boolean weighted = true;
            for(String galaxy: galaxies){
                if(galaxy.charAt(i) == '#'){
                    weighted = false;
                    break;
                }
            }
            if(weighted){
                ret.add(weight);
            } else {
                ret.add(1);
            }
        }

        return ret;
    }

    private static List<Integer> getYWeights(List<String> galaxies, int weight){
        List<Integer> ret = new ArrayList<>();

        for (String galaxy : galaxies) {
            if (!galaxy.contains("#")) {
                ret.add(weight);
            } else {
                ret.add(1);
            }
        }

        return ret;
    }

    public static long challenge2(List<String> universe){
        List<Integer> yWeights = getYWeights(universe, 1000000);
        List<Integer> xWeights = getXWeights(universe, 1000000);

        List<Coordinate> galaxies = new ArrayList<>();
        for(int j = 0; j < universe.size(); j++){
            for(int i = 0; i < universe.get(0).length(); i++){
                if(universe.get(j).charAt(i) == '#'){
                    galaxies.add(Coordinate.of(i, j));
                }
            }
        }

        long total = 0;

        while(!galaxies.isEmpty()) {
            Coordinate currGalaxy = galaxies.get(0);
            galaxies.remove(currGalaxy);

            for (Coordinate galaxy : galaxies) {
                int xSum = 0;
                int ySum = 0;

                if (!currGalaxy.getX().equals(galaxy.getX())) {
                    if (currGalaxy.getX() < galaxy.getX()) {
                        for (int i = currGalaxy.getX() + 1; i <= galaxy.getX(); i++) {
                            xSum += xWeights.get(i);
                        }
                    } else {
                        for (int i = galaxy.getX() + 1; i <= currGalaxy.getX(); i++) {
                            xSum += xWeights.get(i);
                        }
                    }
                }

                if (!currGalaxy.getY().equals(galaxy.getY())) {
                    if (currGalaxy.getY() < galaxy.getY()) {
                        for (int i = currGalaxy.getY() + 1; i <= galaxy.getY(); i++) {
                            ySum += yWeights.get(i);
                        }
                    } else {
                        for (int i = galaxy.getY() + 1; i <= currGalaxy.getY(); i++) {
                            ySum += yWeights.get(i);
                        }
                    }
                }

                total += (xSum + ySum);
            }
        }

        return total;
    }

    public static void main(String [] args){
        // TODO: Set up initial state
        List<String> universe = fileUtils.getFileContents("challenge11.txt").toList();

        int answer1 = challenge1(universe);

        logger.info("Answer 1: {}", answer1);

        long answer2 = challenge2(universe);

        logger.info("Answer 2: {}", answer2);
    }
}