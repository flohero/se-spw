package spw4.tsp;

public class Solution<T> implements Comparable<Solution<T>> {
    private T solutionData;
    private double quality;

    public Solution(T solutionData, double quality) {
        if (solutionData == null) throw new IllegalArgumentException("data must not be null");
        if (!Double.isFinite(quality)) throw new IllegalArgumentException("quality must be a valid number");

        this.solutionData = solutionData;
        this.quality = quality;
    }

    public T getSolutionData() {
        return solutionData;
    }
    double getQuality() {
        return quality;
    }

    public int compareTo(Solution<T> other) {
        if (this.quality < other.getQuality())
            return -1;
        else if (this.quality > other.getQuality())
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return String.format("%.3f", quality) + "   " + solutionData.toString();
    }
}
