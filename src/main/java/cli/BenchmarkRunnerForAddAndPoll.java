package cli;

import algorithms.MinHeap;
import java.io.IOException;
import java.nio.file.*;
import java.util.Random;

public class BenchmarkRunnerForAddAndPoll {
    private static final String HEADER = "n,add_ms,poll_ms";
    private static final Path OUT = Paths.get("docs", "performance-plots", "empirical_add_poll.csv");

    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 5000, 10000, 50000, 100000};
        int seed = 42;
        Random rnd = new Random(seed);

        if (!Files.exists(OUT.getParent())) Files.createDirectories(OUT.getParent());
        Files.writeString(OUT, HEADER + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println(HEADER);

        for (int n : sizes) {
            int[] data = rnd.ints(n, Integer.MIN_VALUE, Integer.MAX_VALUE).toArray();
            MinHeap heap = new MinHeap(n);

            long StartAdd= System.nanoTime();
            for (int x : data) heap.add(x);
            long EndAdd = System.nanoTime();
            double AvgAdd = (EndAdd - StartAdd) / 1e6 / n;

            long StartPoll = System.nanoTime();
            for (int i = 0; i < n; i++) heap.poll();
            long EndPoll = System.nanoTime();
            double AvgPoll = (EndPoll - StartPoll) / 1e6 / n;

            String row = String.format("%d, %.3f, %.3f", n, AvgAdd, AvgPoll );
            System.out.println(row);
            Files.writeString(OUT, row + System.lineSeparator(), StandardOpenOption.APPEND);
        }

        System.out.println("CSV saved → " + OUT.toString());
    }
}

