class Node<T> {
    // Atribute
    T value;
    Node<T> nextNode;
    Node<T> prevNode;

    // Construstor
    Node(T value) {
        this.value = value;
        this.nextNode = null;
        this.prevNode = null;
    }
}

public class LinkedList<T> {
    // Atribute
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

    //Method to print LinkedList from forward (head)
    public void printForward() {
        if (isEmpty()) {
            System.out.println("LinkedList is Empty");
        }
        else {
            Node<T> temp = head;
            while (temp != null) {
                System.out.print(temp.value + " <-> ");
                temp = temp.nextNode;
            }
            System.out.println("null");
        }
    }

    // Method to print LinkedList from backward (tail)
    public void printBackward() {
        if (isEmpty()) {
            System.out.println("LinkedList is Empty");
        }
        else {
            Node<T> temp = tail;
            while (temp != null) {
                System.out.print(temp.value + " <-> ");
                temp = temp.prevNode;
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
            head.prevNode = newNode;
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
        }
        else {
            tail.nextNode = newNode;
            newNode.prevNode = tail;
        }
        tail = newNode;
        size++;
    }

    // Method to add node at spesific index
    public void addAt(int index, T value) {
        if (index <= 0) {
            addFirst(value);
            return;
        }
        else if (index >= size) {
            addLast(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        if (index <= size / 2) {
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.nextNode;
            }
            newNode.nextNode = temp;
            newNode.prevNode = temp.prevNode;
            temp.prevNode = newNode;
            newNode.prevNode.nextNode = newNode;
        }
        else {
            Node<T> temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prevNode;
            }
            newNode.nextNode = temp;
            newNode.prevNode = temp.prevNode;
            temp.prevNode = newNode;
            if (newNode.prevNode != null) {
                newNode.prevNode.nextNode = newNode;
            }
        }
        size++;
    }

    // Method to set (replace) value at a specific index
    public void set(int index, T newValue) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds");
            return;
        }

        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.nextNode;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prevNode;
            }
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
            head.prevNode = null;
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
            tail = tail.prevNode;
            tail.nextNode = null;
        }
        size--;
    }

    // Method to remove node at spesific index
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index not found");
            return;
        }
        if (index == 0) {
            removeFirst();
            return;
        }
        if (index == size - 1) {
            removeLast();
            return;
        }

        Node<T> temp;
        if (index <= size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.nextNode;
            }
        }
        else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prevNode;
            }
        }
        temp.prevNode.nextNode = temp.nextNode;
        temp.nextNode.prevNode = temp.prevNode;
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
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.nextNode;
            }
        }
        else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prevNode;
            }
        }
        return temp.value;
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
        return -1; // Not found
    }

    // Method to check if value exists in LinkedList
    public boolean contains(T value) {
        return indexOf(value) != -1;
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
}