package spw4.tsp;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CyclicCrossoverTests {
    @Test
    @DisplayName("CyclicCrossover.cross when parent1 and parent2 are valid returns valid result")
    void crossWhenParent1AndParent2AreValidReturnsValidResult() {
        TSPSolver.random = new RandomStub(List.of(0));

        var parent1 = new Permutation(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        var parent2 = new Permutation(new int[]{2, 5, 6, 0, 7, 1, 3, 8, 4, 9});

        int[] expected = new int[]{0, 5, 2, 3, 7, 1, 6, 8, 4, 9};

        var sut = new CyclicCrossover();

        int[] result = sut.cross(parent1, parent2).getValues();

        assertArrayEquals(expected, result);
    }
}
