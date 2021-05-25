package spw4.tsp;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelector<T> implements Selector<T> {
    public List<Solution<T>> select(List<Solution<T>> solutions, int parents) {
        if ((solutions == null) || (solutions.size() < 1)) throw new IllegalArgumentException("solutions must not be null or empty");
        if (parents < 0) throw new IllegalArgumentException("parents must not be negative");

        int index1, index2;
        var selected = new ArrayList<Solution<T>>(parents);

        for (int i = 0; i < parents; i++) {
            index1 = TSPSolver.random.nextInt(solutions.size());
            index2 = TSPSolver.random.nextInt(solutions.size());
            if (solutions.get(index1).compareTo(solutions.get(index2)) < 0)
                selected.add(solutions.get(index1));
            else
                selected.add(solutions.get(index2));
        }
        return selected;
    }
}
