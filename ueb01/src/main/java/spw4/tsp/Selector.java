package spw4.tsp;

import java.util.List;

public interface Selector<T> {
    List<Solution<T>> select(List<Solution<T>> solutions, int parents);
}
