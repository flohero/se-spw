package spw4.tsp;

public interface Problem<T> {
    double getBestKnownQuality();

    Solution<T> createRandomSolution();

    Solution<T> evaluate(T solutionData);
}
