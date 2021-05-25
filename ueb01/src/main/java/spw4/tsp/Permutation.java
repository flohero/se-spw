package spw4.tsp;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Permutation {
    private int[] values;

    public Permutation(int length) {
        if (length <= 1)
            throw new IllegalArgumentException("length of permutation must be larger than 1");

        values = IntStream.range(0, length).toArray();
    }

    public Permutation(int[] values) {
        if ((values == null) || (values.length <= 1)) throw new IllegalArgumentException("number of values must be larger than 1");
        if (!isValidPermutation(values)) throw new IllegalArgumentException("given values are not a valid permutation");

        this.values = values;
    }

    public int[] getValues() {
        return values.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public static Permutation createRandom(int length) {
        if (length <= 1)
            throw new IllegalArgumentException("length of random permutation must be larger than 1");

        int index, temp;
        int[] values = IntStream.range(0, length).toArray();

        // use Knuth Shuffle to create random permutation
        for (int i = 0; i < length - 1; i++) {
            index = i + TSPSolver.random.nextInt(length - i);
            temp = values[i];
            values[i] = values[index];
            values[index] = temp;
        }
        return new Permutation(values);
    }

    private static boolean isValidPermutation(int[] permutation) {
        boolean[] foundValues = new boolean[permutation.length];
        Arrays.fill(foundValues, false);

        for (int i = 0; i < permutation.length; i++) {
            if ((permutation[i] < 0) || (permutation[i] >= permutation.length)) return false;
            if (foundValues[permutation[i]]) return false;
            foundValues[permutation[i]] = true;
        }
        return true;
    }
}
