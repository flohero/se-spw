package spw4.tsp;

import java.util.ArrayList;
import java.util.List;

public class RandomSelector<T> implements Selector<T> {
    public List<Solution<T>> select(List<Solution<T>> solutions, int parents) {
        if ((solutions == null) || (solutions.size() < 1)) throw new IllegalArgumentException("solutions must not be null or empty");
        if (parents < 0) throw new IllegalArgumentException("parents must not be negative");

        var selected = new ArrayList<Solution<T>>(parents);
        for (int i = 0; i < parents; i++)
            selected.add(solutions.get(TSPSolver.random.nextInt(solutions.size())));
        return selected;
    }
}
