package scrabblesets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class ScrabbleSetsShould {

    private ScrabbleSets scrabbleSets;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
                { "PQAREIOURSTHGWIOAE_", "10: E\n" +
                        "7: A, I\n" +
                        "6: N, O\n" +
                        "5: T\n" +
                        "4: D, L, R\n" +
                        "3: S, U\n" +
                        "2: B, C, F, G, M, V, Y\n" +
                        "1: H, J, K, P, W, X, Z, _\n" +
                        "0: Q" },
                {"LQTOONOEFFJZT", "11: E\n" +
                        "9: A, I\n" +
                        "6: R\n" +
                        "5: N, O\n" +
                        "4: D, S, T, U\n" +
                        "3: G, L\n" +
                        "2: B, C, H, M, P, V, W, Y, _\n" +
                        "1: K, X\n" +
                        "0: F, J, Q, Z"},
                {"AXHDRUIOR_XHJZUQEE", "Invalid input. More X's have been taken from the bag than possible."},
        });
    }

    @Parameterized.Parameter
    public String input;
    @Parameterized.Parameter(value = 1)
    public String expectedOutput;

    @Before
    public void setUp() throws Exception {
        scrabbleSets = new ScrabbleSets(new EnglishTileSet());
    }

    @Test
    public void return_the_remaining_tiles_in_the_bag() {
        assertThat(scrabbleSets.tilesLeft(input), is(expectedOutput));
    }

}
