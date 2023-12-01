package org.example.day1;


import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = Main.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        Map<String, Integer> strValues = Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9
        );

        while (sc.hasNext()) {
            String inputLine = sc.nextLine();

            List<Character> list = inputLine.chars()
                    .mapToObj(c -> (char) c)
                    .toList();

            StringBuilder str1 = new StringBuilder();
            Integer nr1 = null;

            for (int i = 0; i < list.size() && nr1 == null; i++) {

                Character ch = list.get(i);

                if (Character.isDigit(ch)) {
                    nr1 = Integer.parseInt(ch.toString());

                } else {
                    str1.append(ch);

                    for (String s : strValues.keySet()) {
                        if (str1.toString().contains(s)) {
                            nr1 = strValues.get(s);
                            break;
                        }
                    }
                }
            }

            StringBuilder str2 = new StringBuilder();
            Integer nr2 = null;

            for (int i = list.size() - 1; i > -1 && nr2 == null; i--) {

                Character ch = list.get(i);

                if (Character.isDigit(ch)) {
                    nr2 = Integer.parseInt(ch.toString());

                } else {
                    str2.insert(0, ch);

                    for (String s : strValues.keySet()) {
                        if (str2.toString().contains(s)) {
                            nr2 = strValues.get(s);
                            break;
                        }
                    }
                }
            }

            String actualNrStr = String.valueOf(nr1) + nr2;
            result += Integer.parseInt(actualNrStr);
        }

        System.err.println(result);
    }

}