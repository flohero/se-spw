package spw4.tsp;

import org.assertj.core.api.Assert;
import org.assertj.core.condition.AllOf;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionTests {

    @DisplayName("Solution.ctor when solutionData null throws Illegal Argument Exception")
    @Test
    void ctorWhenSolutionDataNullThrowsIllegalArgumentException() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            new Solution<>(null, 0);
        });
    }

    @DisplayName("Solution.ctor when quality not finite throws Illegal Argument Exception")
    @ParameterizedTest(name = "value = {0}")
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void ctorWhenQualityThrowsIllegalArgumentException(double value) {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            new Solution<>(new Object(), value);
        });
    }

    @DisplayName("Solution.ctor when solutionData not null is valid")
    @ParameterizedTest(name = "solutionData = '{0}'")
    @ValueSource(strings = {"Hello", "world", "1", "\n", ""})
    void ctorWhenSolutionDataNotNullIsValid(String solutionData) {
        //given
        String expected = solutionData;
        double quality = 0;

        //when
        Solution<String> solution = new Solution<>(solutionData, quality);

        //then
        assertEquals(expected, solution.getSolutionData());
    }


    @DisplayName("Solution.ctor when solutionData not null is valid")
    @ParameterizedTest(name = "quality = {0}")
    @ValueSource(doubles = {10, 0, -10, Double.MIN_VALUE, Double.MAX_VALUE})
    void ctorWhenQualityNotInfiniteIsValid(double quality) {
        //given
        double expected = quality;
        Object solutionData = new Object();

        //when
        Solution<Object> solution = new Solution<>(solutionData, quality);

        //then
        assertEquals(expected, solution.getQuality());
    }

    @DisplayName("Solution.compareTo left solution is smaller or greater when smaller/greater quality")
    @ParameterizedTest(name = "quality1 = {0}, quality2 = {1}, expected = {2}")
    @MethodSource
    void compareToReturnsFalseWhenDifferentQualities(double quality1, double quality2, int expected) {
        //given
        Object solutionData = new Object();
        Solution<Object> solution1 = new Solution<>(solutionData, quality1);
        Solution<Object> solution2 = new Solution<>(solutionData, quality2);

        //when
        int result = solution1.compareTo(solution2);

        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> compareToReturnsFalseWhenDifferentQualities() {
        return Stream.of(
                Arguments.of(0, 1, -1),
                Arguments.of(1, 0, 1),
                Arguments.of(-10, 10, -1),
                Arguments.of(10, -10, 1),
                Arguments.of(0.1, 0.2, -1),
                Arguments.of(0.2, 0.1, 1),
                Arguments.of(Double.MIN_VALUE, Double.MAX_VALUE, -1),
                Arguments.of(Double.MAX_VALUE, Double.MIN_VALUE, 1)
        );
    }

    @DisplayName("Solution.compareTo returns 0 when quality equal")
    @ParameterizedTest(name = "solutionData1 = {0}, solutionData2 = {1}, quality = {2}")
    @MethodSource
    void compareToReturns0WhenSolutionDataDifferentAndQualityEqual(String solutionData1, String solutionData2, double quality) {
        //given
        Solution<String> solution1 = new Solution<>(solutionData1, quality);
        Solution<String> solution2 = new Solution<>(solutionData2, quality);

        //when
        int result = solution1.compareTo(solution2);

        //then
        assertEquals(0, result);
    }

    private static Stream<Arguments> compareToReturns0WhenSolutionDataDifferentAndQualityEqual() {
        return Stream.of(
                Arguments.of("test", "test", 0),
                Arguments.of("test1", "test2", -1),
                Arguments.of("", "test", 10),
                Arguments.of("test", "", 0.1)
        );
    }

    @DisplayName("Solution.toString returns correct format")
    @ParameterizedTest(name = "quality = {0}, solutionData = {1}")
    @MethodSource
    void toStringReturnsCorrectFormat(double quality, String qualityString, String solutionData) {
        //given
        Solution<String> solution = new Solution(solutionData, quality);

        //when
        String result = solution.toString();

        //then

        assertThat(result, CoreMatchers.allOf(
                containsString(qualityString),
                containsString(solutionData.toString())));
    }

    private static Stream<Arguments> toStringReturnsCorrectFormat() {
        return Stream.of(
                Arguments.of(0.1, "0.1", "Hello"),
                Arguments.of(1, "1", "Test"),
                Arguments.of(-10, "-10", "Test"),
                Arguments.of(0, "0", "Test"),
                Arguments.of(0, "0", "\n"),
                Arguments.of(-10.01, "-10.01", "Test"),
                Arguments.of(0.00001, "0.000", "Test")
        );
    }

}
