package scrabblesets;

import java.util.Map;

@FunctionalInterface
interface TileSet {
    Map<Character, Long> get();
}
