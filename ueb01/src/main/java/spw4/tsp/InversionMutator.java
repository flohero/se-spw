package spw4.tsp;

public class InversionMutator implements Mutator<Permutation> {
    public Permutation mutate(Permutation solution) {
        if (solution == null) throw new IllegalArgumentException("solution must not be null");

        int breakPoint1, breakPoint2;
        int[] tour = solution.getValues();
        int[] newTour = solution.getValues();

        breakPoint1 = TSPSolver.random.nextInt(tour.length - 1);
        breakPoint2 = breakPoint1 + 1 + TSPSolver.random.nextInt(tour.length - (breakPoint1 + 1));

        // inverse tour between breakpoints
        for (int i = 0; i <= (breakPoint2 - breakPoint1); i++) {
            newTour[breakPoint1 + i] = tour[breakPoint2 - i];
        }
        return new Permutation(newTour);
    }
}
