public class Queue {

    private int[] queue;
    private int front, rear, size, capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = size = 0;
        rear = -1;
    }

    public void enqueue(int item) {
        if (size == capacity) {
            System.out.println("Queue penuh");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue kosong");
            return -1;
        }
        int item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue kosong");
            return -1;
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // tambah enqueue dengan addLast, dequeue dengan removeFirst, display/traversal, isEmpty, size, peek dengan get, traverse
}
