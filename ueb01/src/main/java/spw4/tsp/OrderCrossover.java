package spw4.tsp;

public class OrderCrossover implements Crossover<Permutation> {
    public Permutation cross(Permutation parent1, Permutation parent2) {
        if (parent1 == null) throw new IllegalArgumentException("parent1 must not be null");
        if (parent2 == null) throw new IllegalArgumentException("parent2 must not be null");

        int[] tourA = parent1.getValues();
        int[] tourB = parent2.getValues();
        int length = tourA.length;
        int[] newTour = new int[length];
        int breakPoint1, breakPoint2, index;
        boolean[] cityCopied = new boolean[length];

        breakPoint1 = TSPSolver.random.nextInt(length - 1);
        breakPoint2 = breakPoint1 + 1 + TSPSolver.random.nextInt(length - (breakPoint1 + 1));

        // copy part of first tour
        for (int i = breakPoint1; i <= breakPoint2; i++) {
            newTour[i] = tourA[i];
            cityCopied[tourA[i]] = true;
        }

        // copy remaining part of second tour
        index = 0;
        for (int i = 0; i < length; i++) {
            if (index == breakPoint1) {  // skip already copied part
                index = breakPoint2 + 1;
            }
            if (!cityCopied[tourB[i]]) {
                newTour[index] = tourB[i];
                index++;
            }
        }
        return new Permutation(newTour);
    }
}
