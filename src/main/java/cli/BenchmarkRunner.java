package cli;

import algorithms.MinHeap;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.nio.file.*;
import java.util.Random;

public class BenchmarkRunner {
    private static final String HEADER = "n,time_ms,comparisons,swaps,reads,writes,heapifyCalls";
    private static final Path OUT = Paths.get("docs", "performance-plots", "empirical.csv");

    public static void main(String[] args) throws IOException {
        int[] sizes = {10, 100, 1000, 10000, 100000};
        int seed = 42;

        if (!Files.exists(OUT.getParent())) Files.createDirectories(OUT.getParent());
        if (!Files.exists(OUT)) {
            Files.writeString(OUT, HEADER + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        System.out.println("Empirical Validation Benchmark (Min-Heap)");
        System.out.println(HEADER);

        for (int n : sizes) {
            int[] data = new Random(seed).ints(n, Integer.MIN_VALUE, Integer.MAX_VALUE).toArray();

            long t0 = System.nanoTime();
            MinHeap heap = MinHeap.fromArray(data);
            long t1 = System.nanoTime();

            int extractK = Math.max(1, n / 10);
            for (int i = 0; i < extractK; i++) heap.poll();
            long t2 = System.nanoTime();

            long totalMs = Math.round((t2 - t0) / 1e6);
            PerformanceTracker p = heap.metrics();

            String row = String.format("%d,%d,%d,%d,%d,%d,%d",
                    n, totalMs,
                    p.getComparisons(), p.getSwaps(),
                    p.getReads(), p.getWrites(), p.getHeapifyCalls()
            );

            System.out.println(row);
            Files.writeString(OUT, row + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        System.out.println("CSV appended → " + OUT.toString());
    }
}
