package com.github.redawl.aocday5;

import java.util.List;
import java.util.ArrayList;

public class AlmanacMap {

    private final List<Range> seedMapping = new ArrayList<>();

    private record Range(long destination, long source, long range) {
    }

    private AlmanacMap (){}

    public static AlmanacMap getInstance(){
        return new AlmanacMap();
    }

    public void addMapping(long destinationCategory, long sourceCategory, long range){
        seedMapping.add(new Range(destinationCategory, sourceCategory, range));
    }

    public long map(long input){
        for(Range range: seedMapping){
            if(input <= range.source + range.range - 1 && input >= range.source){
                return range.destination + (input - range.source);
            }
        }

        return input;
    }

    public long map(long rangeStart, long rangeLength){
        List<Range> validRanges = new ArrayList<>();

        // First find all ranges that overlap with target range
        for(Range range: seedMapping){
            if((rangeStart >= range.source && rangeStart <= range.source + range.range - 1)
                || (range.source >= rangeStart && range.source <= rangeStart + rangeLength - 1)){
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
        if(targetRange.destination >= rangeStart && targetRange.destination <= rangeStart + rangeLength - 1){
            return targetRange.destination;
        }

        return targetRange.destination + (rangeStart - targetRange.source);
    }
}
