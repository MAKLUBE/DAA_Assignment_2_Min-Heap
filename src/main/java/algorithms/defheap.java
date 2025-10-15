package algorithms;

import java.util.Arrays;

public class defheap {

    private int capacity = 16;
    private int[] heap = new int[capacity];
    private int size = 0;

    private int getParent(int i) {return i / 2;}
    private int getLeftChild(int i) {return 2 * i + 1;}
    private int getRightChild(int i) {return 2 * i + 2;}

    private boolean hasParent(int i) {return i > 0;}
    private boolean hasLeftChild(int i) {return getLeftChild(i) < size;}
    private boolean hasRightChild(int i) {return getRightChild(i) < size;}

    private void ensureCapacity(int i) {
        if (size == capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
    }

    private void swap(int i, int j) {
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private int peek(int i) {
        if (size == 0) throw new IllegalArgumentException("HEAP EMPTY");
        return heap[i];
    }

    private void add(int value){
        ensureCapacity(size + 1);
        heap[size] = value;
        int index = size - 1;
        size++;
        heapifyUp(index);
    }

    private void heapifyUp(int i) {
        while(true){
            int parent = getParent(i);
            if(heap[i] >= heap[parent]){break;}
                swap(parent, i);
                i = parent;
        }
    }



    public static void main(String[] args) {
        defheap heap = new defheap();
        heap.add(1);
        heap.add(2);
        System.out.println(heap.peek(1));
    }
}
