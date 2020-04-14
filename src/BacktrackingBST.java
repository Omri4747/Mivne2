public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    boolean retrack;


    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
        retrack=false;
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
        retrack=false;
        stack.push(z);
        stack.push("insert");
        insert1(z);
    }


    //finished, except for the stuck pushing
    public void delete(Node x) {
        retrack=false;
        BacktrackingBST T = new BacktrackingBST(stack,redoStack);
        if (x.left == null & x.right == null) {  //x has no children
            x.deleteZeroSons();
            stack.push(x);
            stack.push("delete0");
        }
        else if (x.left != null & x.right != null) {  //x has two children
            Node successor = successor(x);
            stack.push(x);
            stack.push(successor);
            stack.push(successor.parent);
            x.deleteTwoSons(successor);
            stack.push("delete2");
        }
        else {//x has one children
            x.deleteOneSon();
            stack.push(x);
            stack.push(x.parent);
            stack.push(x.whichSon());       //string, was left or right son
            stack.push("delete1");
        }
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
        retrack=true;
        String action = (String) stack.pop();
        if (action.equals("insert")) {
            Node toRemove = (Node) stack.pop();
            toRemove.deleteZeroSons();
            redoStack.push(toRemove);
            redoStack.push("insert");
        }
        else {
            stack.push(action);
            insertbacktrack();      //reversing delete
        }
    }

    @Override
    public void retrack() {
        if (retrack) {
            String action = (String) redoStack.pop();
            if (action.equals("insert"))
                delete((Node) redoStack.pop());
            else
                insert((Node) redoStack.pop());
        }
    }

    public void printPreOrder() {
        System.out.println(root.printPreOrder(""));
    }

    @Override
    public void print() {
        printPreOrder();
    }



    // assistance functions
    //insert without pushing element to the stack
    private void insert1(BacktrackingBST.Node z){
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

    private void insertbacktrack(){
        String action = (String) stack.pop();
        if (action.equals("delete0")){
            Node toinsert = (Node)stack.pop();
            insert1(toinsert);
            redoStack.push(toinsert);
            redoStack.push("delete");
        }
        else if (action.equals("delete1")) {
            String side = (String) stack.pop();
            Node father = (Node) stack.pop();
            Node toinsert = (Node) stack.pop();
            redoStack.push(toinsert);
            redoStack.push("delete");
            if (side.equals("left")) {              //he was the left son
                if (father.left.key < toinsert.key)
                    toinsert.left = father.left;
                else {
                    toinsert.right = father.left;
                }
                //always
                father.left = toinsert;
                toinsert.parent = father;
            } else if (side.equals("right")) {      //he was the right son
                if (father.right.key > toinsert.key)
                    toinsert.right = father.right;
                else {
                    toinsert.left = father.right;
                }
                //always
                father.right = toinsert;
                toinsert.parent = father;
            }
        }
        else{                                   //last delete had two sons
            Node wbf = (Node) stack.pop();     //wbf stands for will be father
            Node mySuccessor = (Node) stack.pop();
            Node toinsert = (Node) stack.pop();
            redoStack.push(toinsert);
            redoStack.push("delete");
            if (wbf.key==toinsert.key) {      //the successor was the right son of to insert
                toinsert.right = mySuccessor;
                mySuccessor.parent = toinsert;
                toinsert.left=mySuccessor.left;
                mySuccessor.left=null;
            }
            else {
                toinsert.right=mySuccessor.right;
                toinsert.left=mySuccessor.left;
                toinsert.parent=mySuccessor.parent;
                toinsert.left.parent=toinsert;
                toinsert.right.parent=toinsert;
                mySuccessor.parent=wbf;
                mySuccessor.right=wbf.left;
                mySuccessor.left=null;
                wbf.left.parent=mySuccessor;
                wbf.left=mySuccessor;
            }
        }
    }
    // TODO remove
    public void treeFormPrint(){
        if (root != null) treeFormPrint(root, "");

    }
    // TODO remove
    private void treeFormPrint(Node node, String acc) {
        String signSpace = acc + " ";
        if (node.right != null) {
            treeFormPrint(node.right, acc + " ");
            if (node.right.parent == node)
                System.out.println(signSpace + "/");
            else System.out.println(signSpace + "$");
        }
        System.out.println(acc + "| key: " + node.key);
        System.out.println(acc + "| par: " + node.parent);
        if (node.left != null) {
            if (node.left.parent == node)
                System.out.println(signSpace + "\\");
            else System.out.println(signSpace + "$");
            treeFormPrint(node.left, acc + " ");
        }
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

        private Node minimum() {
            if (left == null)
                return this;
            else
                return left.minimum();
        }

        private Node maximum() {
            if (right == null)
                return this;
            else
                return right.maximum();
        }

        private String printPreOrder(String output) {
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
            if (parent==null)
                return "root";
            else if (key > parent.key)
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

        private void deleteTwoSons(Node successor) {    //deletes this and replace with successor
            if (successor.right != null)
                successor.deleteOneSon();
            else
                successor.deleteZeroSons();
            successor.parent = parent;
            successor.left = left;
            successor.right = right;
            left.parent = successor;
            right.parent = successor;
            if (whichSon().equals("left"))
                parent.left = successor;
            else if (whichSon().equals("right"))
                parent.right = successor;
        }

        @Override
        public String toString(){return ""+key;}
    }

    public static void main(String[] args) {
        BacktrackingBST T = new BacktrackingBST(new Stack(), new Stack());
        BacktrackingBST.Node todelete1 = new BacktrackingBST.Node(3,6);
        BacktrackingBST.Node todelete2= new BacktrackingBST.Node(10,6);
        BacktrackingBST.Node todelete3 = new BacktrackingBST.Node(0,6);
        BacktrackingBST.Node todelete4 = new BacktrackingBST.Node(5,6);
        BacktrackingBST.Node todelete5 = new BacktrackingBST.Node(8,6);
        BacktrackingBST.Node todelete6 = new BacktrackingBST.Node(7,6);

        T.insert(todelete1);
        T.insert(todelete2);
        T.insert(todelete3);
        T.insert(todelete4);
        T.insert(todelete5);
        T.insert(todelete6);

        System.out.println(T.minimum().key);
        System.out.println(T.maximum().key);

    }

}



