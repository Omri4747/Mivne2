public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;


    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        Node curr = root;
        while (curr != null) {
            if (curr.key == x)
                break;
            else {
                if (x < curr.key)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
        }
        return curr;
    }

    public void insert(BacktrackingBST.Node z) {
        if (!stack.isEmpty()) {
            String what = (String) stack.pop();
            if (!what.equals("backtrack")) {
                stack.push(what);
                stack.push(z);
                stack.push("insert");
            }
        } else {
            stack.push(z);
            stack.push("insert");
        }
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == null)
            root = z;     //tree was empty
        else if (z.key < y.key)
            y.left = z;
        else
            y.right = z;
    }


    //finished, except for the stuck pushing
    public void delete(Node x) {
        if (!stack.isEmpty()) {
            String what = (String) stack.pop();
            if (!what.equals("backtrack")) {
                stack.push(what);
                stack.push(x);
                stack.push("delete");
            }
        } else {
            stack.push(x);
            stack.push("delete");
        }
        if (x.left == null & x.right == null)  //x has no children
            x.deleteZeroSons();
        else if (x.left != null & x.right != null)  //x has two children
            x.deleteTwoSons(x, successor(x));
        else //x has one children
            x.deleteOneSon();
    }


    public Node minimum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        Node output = root.minimum();
        return output;
    }

    public Node maximum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        Node output = root.maximum();
        return output;
    }

    public Node successor(Node x) {
        Node output;
        if (x.right != null)
            output = x.right.minimum();
        else {
            output = x.parent;
            while (output != null & x == output.right) {
                x = output;
                output = output.parent;
            }
        }
        return output;
    }

    public Node predecessor(Node x) {
        Node output;
        if (x.left != null)
            output = x.left.maximum();
        else {
            output = x.parent;
            while (output != null & x == output.left) {
                x = output;
                output = output.parent;
            }
        }
        return output;
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // TODO: implement your code here
    }

    public void printPreOrder() {
        root.printPreOrder("");
    }

    @Override
    public void print() {
        printPreOrder();
    }

    public static class Node {
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Node minimum() {
            if (left == null)
                return this;
            else
                return left.minimum();
        }

        public Node maximum() {
            if (right == null)
                return this;
            else
                return right.minimum();
        }

        public String printPreOrder(String output) {
            if (this == null)
                return output;
            output = output + this.key;
            if (left != null)
                output = left.printPreOrder(output + " ");
            if (right != null)
                output = right.printPreOrder(output + " ");
            return output;
        }

        private String whichSon() {
            if (key > parent.key)
                return "right";
            else
                return "left";
        }

        private void deleteZeroSons() {
            if (whichSon().equals("left"))
                parent.left = null;
            else
                parent.right = null;
        }

        private void deleteOneSon() {
            if (whichSon().equals("left")) {
                if (left != null) {
                    parent.left = left;
                    left.parent = parent;
                } else {
                    parent.left = right;
                    right.parent = parent;
                }
            }
            else {
                if (left != null){
                    parent.right = left;
                    left.parent = parent;
                }
                else {
                    parent.right = right;
                    right.parent = parent;
                }
            }
        }

        private static void deleteTwoSons(Node x, Node successor) {
            if (successor.right != null)
                successor.deleteOneSon();
            else
                successor.deleteZeroSons();
            successor.parent = x.parent;
            successor.left = x.left;
            successor.right = x.right;
            x.left.parent = successor;
            x.right.parent = successor;
            if (x.whichSon().equals("left"))
                x.parent.left = successor;
            else
                x.parent.right = successor;
        }
    }
}



