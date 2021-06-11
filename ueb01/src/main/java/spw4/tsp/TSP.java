package spw4.tsp;

public class TSP implements Problem<Permutation> {
    private int cities;
    private double[][] vertices;
    private double bestKnownQuality;

    public TSP(String filename, double bestKnownQuality) throws Exception {
        if ((filename == null) || filename.isEmpty()) throw new IllegalArgumentException("filename must not be null or empty");

        try {
            TSPLibParser parser = new TSPLibParser(filename);
            parser.parse();
            vertices = parser.getVertices();
            cities = vertices.length;
            this.bestKnownQuality = bestKnownQuality;
        } catch (Exception e) {
            throw new Exception("error creating TSP instance: " + e.getMessage());
        }
    }

    public int getCities() {
        return cities;
    }
    public double getBestKnownQuality() {
        return bestKnownQuality;
    }

    public Solution<Permutation> createRandomSolution() {
        return evaluate(Permutation.createRandom(cities));
    }

    public Solution<Permutation> evaluate(Permutation solutionData) {
        if (solutionData == null) throw new IllegalArgumentException("solutionData must not be null");

        int[] tour = solutionData.getValues();
        double quality = 0;
        for (int i = 1; i < tour.length; i++) {
            quality += getDistance(tour[i - 1], tour[i]);
        }
        quality += getDistance(tour[tour.length - 1], tour[0]);
        return new Solution<>(solutionData, quality);
    }

    private double getDistance(int cityA, int cityB) {
        double x1, y1, x2, y2, deltaX, deltaY, length;

        x1 = vertices[cityA][0];
        y1 = vertices[cityA][1];
        x2 = vertices[cityB][0];
        y2 = vertices[cityB][1];
        deltaX = x1 - x2;
        deltaY = y1 - y2;
        return Math.round(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }
}
