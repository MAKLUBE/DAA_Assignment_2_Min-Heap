package algorithms;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MinHeapTest {

    @Test
    void addAndPoll() {
        MinHeap h = new MinHeap();
        int[] data = {5, 1, 9, -3, 7, 0};
        for (int x : data) h.add(x);

        int prev = Integer.MIN_VALUE;
        while (true) {
            try {
                int val = h.poll();
                assertTrue(val >= prev, "Heap violated");
                prev = val;
            } catch (IllegalStateException e) {
                break;
            }
        }
    }

    @Test
    void addPeekPoll() {
        MinHeap heap = new MinHeap();
        heap.add(5); heap.add(1); heap.add(3); heap.add(-2); heap.add(9); heap.add(0);

        assertEquals(-2, heap.peek());
        assertEquals(-2, heap.poll());
        assertEquals(0, heap.poll());
        assertEquals(1, heap.poll());
        assertEquals(3, heap.poll());
        assertEquals(5, heap.poll());
        assertEquals(9, heap.poll());
        assertThrows(IllegalStateException.class, heap::peek);
        assertThrows(IllegalStateException.class, heap::poll);
    }


    @Test
    void duplicates() {
        MinHeap heap = new MinHeap();
        heap.add(2); heap.add(2); heap.add(1); heap.add(1); heap.add(1);
        assertEquals(1, heap.poll());
        assertEquals(1, heap.poll());
        assertEquals(1, heap.poll());
        assertEquals(2, heap.poll());
        assertEquals(2, heap.poll());
    }

    @Test
    void differentValues() {
        MinHeap h = new MinHeap();
        int[] a = { -10, -1, -5, 0, 7, -3 };
        for (int v : a) h.add(v);
        int prev = Integer.MIN_VALUE;
        while (true) {
            try {
                int cur = h.poll();
                assertTrue(cur >= prev);
                prev = cur;
            } catch (IllegalStateException e) {
                break;
            }
        }
    }

    @Test
    void emptyHeap() {
        MinHeap h = new MinHeap();
        assertThrows(IllegalStateException.class, h::peek);
        assertThrows(IllegalStateException.class, h::poll);
    }


    @Test
    void decreaseKeyAndMergework() {
        MinHeap a = new MinHeap();
        a.add(10); a.add(5); a.add(30); a.add(40);
        a.decreaseKey(3, 1);
        assertEquals(1, a.peek());

        MinHeap b = new MinHeap();
        b.add(2); b.add(6); b.add(0);

        a.merge(b);
        int[] expect = {0,1,2,5,6,10,30};
        for (int e : expect) {
            assertEquals(e, a.poll());
        }
        assertTrue(a.isEmpty());
    }
    assertTrue(a.isEmpty());
}



}
