package algorithms;

public class withoutMetricsForMinHeap {
    private final int DEFAULT_CAPACITY = 8;

    private int capacity = DEFAULT_CAPACITY;
    private int[] heap = new int[capacity];
    private int size = 0;

    private int getParentIndex(int childIndex) {return (childIndex - 1) / 3;}
    private int getLeftChildIndex(int parentIndex) {return parentIndex * 3 + 1;}
    private int getCenterChildIndex(int parentIndex) {return parentIndex * 3 + 2;}
    private int getRightChildIndex(int parentIndex) {return parentIndex * 3 + 3;}

    private boolean hasChild(int i) {return getLeftChildIndex(i) < 0;}
    private boolean hasParent(int index) {return index > 0;}

    private void swap(int index1, int index2) {
        int temp = heap[index2];
        heap[index2] = heap[index1];
        heap[index1] = temp;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            int newCapacity = heap.length * 2;
            int[] newHeap = new int[newCapacity];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
            capacity = newCapacity;
        }
    }

    // to see minimum
    public int peek() {
        if (size == 0) {throw new IllegalStateException("Empty heap");}
        return heap[0];
    }

    public void add(int value) {
        ensureCapacity(); // checking for capacity if malo to increase capacity
        heap[size] = value;
        int index = size;
        size++;
        heapifyUp(index);
    }


    //deleting minimum
    public int poll(){
        if (size == 0) {throw new IllegalStateException("Empty heap");}
        int min = heap[0];
        // taking last element putting it into root
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);
        return min;
    }


    public void heapifyDown(int index) {
        /**
         *
         * int left = getLeftChildIndex(index);
           int right = getRightChildIndex(index);
         * if(left >= size) return;
         *
         * int min = (right < size && heap[right] < heap[left]) ? right : left;
         *         if(heap[min] >= heap[index]) {return;}
         *         swap(index, min);
         *         heapifyDown(min);
         *
         *
         *
         * **/
        while(hasChild(index)) {
            int left = getLeftChildIndex(index);
            int center = getCenterChildIndex(index);
            int right = getRightChildIndex(index);

            int min = left;

            if (center < size && heap[center] < min) {min = center;}
            if (right < size && heap[right] < min) {min = right;}

            if(heap[min] >= heap[index]) {return;}
            swap(index, min);
            index = min;
        }
    }

    public void heapifyUp(int index){
        while (index > 0) {
            int parent = getParentIndex(index);
            if(heap[index] >= heap[parent]) break;

            swap(index, parent);
            index = parent;
        }
    }


    private void decreaseKey(int index, int value) {
        if (heap[index] == value) {
            heap[index] = value;
        }
        heapifyUp(index);
    }


    /**
    private void mergeSlow(DefenceForMinHeap other) {
    if (other == null || other.size == 0) return;
    for (int j = 0; j < other.size; j++) {
     add(other.heap[j]); // add внутри сам вызовет ensureCapacity() и heapifyUp
     **/

    private void merge(withoutMetricsForMinHeap other) {
        if (other == null || other.size == 0) {return ;}
        int newSize = this.size + other.size;

        int newCap = Math.max(this.capacity, newSize);

        int[] combined = new int[newCap];
        System.arraycopy(heap, 0, combined, 0, size);
        System.arraycopy(other.heap, 0, combined, size, other.size);

        this.heap = combined;
        this.size = newSize;
        this.capacity = newCap;

        for (int i = this.size / 2 - 1; i >= 0; i--) heapifyDown(i);
    }

    public static void main(String[] args) {
        withoutMetricsForMinHeap heap1 = new withoutMetricsForMinHeap();
        heap1.add(1);
        heap1.add(4);
        heap1.add(3);
        heap1.add(6);
        heap1.add(2);

        while(heap1 != null){
            System.out.print(heap1.poll() + " ");
        }
    }
}
