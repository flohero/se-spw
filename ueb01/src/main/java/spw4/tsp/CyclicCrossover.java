package spw4.tsp;

public class CyclicCrossover implements Crossover<Permutation> {
    public Permutation cross(Permutation parent1, Permutation parent2) {
        if (parent1 == null) throw new IllegalArgumentException("parent1 must not be null");
        if (parent2 == null) throw new IllegalArgumentException("parent2 must not be null");

        int[] tourA = parent1.getValues();
        int[] tourB = parent2.getValues();
        int length = tourA.length;
        int[] newTour = new int[length];
        int index, city;
        boolean[] indexCopied = new boolean[length];

        // copy whole cycle to new tour
        index = TSPSolver.random.nextInt(length);
        while (!indexCopied[index]) {
            newTour[index] = tourA[index];
            city = tourB[index];
            indexCopied[index] = true;

            // search copied city in second tour
            index = 0;
            while (tourA[index] != city) {
                index++;
            }
        }

        // copy rest of second route to new route
        for (int i = 0; i < length; i++) {
            if (!indexCopied[i]) {
                newTour[i] = tourB[i];
            }
        }
        return new Permutation(newTour);
    }
}
