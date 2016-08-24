package scrabblesets;

import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class ScrabbleSets {
    private final Map<Character, Long> bag;

    ScrabbleSets(Map<Character, Long> bag) {
        this.bag = bag;
    }

    String tilesLeft(String input) {
        Map<Character, Long> remainingTiles = mergeMaps(input);
        if (aTileHasTakenMoreThanPossible(remainingTiles)) {
            return errorMessage();
        }
        return formatOutput(invertMap(remainingTiles));
    }

    private Map<Character, Long> mergeMaps(final String input) {
        return Stream.of(bag, groupByFrequency(input))
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (val1, val2) -> val1 - val2));
    }

    private Map<Character, Long> groupByFrequency(final String input) {
        return input.chars()
                    .mapToObj(c -> (char) c)
                    .collect(groupingBy(identity(), counting()));
    }

    private boolean aTileHasTakenMoreThanPossible(Map<Character, Long> remainingTiles) {
        return remainingTiles.entrySet().stream().anyMatch(e -> e.getValue() < 0);
    }

    private String errorMessage() {
        return "Invalid input. More X's have been taken from the bag than possible.";
    }

    private Map<Long, String> invertMap(Map<Character, Long> remainingTiles) {
        return remainingTiles.entrySet().stream()
                .filter(entrySet -> entrySet.getValue() >= 0)
                .collect(groupingBy(Map.Entry::getValue, mapping(e -> e.getKey().toString(), joining(", "))));
    }

    private String formatOutput(Map<Long, String> collect) {
        return collect.entrySet().stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(e -> format("%d: %s", e.getKey(), e.getValue()))
                .collect(joining("\n"));
    }
}
