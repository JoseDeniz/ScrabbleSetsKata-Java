package scrabblesets;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScrabbleSetsShould {

    private static final Map<Character, Long> bag = new HashMap<>();
    static {
        bag.put('A', 9L);
        bag.put('B', 2L);
        bag.put('C', 2L);
        bag.put('D', 4L);
        bag.put('E', 12L);
        bag.put('F', 2L);
        bag.put('G', 3L);
        bag.put('H', 2L);
        bag.put('I', 9L);
        bag.put('J', 1L);
        bag.put('K', 1L);
        bag.put('L', 4L);
        bag.put('M', 2L);
        bag.put('N', 6L);
        bag.put('O', 8L);
        bag.put('P', 2L);
        bag.put('Q', 1L);
        bag.put('R', 6L);
        bag.put('S', 4L);
        bag.put('T', 6L);
        bag.put('U', 4L);
        bag.put('V', 2L);
        bag.put('W', 2L);
        bag.put('X', 1L);
        bag.put('Y', 2L);
        bag.put('Z', 1L);
        bag.put('_', 2L);
    }

    @Test
    public void return_the_remaining_tiles_in_the_bag() {
        String expected1 = "10: E\n" +
                           "7: A, I\n" +
                           "6: N, O\n" +
                           "5: T\n" +
                           "4: D, L, R\n" +
                           "3: S, U\n" +
                           "2: B, C, F, G, M, V, Y\n" +
                           "1: H, J, K, P, W, X, Z, _\n" +
                           "0: Q";
        String expected2 = "11: E\n" +
                            "9: A, I\n" +
                            "6: R\n" +
                            "5: N, O\n" +
                            "4: D, S, T, U\n" +
                            "3: G, L\n" +
                            "2: B, C, H, M, P, V, W, Y, _\n" +
                            "1: K, X\n" +
                            "0: F, J, Q, Z";
        String expected3 = "Invalid input. More X's have been taken from the bag than possible.";

        assertThat(tilesLeft("PQAREIOURSTHGWIOAE_"), is(expected1));
        assertThat(tilesLeft("LQTOONOEFFJZT"), is(expected2));
        assertThat(tilesLeft("AXHDRUIOR_XHJZUQEE"), is(expected3));
    }

    private String tilesLeft(String input) {
        Map<Character, Long> inputOccurrences = input
                .chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));

        Map<Character, Long> subtractValues = Stream.of(bag, inputOccurrences)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Entry::getKey, Entry::getValue, (val1, val2) -> val1 - val2));

        if (subtractValues.entrySet().stream().anyMatch(e -> e.getValue() < 0)) {
            return "Invalid input. More X's have been taken from the bag than possible.";
        }

        Map<Long, String> collect = subtractValues.entrySet().stream()
                .filter(entrySet -> entrySet.getValue() >= 0)
                .collect(groupingBy(Entry::getValue, mapping(e -> valueOf(e.getKey()), joining(", "))));

        String formatEntries = collect.entrySet().stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(e -> format("%d: %s", e.getKey(), e.getValue()))
                .collect(joining("\n"));

        return formatEntries;
    }

}
