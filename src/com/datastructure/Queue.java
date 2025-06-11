package com.datastructure;

public class Queue<T> {
    private Object[] data;
    private int front, rear, size, capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(T item) {
        if (size == capacity) throw new IllegalStateException("Queue full");
        rear = (rear + 1) % capacity;
        data[rear] = item;
        size++;
    }

    public T dequeue() {
        if (size == 0) throw new IllegalStateException("Queue empty");
        T item = (T) data[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int idx = front;
        while (count < size) {
            sb.append(data[idx]).append("\n");
            idx = (idx + 1) % capacity;
            count++;
        }
        return sb.toString();
    }
}