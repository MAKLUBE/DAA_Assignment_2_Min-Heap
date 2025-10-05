package algorithms;

import java.util.Arrays;

public class MinHeap {
    private int capacity = 10;
    private int size = 0;
    private int[] items = new int[capacity];

    private int getLeftChildIndex(int parentIndex)  { return parentIndex * 2 + 1; }
    private int getRightChildIndex(int parentIndex) { return parentIndex * 2 + 2; }
    private int getParentIndex(int childIndex)      { return (childIndex - 1) / 2; }

    //Checking indexes
    private boolean hasLeftChild(int index)  { return getLeftChildIndex(index)  < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }
    private boolean hasParent(int index)     { return index > 0; }

    private int leftChild(int index)  { return items[getLeftChildIndex(index)]; }
    private int rightChild(int index) { return items[getRightChildIndex(index)]; }
    private int parent(int index)     { return items[getParentIndex(index)]; }

    private void swap(int i, int j) {
        int t = items[i];
        items[i] = items[j];
        items[j] = t;
    }

    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            items = Arrays.copyOf(items, capacity);
        }
    }

    public int peek() {
        if (size == 0)
            throw new IllegalStateException("Heap is empty");
        return items[0];
    }

    public int poll() {
        if (size == 0)
            throw new IllegalStateException("Heap is empty");
        int item = items[0];
        items[0] = items[size - 1];
        size--;
        heapifyDown();
        return item;
    }

    public void add(int item) {
        ensureCapacity();
        items[size] = item;
        size++;
        heapifyUp();
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && parent(index) > items[index]) {
            int p = getParentIndex(index);
            swap(p, index);
            index = p;
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && rightChild(index) < leftChild(index)) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if (items[index] <= items[smallerChildIndex]) break;

            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }
}
