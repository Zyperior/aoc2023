package org.example.day5;


import org.apache.commons.lang3.Range;

import java.io.InputStream;
import java.util.*;

public class Part1 {

    private static final String SEEDS = "seeds:";
    private static final String SEED_TO_SOIL = "seed-to-soil map:";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer map:";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water map:";
    private static final String WATER_TO_LIGHT = "water-to-light map:";
    private static final String LIGHT_TO_TEMP = "light-to-temperature map:";
    private static final String TEMP_TO_HUMIDITY = "temperature-to-humidity map:";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location map:";

    public static void main(String[] args) {
        InputStream inputStream = Part1.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        List<Long> seeds = new ArrayList<>();
        Mappings seedToSoil = new Mappings();
        Mappings soilToFertilizer = new Mappings();
        Mappings fertilizerToWater = new Mappings();
        Mappings waterToLight = new Mappings();
        Mappings lightToTemp = new Mappings();
        Mappings tempToHumidity = new Mappings();
        Mappings humidityToLocation = new Mappings();

        while (sc.hasNext()) {
            String inputLine = sc.nextLine();

            if (inputLine.contains(SEEDS)) {
                String seedNumbersStr = inputLine.replace(SEEDS, "");
                seeds = getNumbersFromString(seedNumbersStr);
            } else {
                switch (inputLine) {
                    case SEED_TO_SOIL -> seedToSoil = mapNumbers(sc);
                    case SOIL_TO_FERTILIZER -> soilToFertilizer = mapNumbers(sc);
                    case FERTILIZER_TO_WATER -> fertilizerToWater = mapNumbers(sc);
                    case WATER_TO_LIGHT -> waterToLight = mapNumbers(sc);
                    case LIGHT_TO_TEMP -> lightToTemp = mapNumbers(sc);
                    case TEMP_TO_HUMIDITY -> tempToHumidity = mapNumbers(sc);
                    case HUMIDITY_TO_LOCATION -> humidityToLocation = mapNumbers(sc);
                }
            }
        }
        List<Long> locations = new ArrayList<>();
        for (var seed : seeds) {
            var soil = seedToSoil.getDestination(seed);
            var fertilizer = soilToFertilizer.getDestination(soil);
            var water = fertilizerToWater.getDestination(fertilizer);
            var light = waterToLight.getDestination(water);
            var temp = lightToTemp.getDestination(light);
            var humidity = tempToHumidity.getDestination(temp);
            var location = humidityToLocation.getDestination(humidity);
            locations.add(location);
        }

        System.err.println(
                locations.stream()
                        .mapToLong(l -> l)
                        .min()
                        .orElseThrow()
        );
    }

    private static Mappings mapNumbers(Scanner sc) {
        List<Mapping> mappings = new ArrayList<>();
        while (sc.hasNext()) {
            String inputLine = sc.nextLine();
            if (inputLine.isBlank()) {
                break;
            }
            List<Long> numbers = getNumbersFromString(inputLine);
            var startingSource = numbers.get(1);
            var startingDestination = numbers.get(0);
            var length = numbers.get(2);
            mappings.add(
                    new Mapping(
                            Range.of(startingSource, startingSource + length - 1),
                            startingSource,
                            startingDestination
                    )
            );
        }
        return new Mappings(mappings);
    }

    private static List<Long> getNumbersFromString(String numberStr) {
        return Arrays.stream(numberStr.split(" "))
                .filter(str -> !str.isBlank())
                .map(String::trim)
                .map(Long::valueOf)
                .toList();
    }

    private static class Mappings {
        private List<Mapping> mappings;

        public Mappings() {
        }

        public Mappings(List<Mapping> mappings) {
            this.mappings = mappings;
        }

        public Long getDestination(Long source) {
            if (mappings == null || mappings.isEmpty()) {
                return source;
            }

            for (Mapping mapping : mappings) {
                if(mapping.contains(source)) {
                    return mapping.getDestination(source);
                }
            }

            return source;
        }

    }

    private record Mapping(Range<Long> range, Long startingSource, Long startingDestination) {

        public boolean contains(Long source) {
                if (range == null) {
                    return false;
                }

                return range.contains(source);
            }

            public Long getDestination(Long source) {

                if (range == null || !range.contains(source)) {
                    return source;
                }

                if(startingDestination > startingSource) {
                    long diff = startingDestination - startingSource;
                    return source + diff;

                } else {
                    long diff = startingSource - startingDestination;
                    return source - diff;
                }

            }
        }

}