package org.example.day5;


import org.apache.commons.lang3.Range;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part2 {

    private static final String SEEDS = "seeds:";
    private static final String SEED_TO_SOIL = "seed-to-soil map:";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer map:";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water map:";
    private static final String WATER_TO_LIGHT = "water-to-light map:";
    private static final String LIGHT_TO_TEMP = "light-to-temperature map:";
    private static final String TEMP_TO_HUMIDITY = "temperature-to-humidity map:";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location map:";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        InputStream inputStream = Part2.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        List<Range<Long>> seedRanges = new ArrayList<>();
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
                List<Long> seedConfigurations = getNumbersFromString(seedNumbersStr);

                seedRanges = generateSeedsFromConfiguration(seedConfigurations);
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

        long maxSeed = seedRanges.stream()
                .mapToLong(Range::getMaximum)
                .max()
                .orElseThrow();

        Long minLocation = null;


        for (long location = 0; location <= maxSeed; location++) {
            var humidity = humidityToLocation.getSource(location);
            var temp = tempToHumidity.getSource(humidity);
            var light = lightToTemp.getSource(temp);
            var water = waterToLight.getSource(light);
            var fertilizer = fertilizerToWater.getSource(water);
            var soil = soilToFertilizer.getSource(fertilizer);
            var seed = seedToSoil.getSource(soil);
            for (Range<Long> seedRange : seedRanges) {
                if (seedRange.contains(seed)) {
                    minLocation = location;
                    break;
                }
            }
            if (minLocation != null) {
                break;
            }
        }

        System.err.println(minLocation);
        System.out.println((System.currentTimeMillis() - start) / 1000);
    }

    private static List<Range<Long>> generateSeedsFromConfiguration(List<Long> seedConfigurations) {
        List<Range<Long>> seeds = new ArrayList<>();
        for (int i = 0; i < seedConfigurations.size() - 2; i++) {
            Long rangeStart = seedConfigurations.get(i++);
            Long rangeLength = seedConfigurations.get(i);
            seeds.add(Range.of(rangeStart, rangeStart + rangeLength));
        }
        return seeds;
    }

    private static Mappings mapNumbers(Scanner sc) {
        List<Mapping> mappings = new ArrayList<>();
        while (sc.hasNext()) {
            String inputLine = sc.nextLine();
            if (inputLine.isBlank()) {
                break;
            }
            List<Long> numbers = getNumbersFromString(inputLine);
            var startingDestination = numbers.get(0);
            var startingSource = numbers.get(1);
            var length = numbers.get(2);
            mappings.add(
                    new Mapping(
                            Range.of(startingDestination, startingDestination + length - 1),
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

        public Long getSource(Long destination) {
            if (mappings == null || mappings.isEmpty()) {
                return destination;
            }

            for (Mapping mapping : mappings) {
                if (mapping.contains(destination)) {
                    return mapping.getSource(destination);
                }
            }

            return destination;
        }
    }

    private record Mapping(Range<Long> range, Long startingSource, Long startingDestination) {

        public boolean contains(Long destination) {
            if (range == null) {
                return false;
            }

            return range.contains(destination);
        }

        public Long getSource(Long destination) {

            if (range == null || !range.contains(destination)) {
                return destination;
            }

            if (startingDestination > startingSource) {
                long diff = startingSource - startingDestination;
                return destination + diff;

            } else {
                long diff = startingDestination - startingSource;
                return destination - diff;
            }

        }
    }

}