package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class MinHeap {
    private static final int DEFAULT_CAPACITY = 10;

    private int capacity = DEFAULT_CAPACITY;
    private int size = 0;
    private int[] items = new int[capacity];

    private final PerformanceTracker perf = new PerformanceTracker();
    public PerformanceTracker metrics() { return perf; }

    public MinHeap() {}
    public MinHeap(int capacity) {
        this.capacity = Math.max(1, capacity);
        this.items = new int[this.capacity];
    }


    //--------------------------------------------------------------------------------------

    private int getLeftChildIndex(int parentIndex)  { return parentIndex * 2 + 1; }
    private int getRightChildIndex(int parentIndex) { return parentIndex * 2 + 2; }
    private int getParentIndex(int childIndex)      { return (childIndex - 1) / 2; }

    private boolean hasLeftChild(int index)  { return getLeftChildIndex(index)  < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }
    private boolean hasParent(int index)     { return index > 0; }

    private int  get(int i)        { perf.r(); return items[i]; }
    private void set(int i, int v) { perf.w(); items[i] = v;   }
    private boolean less(int a, int b) { perf.c(); return a < b; }

    private int leftChild(int index)  { return get(getLeftChildIndex(index)); }
    private int rightChild(int index) { return get(getRightChildIndex(index)); }
    private int parent(int index)     { return get(getParentIndex(index)); }

    //--------------------------------------------------------------------------------------

    private void swap(int i, int j) {
        if (i == j) return;
        perf.s();
        int ai = get(i);
        int aj = get(j);
        set(i, aj);
        set(j, ai);
    }

    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            items = Arrays.copyOf(items, capacity);
        }
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    //-------------------------------------------------------------------------------------
    public int peek() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return get(0);
    }

    public int poll() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        int min = get(0);
        set(0, get(size - 1));
        size--;
        heapifyDownFrom(0);
        return min;
    }

    public void add(int value) {
        ensureCapacity();
        set(size, value);
        size++;
        heapifyUpFrom(size - 1);
    }

    //----------------------------------------------------------------------------------------
    /**
    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int old = get(index);
        if (newValue > old) throw new IllegalArgumentException("newValue > old");
        set(index, newValue);
        heapifyUpFrom(index);
    }
    **/

    public void decreaseKey(int i, int newVal) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        int cur = get(i);
        if (newVal > cur) throw new IllegalArgumentException("newVal > current");
        if (newVal == cur) return;
        set(i, newVal);
        heapifyUpFrom(i);
    }


    public void merge(MinHeap other) {
        if (other == null || other.size == 0) return;
        int newSize = this.size + other.size;

        int newCap = Math.max(this.capacity, newSize);
        if (newCap < newSize) newCap = newSize;
        int[] combined = new int[newCap];

        System.arraycopy(this.items, 0, combined, 0, this.size);
        System.arraycopy(other.items, 0, combined, this.size, other.size);

        this.items = combined;
        this.capacity = newCap;
        this.size = newSize;

        for (int i = this.size / 2 - 1; i >= 0; i--) {
            heapifyDownFrom(i);
        }
    }

    public static MinHeap fromArray(int[] a) {
        MinHeap h = new MinHeap(a.length);
        System.arraycopy(a, 0, h.items, 0, a.length);
        h.size = a.length;
        for (int i = h.size / 2 - 1; i >= 0; i--) h.heapifyDownFrom(i);
        return h;
    }


    //---------------------------------------------------------------------------------

    private void heapifyUpFrom(int i) {
        while (hasParent(i) && less(get(i), parent(i))) {
            int p = getParentIndex(i);
            swap(p, i);
            i = p;
        }
    }

    private void heapifyDownFrom(int index) {
        perf.h();
        int i = index;
        while (hasLeftChild(i)) {
            int l = getLeftChildIndex(i);
            int r = getRightChildIndex(i);
            int s = l;

            if (r < size && less(get(r), get(l))) s = r;
            if (!less(get(s), get(i))) break;

            swap(i, s);
            i = s;
        }
    }

}
