package datastructure;

import entity.Patient;

class TreeNode {
    Patient data;
    int height;
    TreeNode left;
    TreeNode right;

    TreeNode(Patient data) {
        this.data = data;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

// Khusus buat patient weh, aku ganti node nya patient - rifqia
public class BST {
    private TreeNode root;
    private int size = 0;

    // Method untuk menambahkan elemen ke BST
    public boolean add(Patient element) {
        if (element.getID() < 0 || this.contains(element)) { // cek untuk invalid ID
            return false;
        }
        this.root = addRecursive(this.root, element);
        return true;
    }

    // Method rekursi untuk penambahan node baru
    private TreeNode addRecursive(TreeNode currentNode, Patient element) {
        if (currentNode == null) {
            this.size++;
            return new TreeNode(element);
        }
        if (element.getID() < currentNode.data.getID()) {
            currentNode.left = addRecursive(currentNode.left, element);
        }
        else if (element.getID() > currentNode.data.getID()) {
            currentNode.right = addRecursive(currentNode.right, element);
        }
        else {
            return currentNode;
        }

        // Update tinggi node
        currentNode.height = 1 + Math.max(getHeight(currentNode.left), getHeight(currentNode.right));

        // Hitung balance factor
        int balance = getBalance(currentNode);

        // 4 kasus rotasi
        // LL
        if (balance > 1 && element.getID() < currentNode.left.data.getID())
            return rightRotate(currentNode);

        // RR
        if (balance < -1 && element.getID() > currentNode.right.data.getID())
            return leftRotate(currentNode);

        // LR
        if (balance > 1 && element.getID() > currentNode.left.data.getID()) {
            currentNode.left = leftRotate(currentNode.left);
            return rightRotate(currentNode);
        }

        // RL
        if (balance < -1 && element.getID() < currentNode.right.data.getID()) {
            currentNode.right = rightRotate(currentNode.right);
            return leftRotate(currentNode);
        }

        return currentNode;
    }

    // Method publik untuk menghapus elemen dari BST
    public boolean remove(Patient element) {
        if (element.getID() < 0 || !this.contains(element)) {
            return false;
        }
        this.root = removeRecursive(this.root, element);
        this.size--;
        return true;
    }

    // Method rekursif untuk menghapus node dan menjaga keseimbangan AVL
    private TreeNode removeRecursive(TreeNode node, Patient key) {
        if (node == null) return null;

        if (key.getID() < node.data.getID()) {
            node.left = removeRecursive(node.left, key);
        }
        else if (key.getID() > node.data.getID()) {
            node.right = removeRecursive(node.right, key);
        }
        else {
            // Node dengan hanya satu anak atau tanpa anak
            if ((node.left == null) || (node.right == null)) {
                TreeNode temp = (node.left != null) ? node.left : node.right;

                // Tidak ada anak
                if (temp == null) {
                    node = null;
                }
                else { // Satu anak
                    node = temp;
                }
            }
            else {
                // Node dengan dua anak: ambil inorder successor (terkecil di kanan)
                TreeNode temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = removeRecursive(node.right, temp.data);
            }
        }

        // Jika hanya satu node di tree
        if (node == null) return node;

        // Update height
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // Balance factor
        int balance = getBalance(node);

        // 4 kasus rotasi setelah penghapusan
        // LL
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        // LR
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        // RL
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Method untuk mencari node dengan nilai minimum (terkiri)
    private TreeNode minValueNode(TreeNode node) {
        TreeNode current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Method untuk mengembalikan nilai terbesar di BST tanpa menghapus
    public Patient getMax() {
        if (isEmpty()) {
            return null;
        }
        TreeNode current = this.root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    private int getHeight(TreeNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(TreeNode node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        // rotasi
        x.right = y;
        y.left = T2;

        // update tinggi
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        // rotasi
        y.left = x;
        x.right = T2;

        // update tinggi
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    // Method untuk mengecek apakah sebuah elemen ada di dalam BST
    public boolean contains(Patient element){
        if (element == null) { // cek untuk null patient
            return false;
        }
        if (isEmpty()) {
            System.out.println("Tree is Empty!!");
        }
        TreeNode currentNode = this.root;
        while (currentNode != null) {
            if (element.getID() == currentNode.data.getID()) {
                return true;
            }
            else if (element.getID() < currentNode.data.getID()) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    // Method untuk mengecek apakah sebuah ID dari patient ada di dalam BST
    public Patient containsID(int element){
        if (element < 0) { // cek untuk null patient
            return null;
        }
        if (isEmpty()) {
            System.out.println("Tree is Empty!!");
        }
        TreeNode currentNode = this.root;
        while (currentNode != null) {
            if (element == currentNode.data.getID()) {
                return currentNode.data;
            }
            else if (element < currentNode.data.getID()) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        return null;
    }

    // Method pencari element dengan method contains
    public Patient search(Patient element) {
        if (this.contains(element)) {
            return element;
        }
        else {
            return null; // Jika tidak ditemukan return -1
        }
    }

    // Method untuk memanggil pencetak terurut rekursi
    public void inOrder() {
        inOrderRecursive(this.root);
        System.out.println();
    }

    // Method pencetak element secara rekursi
    private void inOrderRecursive(TreeNode currentNode) {
        if (currentNode != null) { //penelusuran dari kiri, cetak, lalu kanan
            inOrderRecursive(currentNode.left);
            System.out.println(currentNode.data + " ");
            inOrderRecursive(currentNode.right);
        }
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    // Method untuk menghapus seluruh isi BST
    public void clear() {
        this.root = null;
        this.size = 0;
    }
}