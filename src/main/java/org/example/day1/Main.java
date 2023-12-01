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

    private static boolean isCommand(String[] command) {
        return command[0].equals("$");
    }

    private static boolean checkDuplicateLetters(String substring) {
        LinkedList<Character> list = substring.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));
        while (list.size() > 1) {
            Character pop = list.pop();
            for (Character c : list) {
                if (c.equals(pop)) return true;
            }
        }
        return false;
    }

    private static int calculatePriority(char item) {
        return switch (item) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            case 'i' -> 9;
            case 'j' -> 10;
            case 'k' -> 11;
            case 'l' -> 12;
            case 'm' -> 13;
            case 'n' -> 14;
            case 'o' -> 15;
            case 'p' -> 16;
            case 'q' -> 17;
            case 'r' -> 18;
            case 's' -> 19;
            case 't' -> 20;
            case 'u' -> 21;
            case 'v' -> 22;
            case 'w' -> 23;
            case 'x' -> 24;
            case 'y' -> 25;
            case 'z' -> 26;
            case 'A' -> 27;
            case 'B' -> 28;
            case 'C' -> 29;
            case 'D' -> 30;
            case 'E' -> 31;
            case 'F' -> 32;
            case 'G' -> 33;
            case 'H' -> 34;
            case 'I' -> 35;
            case 'J' -> 36;
            case 'K' -> 37;
            case 'L' -> 38;
            case 'M' -> 39;
            case 'N' -> 40;
            case 'O' -> 41;
            case 'P' -> 42;
            case 'Q' -> 43;
            case 'R' -> 44;
            case 'S' -> 45;
            case 'T' -> 46;
            case 'U' -> 47;
            case 'V' -> 48;
            case 'W' -> 49;
            case 'X' -> 50;
            case 'Y' -> 51;
            case 'Z' -> 52;
            default -> throw new RuntimeException("Invalid input letter");
        };
    }
}