package spw4.tsp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PermutationTests {

    @DisplayName("Permutation.ctor when length > 1 returns valid permutation")
    @ParameterizedTest(name = "length = {0}")
    @ValueSource(ints = {2, 5, 10})
    void ctorWhenLengthIsValidReturnPermutation(int length) {
        int[] expected = IntStream.range(0, length).toArray();

        int[] result = new Permutation(length).getValues();

        assertThat(result).containsExactlyInAnyOrder(expected);
    }

    @DisplayName("Permutation.ctor when length < 2 throws IllegalArgumentException")
    @ParameterizedTest(name = "invalid length = {0}")
    @ValueSource(ints = {0, 1, -1})
    void ctorWhenLengthIsInvalidThrowsIllegalArgumentException(int invalidLength) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Permutation(invalidLength);
        });
    }

    @DisplayName("Permutation.ctor when at least 2 permutation values are valid return permutation")
    @ParameterizedTest(name = "values = {0}")
    @MethodSource
    void ctorWhenValuesAreValidReturnsPermutation(int[] values) {
        int[] result = new Permutation(values).getValues();

        assertThat(result).containsExactlyInAnyOrder(values);
    }


    static Stream<Arguments> ctorWhenValuesAreValidReturnsPermutation() {
        return Stream.of(
                Arguments.of(new int[] { 0, 1 }),
                Arguments.of(new int[] { 2, 1, 0 }),
                Arguments.of(new int[] { 5, 3, 2, 0, 1, 4 })
        );
    }

    @DisplayName("Permutation.ctor when permutation values are invalid throws IllegalArgumentException")
    @ParameterizedTest(name = "invalid values = {0}")
    @NullAndEmptySource
    @MethodSource
    void ctorWhenValuesAreInvalidThrowsException(int[] values) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Permutation(values);
        });
    }
    static Stream<Arguments> ctorWhenValuesAreInvalidThrowsException() {
        return Stream.of(
                Arguments.of(new int[] { 0 }),
                Arguments.of(new int[] { 0, 0 }),
                Arguments.of(new int[] { 0, 1, -1 }),
                Arguments.of(new int[] { 1, 2, 3 }),
                Arguments.of(new int[] { 0, 2, 3 })
        );
    }

    @DisplayName("Permutation.getValues returns a clone of the permutation")
    @Test
    void getValuesReturnsClone() {
        int[] values = IntStream.range(0, 5).toArray();

        int[] result = new Permutation(values).getValues();

        assertNotSame(values, result);
    }

    @DisplayName("Permutation.createRandom when length > 1 returns valid random permutation")
    @Test
    void createRandomWhenLengthIsValidReturnsRandomPermutation() {
        TSPSolver.random = new RandomStub<>(List.of(3, 1, 0, 1));
        int[] expected = new int[] { 3, 2, 1, 4, 0 };

        int[] result = Permutation.createRandom(5).getValues();

        assertArrayEquals(expected, result);
    }

    @DisplayName("Permutation.createRandom when length < 2 throws IllegalArgumentException")
    @ParameterizedTest(name = "invalid values = {0}")
    @ValueSource(ints = {1, 0, -1})
    void createRandomWhenLengthIsInvalidThrowsIllegalArgumentException(int length) {
        assertThrows(IllegalArgumentException.class, () -> {
            Permutation.createRandom(length);
        });
    }

    @DisplayName("Permutation.toString returns correct String")
    @ParameterizedTest(name = "array range = {0}")
    @ValueSource(ints = {2, 4, 6})
    void toStringReturnsCorrectString(int length) {
        //given
        int[] expected = IntStream.range(0, length).toArray();
        Permutation permutation = new Permutation(length);

        //when
        String result = permutation.toString();

        //then
        assertEquals(Arrays.toString(expected), result);
    }

}
