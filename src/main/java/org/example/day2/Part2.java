package org.example.day2;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) {
        InputStream inputStream = Part2.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        while (sc.hasNext()) {

            HashMap<String, Integer> cubeStacks = new HashMap<>();

            cubeStacks.put("red", null);
            cubeStacks.put("green", null);
            cubeStacks.put("blue", null);

            String inputLine = sc.nextLine();

            result += validateGame(inputLine, cubeStacks);

        }

        System.err.println(result);
    }

    private static int validateGame(String inputLine, Map<String, Integer> cubeStacks) {
        String[] inputLineSplit = inputLine.split(":");

        String gameString = inputLineSplit[1];

        String[] sets = gameString.split(";");

        for (String set : sets) {
            for (String setConfiguration : set.split(",")) {

                String[] setAmount = setConfiguration.trim().split(" ");
                int amount = Integer.parseInt(setAmount[0]);

                for (String color : cubeStacks.keySet()) {
                    if (setAmount[1].contains(color)) {
                        Integer i = cubeStacks.get(color);
                        if (i == null || i < amount) {
                            cubeStacks.put(color, amount);
                        }
                    }
                }
            }
        }

        int result = 0;
        for (Integer value : cubeStacks.values()) {
            if (value != null) {
                if(result == 0) {
                    result = value;
                } else {
                    result *= value;
                }
            }
        }
        return result;
    }

}