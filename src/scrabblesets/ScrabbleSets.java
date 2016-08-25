package scrabblesets;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.*;

class ScrabbleSets {
    private final TileSet bag;

    ScrabbleSets(TileSet bag) {
        this.bag = bag;
    }

    String tilesLeft(String input) {
        return bag.get().entrySet().stream()
                .map(e -> subtractOccurrences(input, e))
                .collect(collectingAndThen(partitioningBy(this::isTakenMoreThanPossible), checkAndFormatOutput()));
    }

    private Entry<Character, Long> subtractOccurrences(String input, Entry<Character, Long> entry) {
        input.chars().mapToObj(c -> ((char) c)).forEach(c -> {
            if (entry.getKey() == c) {
                entry.setValue(entry.getValue() - 1);
            }
        });
        return entry;
    }

    private Boolean isTakenMoreThanPossible(Entry<Character, Long> e) {
        return e.getValue() < 0;
    }

    private Function<Map<Boolean, List<Entry<Character, Long>>>, String> checkAndFormatOutput() {
        return partitioned -> Optional.ofNullable(checkTakenFrom(partitioned)).orElseGet(formatMap(partitioned));
    }

    private String checkTakenFrom(Map<Boolean, List<Entry<Character, Long>>> partitioned) {
        List<Entry<Character, Long>> moreTaken = partitioned.get(TRUE);
        return !moreTaken.isEmpty()
                ? String.format("Invalid input. More %c's have been taken from the bag than possible.", moreTaken.get(0).getKey())
                : null;
    }

    private Supplier<String> formatMap(Map<Boolean, List<Entry<Character, Long>>> partitioned) {
        return () -> partitioned.get(FALSE).stream()
                .collect(collectingAndThen(groupByValues(), this::formatOutput));
    }

    private Collector<Entry<Character, Long>, ?, Map<Long, String>> groupByValues() {
        return groupingBy(Entry::getValue, mapping(e -> e.getKey().toString(), joining(", ")));
    }

    private String formatOutput(Map<Long, String> map) {
        return map.entrySet()
                .stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(e -> String.format("%d: %s", e.getKey(), e.getValue()))
                .collect(joining("\n"));
    }

}
