package org.example.day6;


import java.io.InputStream;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) {
        InputStream inputStream = Part2.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        long raceTime = 0L;
        long distanceToBeat = 0L;
        while (sc.hasNext()) {
            raceTime = Long.parseLong(
                    String.join(
                            "",
                            sc.nextLine()
                                    .split(":")[1]
                                    .split(" ")
                    )
            );
            distanceToBeat = Long.parseLong(String.join(
                    "",
                    sc.nextLine()
                            .split(":")[1]
                            .split(" ")
            ));
        }

        var racesWon = 0;

        for (long j = raceTime; j > -1; j--) {
            var boat = new Boat(j, raceTime - j);
            if (boat.distanceMoved() > distanceToBeat) {
                racesWon++;
            }
        }

        System.err.println(racesWon);
    }

    private record Boat(long msHeld, long msToMove) {

        public long distanceMoved() {
            return msHeld * msToMove;
        }
    }
}