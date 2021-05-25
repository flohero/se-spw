package spw4.tsp;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;
import static java.lang.System.currentTimeMillis;

public class GA<T> implements Algorithm<T> {
    private Problem<T> problem;
    private int iterations;
    private int populationSize;
    private Selector<T> selector;
    private Crossover<T> crossover;
    private Mutator<T> mutator;
    private double mutationRate;

    protected List<Solution<T>> population;
    protected int currentIteration;
    protected Solution<T> best;
    protected long runtime;

    public GA(Problem<T> problem, int iterations, int populationSize, Selector<T> selector, Crossover<T> crossover, Mutator<T> mutator, double mutationRate) {
        if (problem == null) throw new IllegalArgumentException("problem must not be null");
        if (iterations < 1) throw new IllegalArgumentException("iterations must be larger than 0");
        if (populationSize < 1) throw new IllegalArgumentException("populationSize must be larger than 0");
        if (selector == null) throw new IllegalArgumentException("selector must not be null");
        if (crossover == null) throw new IllegalArgumentException("crossover must not be null");
        if (mutator == null) throw new IllegalArgumentException("mutator must not be null");
        if (!Double.isFinite(mutationRate) || (mutationRate < 0)) throw new IllegalArgumentException("mutationRate must be finite and not negative");

        this.problem = problem;
        this.iterations = iterations;
        this.populationSize = populationSize;
        this.selector = selector;
        this.crossover = crossover;
        this.mutator = mutator;
        this.mutationRate = mutationRate;
        this.runtime = 0;
    }

    public Problem<T> getProblem() { return problem; }
    public void setProblem(Problem<T> problem) {
        if (problem == null) throw new IllegalArgumentException("problem must not be null");
        this.problem = problem;
    }

    public long getRuntimeInMilliseconds() {
        return runtime;
    }

    public Solution<T> execute() {
        long start = currentTimeMillis();
        initialize();
        while (!isTerminated())
            iterate();
        runtime = currentTimeMillis() - start;
        return best;
    }

    protected boolean isTerminated() {
        return (currentIteration >= iterations);
    }

    protected void initialize() {
        currentIteration = 0;
        population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            population.add(problem.createRandomSolution());
        }
        sort(population);
        best = population.get(0);
        currentIteration++;
    }

    protected void iterate() {
        List<Solution<T>> children = createChildren();
        population = replace(population, children);
        sort(population);
        best = population.get(0);
        currentIteration++;
    }

    protected List<Solution<T>> createChildren() {
        List<Solution<T>> parents;
        List<Solution<T>> children = new ArrayList<>(populationSize - 1);

        for (int i = 0; i < populationSize - 1; i++) {
            parents = selector.select(population, 2);
            children.add(createChild(parents));
        }
        return children;
    }

    protected Solution<T> createChild(List<Solution<T>> parents) {
        T child = crossover.cross(parents.get(0).getSolutionData(), parents.get(1).getSolutionData());
        if (TSPSolver.random.nextDouble() < mutationRate) {
            child = mutator.mutate(child);
        }
        return problem.evaluate(child);
    }

    protected List<Solution<T>> replace(List<Solution<T>> population, List<Solution<T>> children) {
        var nextPopulation = new ArrayList<Solution<T>>(populationSize);
        nextPopulation.add(population.get(0));
        nextPopulation.addAll(children);
        return nextPopulation;
    }
}
