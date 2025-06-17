package datastructure;

@SuppressWarnings("unchecked")
public class PriorityQueue<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int capacity;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = (T[]) new Comparable[capacity];
    }

    public void enqueue(T item) {
        if (size == capacity) {
            throw new IllegalStateException("PriorityQueue is full");
        }

        heap[size] = item;
        bubbleUp(size);
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }

        T top = heap[0];
        heap[0] = heap[size - 1];
        size--;
        bubbleDown(0);
        return top;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
                smallest = left;
            }

            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < size; i++) {
            output.append(heap[i]).append("\n");
        }
        return output.toString();
    }

    public LinkedList<T> toSortedList() {
        LinkedList<T> result = new LinkedList<>();
        PriorityQueue<T> temp = new PriorityQueue<>(capacity);
        
        // Salin semua elemen ke queue sementara
        for (int i = 0; i < size; i++) {
            temp.enqueue(heap[i]);
        }

        // Ambil dari temp agar terurut, tapi queue asli tetap utuh
        while (!temp.isEmpty()) {
            result.addLast(temp.dequeue());
        }

        return result;
    }
}
