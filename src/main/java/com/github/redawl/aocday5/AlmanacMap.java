package com.github.redawl.aocday5;

import java.util.List;
import java.util.ArrayList;

public class AlmanacMap {

    private final List<Range> seedMapping = new ArrayList<>();

    protected static class Range {
        long destinationStart;
        long destinationEnd;
        long sourceStart;

        long sourceEnd;

        Range(long destinationStart, long destinationEnd, long sourceStart, long sourceEnd){
            this.destinationStart = destinationStart;
            this.destinationEnd = destinationEnd;
            this.sourceStart = sourceStart;
            this.sourceEnd = sourceEnd;
        }
    }

    private AlmanacMap (){}

    public static AlmanacMap getInstance(){
        return new AlmanacMap();
    }

    public void addMapping(long destinationCategory, long sourceCategory, long range){
        seedMapping.add(
                new Range(destinationCategory, destinationCategory + range - 1, sourceCategory, sourceCategory + range - 1));
    }

    public long map(long input){
        for(Range range: seedMapping){
            if(input <= range.sourceEnd && input >= range.sourceStart){
                return range.destinationStart + (input - range.sourceStart);
            }
        }

        return input;
    }

    public void mergeMaps(AlmanacMap incoming){
        List<Range> incomingRanges = incoming.seedMapping;

        while(!incomingRanges.isEmpty()){
            Range incomingRange = incomingRanges.get(0);

            for(Range existingRange: seedMapping){
                // If ranges match up perfectly
                if(existingRange.destinationStart == incomingRange.sourceStart && existingRange.destinationEnd == incomingRange.sourceEnd){
                    existingRange.destinationStart = incomingRange.destinationStart;
                    existingRange.destinationEnd = incomingRange.destinationEnd;
                }
            }

            incomingRanges.remove(incomingRange);
        }
    }

    public long map(long rangeStart, long rangeLength){
        List<Range> validRanges = new ArrayList<>();

        // First find all ranges that overlap with target range
        for(Range range: seedMapping){
            if((rangeStart >= range.sourceStart && rangeStart <= range.sourceEnd)
                || (range.sourceStart >= rangeStart && range.sourceStart <= rangeStart + rangeLength - 1)){
                validRanges.add(range);
            }
        }

        if(validRanges.isEmpty()){
            return rangeStart;
        }

        long min = smallestMapping(rangeStart, rangeLength, validRanges.get(0));

        for(Range validRange: validRanges){
            long newMin = smallestMapping(rangeStart, rangeLength, validRange);
            if(newMin < min) min = newMin;
        }

        return min;
    }

    private long smallestMapping(long rangeStart, long rangeLength, Range targetRange){
        if(targetRange.destinationStart >= rangeStart && targetRange.destinationStart <= rangeStart + rangeLength - 1){
            return targetRange.destinationStart;
        }

        return targetRange.destinationStart + (rangeStart - targetRange.sourceStart);
    }

    private List<Range> getSeedMapping(){
        return seedMapping;
    }
}
