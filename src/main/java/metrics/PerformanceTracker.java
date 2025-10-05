package metrics;

public final class PerformanceTracker {
    private long comparisons, swaps, reads, writes, heapifyCalls;

    public void c() { comparisons++; }
    public void s() { swaps++; }
    public void r() { reads++; }
    public void w() { writes++; }
    public void h() { heapifyCalls++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps()       { return swaps; }
    public long getReads()       { return reads; }
    public long getWrites()      { return writes; }
    public long getHeapifyCalls(){ return heapifyCalls; }

    public void reset() {
        comparisons = swaps = reads = writes = heapifyCalls = 0;
    }

    @Override public String toString() {
        return "cmp=" + comparisons + ", swaps=" + swaps + ", reads=" + reads +
                ", writes=" + writes + ", heapify=" + heapifyCalls;
    }
}
