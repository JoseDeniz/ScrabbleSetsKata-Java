package scrabblesets;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class ScrabbleSets {
    private final TileSet bag;

    ScrabbleSets(TileSet bag) {
        this.bag = bag;
    }

    String tilesLeft(String input) {
        Map<Character, Long> remainingTiles = mergeMaps(groupByFrequency(input));
        if (aTileHasTakenMoreThanPossible(remainingTiles)) {
            return errorMessage(remainingTiles);
        }
        return formatOutput(invertMap(remainingTiles));
    }

    private Map<Character, Long> mergeMaps(Map<Character, Long> characterFrequencyMap) {
        return Stream.of(bag.get(), characterFrequencyMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Entry::getKey, Entry::getValue, (val1, val2) -> val1 - val2));
    }

    private Map<Character, Long> groupByFrequency(final String input) {
        return input.chars()
                    .mapToObj(c -> (char) c)
                    .collect(groupingBy(identity(), counting()));
    }

    private boolean aTileHasTakenMoreThanPossible(Map<Character, Long> remainingTiles) {
        return remainingTiles.entrySet().stream().anyMatch(e -> e.getValue() < 0);
    }

    private String errorMessage(Map<Character, Long> remainingTiles) {
        Entry<Character, Long> tile = remainingTiles.entrySet().stream().filter(e -> e.getValue() < 0).findFirst().get();
        return format("Invalid input. More %c's have been taken from the bag than possible.", tile.getKey());
    }

    private Map<Long, String> invertMap(Map<Character, Long> remainingTiles) {
        return remainingTiles.entrySet().stream()
                .filter(entrySet -> entrySet.getValue() >= 0)
                .collect(groupingBy(Entry::getValue, mapping(e -> e.getKey().toString(), joining(", "))));
    }

    private String formatOutput(Map<Long, String> collect) {
        return collect.entrySet().stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(e -> format("%d: %s", e.getKey(), e.getValue()))
                .collect(joining("\n"));
    }
}
