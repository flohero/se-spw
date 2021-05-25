package spw4.tsp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InversionMutatorTests {

    @DisplayName("InversionMutator.mutate when solution null throws Illegal Argmuent Exception")
    @Test
    void mutateWhenSolutionNullThrowsIllegalArgumentException() {
        //given
        InversionMutator mutator = new InversionMutator();
        assertThrows(IllegalArgumentException.class, () -> {
            mutator.mutate(null);
        });
    }

    @DisplayName("InversionMutator.mutate correctly inverses whole solution")
    @ParameterizedTest
    @MethodSource
    void mutateCorrectlyInversesWholeSolution(Permutation permutation, int[] expected,
                                              int lowerLimit, int upperLimit) {
        //given
        TSPSolver.random = new RandomStub<>(List.of(lowerLimit, upperLimit));
        int[] data = new int[]{0, 4, 3, 2, 1};
        InversionMutator mutator = new InversionMutator();

        //when
        Permutation result = mutator.mutate(permutation);

        //then
        assertThat(result.getValues()).isEqualTo(expected);
    }

    private static Stream<Arguments> mutateCorrectlyInversesWholeSolution() {
        final Permutation permutation = new Permutation(new int[]{0, 1, 2, 3});
        return Stream.of(
                Arguments.of(permutation, new int[]{1, 0, 2, 3}, 0, 0),
                Arguments.of(permutation, new int[]{2, 1, 0, 3}, 0, 1),
                Arguments.of(permutation, new int[]{3, 2, 1, 0}, 0, 2)
        );
    }

    @DisplayName("InversionMutator.mutate when permutation small does not throw")
    @Test
    void mutateWhenPermutationSmallDoesNotThrow() {
        //given
        TSPSolver.random = new Random();
        int[] data = new int[]{1, 0};
        Permutation permutation = new Permutation(data);
        InversionMutator mutator = new InversionMutator();

        //when
        assertDoesNotThrow(() -> mutator.mutate(permutation));
    }


}
