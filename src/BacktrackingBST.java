public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    private Integer nevo;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
        nevo = 0;
    }

    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        // TODO: implement your code here
    }

    public void insert(BacktrackingBST.Node z) {
        if (!stack.isEmpty()) {
            String what = (String) stack.pop();
            if (!what.equals("backtrack")) {
                stack.push(what);
                stack.push(z);
                stack.push("insert");
            }
        }
        else{
            stack.push(z);
            stack.push("insert");
        }
        Node y = null;
        Node x = root;
        while (x!=null){
            y=x;
            if (z.key<x.key)
                x=x.left;
            else
                x=x.right;
        }
        z.parent=y;
        if (y==null)
            root=z;     //tree was empty
        else if (z.key<y.key)
            y.left=z;
        else
            y.right=z;
    }

    public void delete(Node x) {
        if (!stack.isEmpty()) {
            String what = (String) stack.pop();
            if (!what.equals("backtrack")) {
                stack.push(what);
                stack.push(x);
                stack.push("delete");
            }
        }

    }

    public Node minimum() {
        if (root==null)
            throw new IllegalArgumentException("empty tree");
        Node output = root;
        while(output.left!=null){
            output=output.left;
        }
        return output;
    }

    public Node maximum() {
        if (root==null)
            throw new IllegalArgumentException("empty tree");
        Node output = root;
        while(output.right!=null){
            output=output.right;
        }
        return output;
    }

    public Node successor(Node x) {
        // TODO: implement your code here
    }

    public Node predecessor(Node x) {
        // TODO: implement your code here
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // TODO: implement your code here
    }

    public void printPreOrder(){
        root.printPreOrder("");
    }

    @Override
    public void print() {
        // TODO: implement your code here

    }

    public static class Node{
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

        public String printPreOrder(String output){
            if (this==null)
                return output;
            output = output + this.key;
            if (this.left!=null)
                output=left.printPreOrder(output + " ");
            if (right!=null)
                output=right.printPreOrder(output+" ");
            return output;
        }
    }

}
