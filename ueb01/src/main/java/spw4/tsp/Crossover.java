package spw4.tsp;

public interface Crossover<T> {
    T cross(T parent1, T parent2);
}
