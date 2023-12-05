package org.example.day3;


import java.io.InputStream;
import java.util.*;

public class Part2 {
    public static void main(String[] args) {
        InputStream inputStream = Part2.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        List<String> inputLines = new ArrayList<>();

        while (sc.hasNext()) {
            inputLines.add(sc.nextLine());
        }

        Character[][] engine = new Character[inputLines.size()][inputLines.get(0).length()];

        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine[i].length; j++) {

                engine[i][j] = inputLines.get(i).charAt(j);

            }
        }

        Map<String, List<Integer>> numbersToMultiply = new HashMap<>();

        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine[i].length; j++) {

                String currentNumber = "";

                if (Character.isDigit(engine[i][j])) {
                    currentNumber += engine[i][j].toString();
                    if (j + 1 < engine[i].length && Character.isDigit(engine[i][j + 1])) {
                        currentNumber += engine[i][j + 1];
                        if (j + 2 < engine[i].length && Character.isDigit(engine[i][j + 2])) {
                            currentNumber += engine[i][j + 2];
                        }
                    }
                }

                if (!currentNumber.isBlank()) {

                    int minRow = i == 0 ? 0 : i - 1;
                    int maxRow = i == engine.length - 1 ? i : i + 1;

                    int minIdx = j == 0 ? 0 : j - 1;
                    int maxIdx = Math.min(j + currentNumber.length(), engine[minRow].length - 1);

                    for (int k = minRow; k <= maxRow; k++) {
                        for (int l = minIdx; l <= maxIdx; l++) {
                            var symbol = engine[k][l];
                            if (!symbol.equals('.') && !Character.isDigit(symbol)) {

                                int number = Integer.parseInt(currentNumber);

                                if (symbol.equals('*')) {
                                    String symbolReference = l + "," + k;

                                    if (numbersToMultiply.containsKey(symbolReference)) {
                                        List<Integer> foundNumbers = numbersToMultiply.get(symbolReference);
                                        foundNumbers.add(number);

                                    } else {
                                        List<Integer> newList = new ArrayList<>();
                                        newList.add(number);
                                        numbersToMultiply.put(symbolReference, newList);
                                    }
                                }
                            }
                        }
                    }

                    j += currentNumber.length() - 1;
                }
            }
        }

        result = numbersToMultiply.values().stream()
                .filter(list -> list.size() == 2)
                .map(list -> list.get(0) * list.get(1))
                .reduce(0, Integer::sum);

        System.err.println(result);
    }

}