package spw4.tsp;

public interface Algorithm<T> {
    void setProblem(Problem<T> problem);

    Problem<T> getProblem();

    long getRuntimeInMilliseconds();

    Solution<T> execute();
}
