public class BST {
    public static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    private Node root;
    private int size;
    public BST() { // Constructor
        this.root = null;
        this.size = 0;
        System.out.println("SimpleBSTSet initialized");
    }

    // Method untuk memanggil method rekursi penambahan element
    public boolean add(int element){
        if(element < 0 || this.contains(element)){ // cek untuk invalid ID
            return false;
        }
        this.root = addRecursive(this.root, element);
        return true;
    }
    // Method penambahan element dengan penelusuran rekursi
    private Node addRecursive(Node currentNode, int element){
        if(currentNode == null){
            this.size++;
            return new Node(element);
        }
        if(element < currentNode.data){
            currentNode.left = addRecursive(currentNode.left, element);
        } else if(element > currentNode.data){
            currentNode.right = addRecursive(currentNode.right, element);
        }
        return currentNode;
    }

    public boolean contains(int element){
        if(element < 0){ // cek untuk invalid ID
            return false;
        }
        Node currentNode = this.root;
        while(currentNode != null){
            if(element == currentNode.data){
                return true;
            }else if (element < currentNode.data){
                currentNode = currentNode.left;
            }else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    // Method pencari element dengan method contains
    public int search(int element) {
        if (this.contains(element)) {
            return element;
        } else {
            return -1;
        }
    }

    // Method untuk memanggil pencetak terurut rekursi
    public void InOrder() {
        System.out.print("In-order traversal: ");
        inOrderRecursive(this.root);
        System.out.println();
    }
    // Method pencetak element secara rekursi
    private void inOrderRecursive(Node currentNode) {
        if (currentNode != null) { //penelusuran dari kiri, cetak, lalu kanan
            inOrderRecursive(currentNode.left);
            System.out.print(currentNode.data + " ");
            inOrderRecursive(currentNode.right);
        }
    }

    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
}
