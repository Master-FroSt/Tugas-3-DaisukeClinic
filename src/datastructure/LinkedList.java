package datastructure;

class Node<T> {
    // Atribute
    T value;
    Node<T> nextNode;

    // Constructor
    Node(T value) {
        this.value = value;
        this.nextNode = null;
    }
}

public class LinkedList<T> {
    // Attribute
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    // Method to get size
    public int size() {
        return size;
    }

    // method to check if LinkedList empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Method to empty-ing LinkedList
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // Method to print LinkedList from forward (head)
    public void printForward() {
        if (isEmpty()) {
            System.out.println("LinkedList is Empty");
        }
        else {
            Node<T> temp = head;
            while (temp != null) {
                System.out.print(temp.value);
                if (temp.nextNode != null) System.out.print(" -> ");
                temp = temp.nextNode;
            }
            System.out.println("null");
        }
    }

    // Method to add node at the beginning
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.nextNode = head;
            head = newNode;
        }
        size++;
    }

    // Method to add node at the end
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.nextNode = newNode;
            tail = newNode;
        }
        size++;
    }

    // Method to set (replace) value at a specific index
    public void set(int index, T newValue) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds");
            return;
        }

        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.nextNode;
        }

        temp.value = newValue;
    }

    // Method to remove the first node
    public void removeFirst() {
        if (isEmpty()) {
            System.out.println("LinkedList is Empty");
            return;
        }
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            head = head.nextNode;
        }
        size--;
    }

    // Method to remove the last node
    public void removeLast() {
        if (isEmpty()) {
            System.out.println("LinkedList is Empty");
            return;
        }
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            Node<T> temp = head;
            while(temp.nextNode != tail) {
                temp = temp.nextNode;
            }
            tail = temp;
            tail.nextNode = null;
        }
        size--;
    }

    // Method to remove node with spesific value
    public boolean removeByValue(T value) {
        int index = indexOf(value);
        if (index < 0) {
            System.out.println("Value not be found");
            return false;
        }
        removeAt(index);
        return true;
    }

    // Method to get value at spesific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index not found");
            return null;
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.nextNode;
        }
        return temp.value;
    }

    // Method to check if value exists in LinkedList
    public boolean contains(T value) {
        if (isEmpty()) {
            return false;
        }
        return indexOf(value) != -1;
    }

    // Method to get the index of a spesific given value
    public int indexOf(T value) {
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (temp.value.equals(value)) {
                return index;
            }
            temp = temp.nextNode;
            index++;
        }
        return -1;
    }

    // Method to remove node at spesific index
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds");
            return;
        }
        if (index == 0) {
            removeFirst();
            return;
        }
        Node<T> prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.nextNode;
        }
        prev.nextNode = prev.nextNode.nextNode;
        if (index == size - 1) {
            tail = prev;
        }
        size--;
    }


    // Method to get the value of the first Node
    public T getFirst() {
        if (isEmpty()) {
            System.out.println("LinkedList is empty");
            return null;
        }
        return head.value;
    }

    // Method to get the value of the last Node
    public T getLast() {
        if (isEmpty()) {
            System.out.println("LinkedList is empty");
            return null;
        }
        return tail.value;
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            StringBuilder output = new StringBuilder();
            Node<T> temp = head;
            while (temp != null) {
                output.append(temp.value);
                if (temp.nextNode != null) {
                    output.append(", ");
                }
                temp = temp.nextNode;
            }
            return output.toString();
        }
        return "LinkedList is empty";
    }
}