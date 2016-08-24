package scrabblesets;

import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class ScrabbleSets {
    private final Map<Character, Long> bag;

    ScrabbleSets(Map<Character, Long> bag) {
        this.bag = bag;
    }

    String tilesLeft(String input) {
        Map<Character, Long> inputOccurrences = input
                .chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));

        Map<Character, Long> subtractValues = Stream.of(bag, inputOccurrences)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (val1, val2) -> val1 - val2));

        if (subtractValues.entrySet().stream().anyMatch(e -> e.getValue() < 0)) {
            return "Invalid input. More X's have been taken from the bag than possible.";
        }

        Map<Long, String> collect = subtractValues.entrySet().stream()
                .filter(entrySet -> entrySet.getValue() >= 0)
                .collect(groupingBy(Map.Entry::getValue, mapping(e -> valueOf(e.getKey()), joining(", "))));

        String formatEntries = collect.entrySet().stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(e -> format("%d: %s", e.getKey(), e.getValue()))
                .collect(joining("\n"));

        return formatEntries;
    }
}
