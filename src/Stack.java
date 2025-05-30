public class Stack<T> {
    private LinkedList<T> stack = new LinkedList<>();

    // Method to remove and return the top item
    public T pop() {
        if (stack.isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        T value = stack.getLast();
        stack.removeLast();
        return value;
    }

    // Method to return the top item without removing it
    public T peek() {
        if (stack.isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        return stack.getLast();
    }

    // Method to add an item to the top
    public void push(T value) {
        stack.addLast(value);
    }

    // Method to check if the stack isEmpty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Method to get the current number of items
    public int size() {
        return stack.size();
    }

    // Method to empty the stack completely
    public void clear() {
        stack.clear();
    }

    // Method to print stack
    public void printStack() {
        System.out.print("Bottom -> ");
        stack.printForward();
    }
}
