package spw4.tsp;

import java.util.*;

public class TSPSolver {
    public static Random random;

    private static String solutionToString(Solution<Permutation> solution, double bestKnownQuality) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%.2f%%", ((solution.getQuality() / bestKnownQuality) - 1) * 100));
        builder.append("   ");
        builder.append(solution.toString());
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        random = new Random(1234);

        Problem<Permutation> problem;
        Algorithm<Permutation> algorithm;
        Solution<Permutation> best;

        problem = new TSP("instances/ch130.tsp", 6110);
        algorithm = new GA(problem, 1000, 100, new TournamentSelector(), new OrderCrossover(), new InversionMutator(), 0.05);
        best = algorithm.execute();
        System.out.println(solutionToString(best, problem.getBestKnownQuality()));
        System.out.println("Runtime [ms]: " + algorithm.getRuntimeInMilliseconds());

        System.out.println();
        System.out.println();

        problem = new TSP("instances/kroA200.tsp", 29368);
        algorithm = new GA(problem, 1000, 100, new TournamentSelector(), new OrderCrossover(), new InversionMutator(), 0.05);
        best = algorithm.execute();
        System.out.println(solutionToString(best, problem.getBestKnownQuality()));
        System.out.println("Runtime [ms]: " + algorithm.getRuntimeInMilliseconds());
    }
}
