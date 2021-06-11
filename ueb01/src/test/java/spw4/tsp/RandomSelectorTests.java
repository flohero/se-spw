package spw4.tsp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RandomSelectorTests {

    @DisplayName("RandomSelector.select when solutions null or empty, throws Illegal Argument Exception")
    @ParameterizedTest(name = "solutions = {0}")
    @NullAndEmptySource
    void selectWhenSolutionsNullOrEmptyThrowsIllegalArgumentException(List<Solution<Integer>> solutions) {
        //given
        RandomSelector<Integer> selector = new RandomSelector<>();
        TSPSolver.random = new Random();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            selector.select(solutions, 0);
        });
    }

    @DisplayName("RandomSelector.select when parents smaller 0, throws Illegal Argument Exception")
    @Test()
    void selectWhenParentsSmaller0ThrowsIllegalArgumentException() {
        //given
        RandomSelector<Integer> selector = new RandomSelector<>();
        TSPSolver.random = new Random();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            selector.select(List.of(new Solution<>(1, 1)), -1);
        });
    }


    @DisplayName("RandomSelector.select returns correct number of selected solutions")
    @ParameterizedTest(name = "solutions = {0}, parents = {1}")
    @MethodSource
    void selectReturnsCorrectNumberOfSelectedSolutions(List<Solution<Integer>> solutions, int parents) {
        //given
        RandomSelector<Integer> selector = new RandomSelector<>();
        TSPSolver.random = new Random();

        //when
        List<Solution<Integer>> result = selector.select(solutions, parents);

        //then
        assertEquals(parents, result.size());
    }

    private static Stream<Arguments> selectReturnsCorrectNumberOfSelectedSolutions() {
        List<Solution<Integer>> solutions = List.of(
                new Solution<>(1, 1),
                new Solution<>(2, 0),
                new Solution<>(3, 1),
                new Solution<>(4, 0),
                new Solution<>(5, 1)
        );
        return Stream.of(
                Arguments.of(solutions, 1),
                Arguments.of(solutions, 2),
                Arguments.of(solutions, 0),
                Arguments.of(solutions, 5),
                Arguments.of(solutions, 20),
                Arguments.of(solutions, 100)
        );
    }

    @DisplayName("RandomSelector.select returns List with same elements as in source")
    @ParameterizedTest(name = "solutions = {0}, parents = {1}")
    @MethodSource
    void selectReturnsListWithSameElementsAsInSource(List<Solution<Integer>> solutions, int parents) {
        //given
        RandomSelector<Integer> selector = new RandomSelector<>();
        TSPSolver.random = new Random();

        //when
        List<Solution<Integer>> result = selector.select(solutions, parents);

        //then
        for (Solution<Integer> solution :
                result) {
            assertTrue(solutions.contains(solution));
        }
    }

    private static Stream<Arguments> selectReturnsListWithSameElementsAsInSource() {
        List<Solution<Integer>> solutions = List.of(
                new Solution<>(1, 1),
                new Solution<>(2, 0),
                new Solution<>(3, 1),
                new Solution<>(4, 0),
                new Solution<>(5, 1)
        );
        return Stream.of(
                Arguments.of(solutions, 1),
                Arguments.of(solutions, 2),
                Arguments.of(solutions, 0),
                Arguments.of(solutions, 5),
                Arguments.of(solutions, 20),
                Arguments.of(solutions, 100)
        );
    }

}
