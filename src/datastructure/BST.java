package datastructure;

// TreeNode remains the same, storing an int.
class TreeNode {
    int data;
    int height;
    TreeNode left;
    TreeNode right;

    TreeNode(int data) {
        this.data = data;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

// The class is no longer generic. It is explicitly for integers.
public class BST {
    private TreeNode root;
    private int size = 0;

    // Method to add an element to the BST
    public boolean add(int element) {
        if (element < 0 || this.contains(element)) { // check for invalid ID
            return false;
        }
        this.root = addRecursive(this.root, element);
        return true;
    }

    // Recursive method for adding a new node
    private TreeNode addRecursive(TreeNode currentNode, int element) {
        if (currentNode == null) {
            this.size++;
            return new TreeNode(element);
        }
        if (element < currentNode.data) {
            currentNode.left = addRecursive(currentNode.left, element);
        } else if (element > currentNode.data) {
            currentNode.right = addRecursive(currentNode.right, element);
        } else {
            // Element already exists
            return currentNode;
        }

        // Update height
        currentNode.height = 1 + Math.max(getHeight(currentNode.left), getHeight(currentNode.right));

        // Get balance factor
        int balance = getBalance(currentNode);

        // 4 rotation cases for AVL balancing
        // LL Case
        if (balance > 1 && element < currentNode.left.data)
            return rightRotate(currentNode);

        // RR Case
        if (balance < -1 && element > currentNode.right.data)
            return leftRotate(currentNode);

        // LR Case
        if (balance > 1 && element > currentNode.left.data) {
            currentNode.left = leftRotate(currentNode.left);
            return rightRotate(currentNode);
        }

        // RL Case
        if (balance < -1 && element < currentNode.right.data) {
            currentNode.right = rightRotate(currentNode.right);
            return leftRotate(currentNode);
        }

        return currentNode;
    }

    // Public method to remove an element from the BST
    public boolean remove(int element) {
        if (element < 0 || !this.contains(element)) {
            return false;
        }
        this.root = removeRecursive(this.root, element);
        this.size--;
        return true;
    }

    // Recursive method to remove a node and maintain AVL balance
    private TreeNode removeRecursive(TreeNode node, int key) {
        if (node == null) return null;

        if (key < node.data) {
            node.left = removeRecursive(node.left, key);
        } else if (key > node.data) {
            node.right = removeRecursive(node.right, key);
        } else {
            // Node with only one child or no child
            if ((node.left == null) || (node.right == null)) {
                TreeNode temp = (node.left != null) ? node.left : node.right;
                node = (temp == null) ? null : temp; // If no child, node becomes null
            } else {
                // Node with two children: get the inorder successor (smallest in the right subtree)
                TreeNode temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = removeRecursive(node.right, temp.data);
            }
        }

        if (node == null) return node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int balance = getBalance(node);

        // Rebalancing cases
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Finds the node with the minimum value (left-most node)
    private TreeNode minValueNode(TreeNode node) {
        TreeNode current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Returns the largest value in the BST without removing it
    public int getMax() {
        if (isEmpty()) {
            return -1;
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
        x.right = y;
        y.left = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y;
    }

    // Method to check if an element exists in the BST
    public boolean contains(int element) {
        if (element < 0) {
            return false;
        }
        TreeNode currentNode = this.root;
        while (currentNode != null) {
            if (element == currentNode.data) {
                return true;
            } else if (element < currentNode.data) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    // Search method now just returns the int if found, or -1
    public int search(int element) {
        if (this.contains(element)) {
            return element;
        } else {
            return -1; // Not found
        }
    }

    // Method to call the recursive in-order traversal
    public void inOrder() {
        System.out.print("In-order traversal of Patient IDs: ");
        inOrderRecursive(this.root);
        System.out.println();
    }

    // Recursive in-order traversal
    private void inOrderRecursive(TreeNode currentNode) {
        if (currentNode != null) {
            inOrderRecursive(currentNode.left);
            System.out.print(String.format("%04d ", currentNode.data));
            inOrderRecursive(currentNode.right);
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    // Method to clear the entire BST
    public void clear() {
        this.root = null;
        this.size = 0;
    }
}
