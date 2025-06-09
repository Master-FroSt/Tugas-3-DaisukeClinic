package com.datastructure;

public class Queue<T> {
    private LinkedList<T> queue = new LinkedList<>();

    // Method to add new data at the rear
    public void enqueue(T value) {
        queue.addLast(value);
    }

    // Method to delete the data at the front
    public T dequeue() {
        T value = queue.getFirst();
        queue.removeFirst();
        return value;
    }

    // Method to return the data at the front
    public T peek() {
        return queue.getFirst();
    }

    // Method to check if the stack isEmpty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Method to get the current number of items
    public int size() {
        return queue.size();
    }
}
