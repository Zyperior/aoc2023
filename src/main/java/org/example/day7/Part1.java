package org.example.day7;


import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) {
        InputStream inputStream = Part1.class.getResourceAsStream("/input.txt");
        if (inputStream == null) throw new RuntimeException("Invalid file path");
        Scanner sc = new Scanner(inputStream);

        int result = 0;

        SortedSet<Player> players = new TreeSet<>();

        while (sc.hasNext()) {
            var inputLine = sc.nextLine();
            String[] inputLineSplit = inputLine.split(" ");
            players.add(new Player(inputLineSplit[0], Integer.parseInt(inputLineSplit[1])));
        }


        int places = players.size();
        for (int i = 1; i <= places; i++) {
            result += players.removeFirst().getBet() * i;
        }

        System.err.println(result);
    }

    public static class Player implements Comparable<Player> {

        private final String dealtHand;
        private final int handValue;
        private final int bet;

        public Player(String dealtHand, int bet) {
            this.dealtHand = dealtHand;
            this.handValue = HandValues.get(dealtHand);
            this.bet = bet;
        }

        public String getDealtHand() {
            return dealtHand;
        }

        public int getHandValue() {
            return handValue;
        }

        public int getBet() {
            return bet;
        }

        @Override
        public int compareTo(Player other) {
            int comparedHandValue = Integer.compare(this.handValue, other.getHandValue());

            if (comparedHandValue == 0) {
                for (int i = 0; i < 5; i++) {
                    int cardValue = HandValues.CARD_VALUE_MAP.get(this.dealtHand.charAt(i));
                    int otherCardValue = HandValues.CARD_VALUE_MAP.get(other.getDealtHand().charAt(i));
                    if (cardValue > otherCardValue) {
                        return 1;
                    } else if (cardValue < otherCardValue) {
                        return -1;
                    }
                }
            }
            return comparedHandValue;
        }

    }

    public static class HandValues {

        public static final Pattern FIVE_OF_A_KIND = Pattern.compile("^(?=.{5}$).*?((?<a>.)(?:.*\\k<a>){4})");
        public static final Pattern FOUR_OF_A_KIND = Pattern.compile("^(?=.{5}$).*?((?<a>.)(?:.*\\k<a>){3}.*)");
        public static final Pattern FULL_HOUSE = Pattern.compile("^(?=.{5}$).*?((?=.*(?<fhcard1>.)(?=(?:.*\\k<fhcard1>){2}))(?=.*(?<fhcard2>(?!\\k<fhcard1>).)(?=.*\\k<fhcard2>)).+)");
        public static final Pattern THREE_OF_A_KIND = Pattern.compile("^(?=.{5}$).*?((?<a>.)(?:.*\\k<a>){2}.*)");
        public static final Pattern TWO_PAIR = Pattern.compile("^(?=.{5}$).*?((?=.*(?<tpcard1>.).*(?=\\k<tpcard1>))(?=.*(?<tpcard2>(?!\\k<tpcard1>).).*(?=.*\\k<tpcard2>)).+)");
        public static final Pattern PAIR = Pattern.compile("^(?=.{5}$).*?(?<tpcard1>.).*\\k<tpcard1>.*");

        public static final Map<Character, Integer> CARD_VALUE_MAP = createCardValueMap();


        private static Map<Character, Integer> createCardValueMap() {
            Map<Character, Integer> cardValues = new HashMap<>();
            cardValues.put('2', 0);
            cardValues.put('3', 1);
            cardValues.put('4', 2);
            cardValues.put('5', 3);
            cardValues.put('6', 4);
            cardValues.put('7', 5);
            cardValues.put('8', 6);
            cardValues.put('9', 7);
            cardValues.put('T', 8);
            cardValues.put('J', 9);
            cardValues.put('Q', 10);
            cardValues.put('K', 11);
            cardValues.put('A', 12);
            return cardValues;
        }

        public static int get(String hand) {
            return FIVE_OF_A_KIND.matcher(hand).matches() ? 6 :
                    FOUR_OF_A_KIND.matcher(hand).matches() ? 5 :
                            FULL_HOUSE.matcher(hand).matches() ? 4 :
                                    THREE_OF_A_KIND.matcher(hand).matches() ? 3 :
                                            TWO_PAIR.matcher(hand).matches() ? 2 :
                                                    PAIR.matcher(hand).matches() ? 1 : 0;
        }
    }
}