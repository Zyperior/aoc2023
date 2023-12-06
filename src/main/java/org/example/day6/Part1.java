package org.example.day6;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) {
        InputStream inputStream = Part1.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        List<Integer> raceTimes = new ArrayList<>();
        List<Integer> distancesToBeat = new ArrayList<>();
        while (sc.hasNext()) {
            raceTimes = getNumbersFromString(sc.nextLine().split(":")[1]);
            distancesToBeat = getNumbersFromString(sc.nextLine().split(":")[1]);
        }

        List<Integer> successfulRaces = new ArrayList<>();
        for (int i = 0; i < raceTimes.size(); i++) {

            var raceTime = raceTimes.get(i);
            var distanceToBeat = distancesToBeat.get(i);
            var racesWon = 0;

            for (int j = raceTime; j > -1; j--) {
                var boat = new Boat(j, raceTime - j);
                if (boat.distanceMoved() > distanceToBeat) {
                    racesWon++;
                }
            }

            if (racesWon > 0) {
                successfulRaces.add(racesWon);
            }
        }

        var result = 1;
        for (Integer successfulRace : successfulRaces) {
            result *= successfulRace;
        }

        System.err.println(result);
    }

    private static List<Integer> getNumbersFromString(String numberStr) {
        return Arrays.stream(numberStr.split(" "))
                .filter(str -> !str.isBlank())
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }


    private record Boat(int msHeld, int msToMove) {

        public int distanceMoved() {
            return msHeld * msToMove;
        }
    }
}