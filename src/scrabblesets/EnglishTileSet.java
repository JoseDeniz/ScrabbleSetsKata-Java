package scrabblesets;

import java.util.HashMap;
import java.util.Map;

class EnglishTileSet implements TileSet {

    private final Map<Character, Long> bag = new HashMap<>();

    EnglishTileSet() {
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

    @Override
    public Map<Character, Long> get() {
        return this.bag;
    }
}
