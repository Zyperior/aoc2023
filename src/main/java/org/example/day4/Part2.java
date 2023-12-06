package org.example.day4;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) {
        InputStream inputStream = Part2.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        List<List<String>> cards = new ArrayList<>();

        while (sc.hasNext()) {
            String inputLine = sc.nextLine();
            List<String> card  = new ArrayList<>();
            card.add(inputLine);
            cards.add(card);
        }

        for (List<String> card : cards) {
            for (String cardStr : card) {

                var cardStrSplit = cardStr.split("\\|");
                var winningNumbersStr = cardStrSplit[0].split(":")[1].trim();
                var myNumbersStr = cardStrSplit[1];

                List<Integer> winningNumbers = getNumbersFromString(winningNumbersStr);

                List<Integer> myNumbers = getNumbersFromString(myNumbersStr);

                var cardValue = 0;

                for (Integer myNumber : myNumbers) {
                    if (winningNumbers.contains(myNumber)) {
                        cardValue++;
                    }
                }

                if(cardValue > 0) {

                    var minIdx = cards.indexOf(card) + 1;
                    var maxIdx = cards.indexOf(card) + cardValue;

                    for (int i = minIdx; i <= maxIdx && i < cards.size(); i++) {
                        cards.get(i).add(cards.get(i).get(0));
                    }
                }
            }

            result = cards.stream()
                    .map(List::size)
                    .reduce(0, Integer::sum);
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