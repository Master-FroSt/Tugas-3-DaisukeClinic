public class Queue<T> {

    private T[] queue;
    private int front, rear, size, capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        this.queue = (T[]) new Object[capacity];
        front = size = 0;
        rear = -1;
    }

    public void enqueue(T item) {
        if (size == capacity) {
            System.out.println("Queue penuh");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue kosong");
            return null;
        }
        T item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("Queue kosong");
            return null;
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void getDisplay(){
        if (isEmpty()) {
            System.out.println("Queue kosong");
            return;
        }
        System.out.println("Isi Queue: )");
        for (int i = 0; i < size; i++){
            int index = (front + i) % capacity;
            System.out.print(queue[index] + " ");
        }
        System.out.println();
    }
}
