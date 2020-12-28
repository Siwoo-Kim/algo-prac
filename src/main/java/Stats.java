import java.util.Collection;
import java.util.Scanner;

public class Stats {
    private final double mean, stddev;

    public Stats(String file) {
        Scanner scanner = AlgData.getScanner(file);
        Bag<Double> bag = LinkedList.bag();
        while (scanner.hasNextDouble())
            bag.add(scanner.nextDouble());
        int N = bag.size();
        double sum = 0.0;
        for (double e: bag)
            sum += e;
        mean = sum / N;
        sum = 0.0;
        for (double e: bag)
            sum += (e - mean) * (e - mean);
        stddev = Math.sqrt((sum / (N-1)));
    }

    public static void main(String[] args) {
        Stats stats = new Stats("stats.txt");
        System.out.println(stats.mean);
        System.out.println(stats.stddev);
    }
}
