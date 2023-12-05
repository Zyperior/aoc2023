package org.example.day3;


import java.io.InputStream;
import java.util.*;

public class Part1 {
    public static void main(String[] args) {
        InputStream inputStream = Part1.class.getResourceAsStream("/input.txt");
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

                    boolean shouldCount = false;

                    int minRow = i == 0 ? 0 : i - 1;
                    int maxRow = i == engine.length - 1 ? i : i + 1;

                    int minIdx = j == 0 ? 0 : j - 1;
                    int maxIdx = Math.min(j + currentNumber.length(), engine[minRow].length - 1);

                    for (int k = minRow; k <= maxRow; k++) {
                        for (int l = minIdx; l <= maxIdx; l++) {
                            if(!engine[k][l].equals('.') && !Character.isDigit(engine[k][l])){
                                shouldCount = true;
                            }
                        }
                    }

                    if(shouldCount) {
                        result += Integer.parseInt(currentNumber);
                    }

                    j += currentNumber.length() - 1;
                }
            }
        }
        System.err.println(result);
    }

}