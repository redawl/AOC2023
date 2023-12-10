package com.github.redawl.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Coordinate {
    private Integer x = 0;
    private Integer y = 0;

    private static final Map<String, Coordinate> cache = new HashMap<>();

    public static Coordinate of(Integer x, Integer y){
        String hash = x.toString() + "-" + y.toString();
        Coordinate old = cache.get(hash);

        if(old != null) return old;

        Coordinate newCoordinate = new Coordinate();
        newCoordinate.x = x;
        newCoordinate.y = y;

        cache.put(hash, newCoordinate);

        return newCoordinate;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Coordinate coordinate)) return false;

        return x.equals(coordinate.x) && y.equals(coordinate.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x.hashCode(), y.hashCode());
    }
}
