package org.example.day4;


import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) {
        InputStream inputStream = Part1.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        while (sc.hasNext()) {
            String inputLine = sc.nextLine();

            var inputSplit = inputLine.split("\\|");
            var winningNumbersStr = inputSplit[0].split(":")[1].trim();
            var myNumbersStr = inputSplit[1];

            List<Integer> winningNumbers = getNumbersFromString(winningNumbersStr);

            List<Integer> myNumbers = getNumbersFromString(myNumbersStr);

            var cardValue = 0;

            for (Integer myNumber : myNumbers) {
                if (winningNumbers.contains(myNumber)) {
                    if (cardValue != 0) {
                        cardValue *= 2;
                    } else {
                        cardValue = 1;
                    }
                }
            }

            result += cardValue;
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

}