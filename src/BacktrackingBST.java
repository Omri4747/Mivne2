public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    private boolean retrack;


    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
        retrack = false;
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
        retrack = false;
        stack.push(z);
        stack.push("insert");
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
        retrack = false;
        deleteMain(x);
    }

    public Node minimum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        return root.minimum();
    }

    public Node maximum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        return root.maximum();
    }

    public Node successor(Node x) {
        Node output = null;
        if (x.right != null)
            output = x.right.minimum();
        else {
            if (x.parent!=null) {           //x is not the root and has no right sons
                output = x.parent;
                while (output != null && x == output.right) {
                    x = output;
                    output = output.parent;
                }
            }
        }
        return output;
    }

    public Node predecessor(Node x) {
        Node output = null;
        if (x.left != null)
            output = x.left.maximum();
        else {
            if (x.parent!=null) {           //x is not the root and has no left sons
                output = x.parent;
                while (output != null && x == output.left) {
                    x = output;
                    output = output.parent;
                }
            }
        }
        return output;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            retrack = true;
            String action = (String) stack.pop();
            if (action.equals("insert")) {
                Node toRemove = (Node) stack.pop();
                redoStack.push(toRemove);
                redoStack.push("insert");
                deleteZeroSons(toRemove);
            } else {
                stack.push(action);
                insertbacktrack();      //reversing delete
            }
        }
    }

    @Override
    public void retrack() {
        if (retrack & !redoStack.isEmpty()) {
            String action = (String) redoStack.pop();
            if (action.equals("insert")) {
                Node toReturn = (Node) redoStack.pop();
                if (toReturn.parent == null)
                    root = toReturn;
                else if (toReturn.key < toReturn.parent.key)
                    toReturn.parent.left = toReturn;
                else
                    toReturn.parent.right = toReturn;
                stack.push(toReturn);
                stack.push("insert");
            }
            else if (action.equals("delete"))
                deleteMain((Node) redoStack.pop());
            else {
                Node mySuccessor = (Node) redoStack.pop();
                Node toDel = (Node) redoStack.pop();
                stack.push(toDel);
                stack.push(mySuccessor);
                stack.push("delete2");
                deleteTwoSons(toDel,mySuccessor);
            }
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
    private void deleteMain (BacktrackingBST.Node x){
        if (x.left == null & x.right == null) {  //x has no children
            deleteZeroSons(x);
            stack.push(x);
            stack.push("delete0");
        }
        else if (x.left != null & x.right != null) {  //x has two children
            Node successor = successor(x);
            stack.push(x);
            stack.push(successor.parent);
            stack.push("delete2");
            deleteTwoSons(x,successor);
        }
        else {//x has one children
            deleteOneSon(x);
            stack.push(x);
            stack.push("delete1");
        }
    }
    private void deleteZeroSons(Node toDel){
        if (toDel.parent == null)
            root = null;
        else if (toDel.key < toDel.parent.key)
            toDel.parent.left = null;
        else
            toDel.parent.right = null;
    }

    private void deleteOneSon(Node toDel){
        if (toDel.left != null) { //i have only left son
            if (toDel.parent == null)
                root = toDel.left;
            else if (toDel.key < toDel.parent.key) //im the left son
                toDel.parent.left = toDel.left;
            else                                  // im the right son
                toDel.parent.right = toDel.left;

            toDel.left.parent = toDel.parent;
        }
        else {                                      //i have only right son
            if (toDel.parent == null)
                root = toDel.right;
            else if (toDel.key < toDel.parent.key)  //im left son
                toDel.parent.left = toDel.right;
            else                                    //im right son
                toDel.parent.right = toDel.right;

            toDel.right.parent = toDel.parent;
        }
    }

    private void deleteTwoSons(Node toDel, Node mySuccessor){
        if (mySuccessor.right == null)        //successor is a leaf
            deleteZeroSons(mySuccessor);

        else if (mySuccessor.right != null)      //successor has right son
            deleteOneSon(mySuccessor);

        //always- swap mySuccessor's fields with toDel's fields
        mySuccessor.parent = toDel.parent;
        mySuccessor.right = toDel.right;
        mySuccessor.left = toDel.left;
        if (mySuccessor.right!=null)
            mySuccessor.right.parent = mySuccessor;
        if (mySuccessor.left!=null)
            mySuccessor.left.parent = mySuccessor;
        if (mySuccessor.parent == null)
            root = mySuccessor;
        else if (mySuccessor.key < mySuccessor.parent.key)
            mySuccessor.parent.left = mySuccessor;
        else
            mySuccessor.parent.right = mySuccessor;
    }

    private void insertbacktrack(){
        String action = (String) stack.pop();
        if (action.equals("delete0")){
            Node toinsert = (Node)stack.pop();
            if (toinsert.parent==null)
                root=toinsert;
            else if (toinsert.key< toinsert.parent.key)
                toinsert.parent.left=toinsert;
            else
                toinsert.parent.right=toinsert;
            redoStack.push(toinsert);
            redoStack.push("delete");
        }
        else if (action.equals("delete1")) {
            Node toinsert = (Node) stack.pop();
            if (toinsert.parent==null)                  //toinsert was the root
                root=toinsert;
            else if (toinsert.key<toinsert.parent.key)   //he was the left son
                toinsert.parent.left = toinsert;
            else                                        //he was the right son
                toinsert.parent.right= toinsert;

            if (toinsert.left != null)                        // had left son
                toinsert.left.parent = toinsert;
            else                                            // had right son
                toinsert.right.parent = toinsert;

            redoStack.push(toinsert);
            redoStack.push("delete");
        }
        else{                                   //last delete had two sons
            Node wbf = (Node) stack.pop();     //wbf stands for 'will be father'
            Node toinsert = (Node) stack.pop();
            Node mySuccessor = toinsert.left.parent;
            redoStack.push(toinsert);
            redoStack.push(mySuccessor);
            redoStack.push("delete2");
            if (wbf.key==toinsert.key) {      //the successor was the right son of to insert
                mySuccessor.parent = toinsert;
                toinsert.left.parent = toinsert;
                mySuccessor.left=null;
                toinsert.right=mySuccessor;
                if (toinsert.parent==null)
                    root=toinsert;
                else if (toinsert.parent.key > toinsert.key)
                    toinsert.parent.left = toinsert;
                else
                    toinsert.parent.right = toinsert;
            }
            else {
                mySuccessor.parent=wbf;
                mySuccessor.right=wbf.left;
                mySuccessor.left=null;
                wbf.left=mySuccessor;
                toinsert.right.parent=toinsert;
                toinsert.left.parent=toinsert;
                if (toinsert.parent==null)
                    root=toinsert;
                else if (toinsert.key<toinsert.parent.key)
                    toinsert.parent.left=toinsert;
                else
                    toinsert.parent.right=toinsert;
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
            output = output + this.key;
            if (left != null)
                output = left.printPreOrder(output + " ");
            if (right != null)
                output = right.printPreOrder(output + " ");
            return output;
        }

        @Override
        public String toString(){return ""+key;}
    }

    public static void main(String[] args) {
        BacktrackingBST T = new BacktrackingBST(new Stack(),new Stack());
        BacktrackingBST.Node toadd1 = new BacktrackingBST.Node(2,null);
        BacktrackingBST.Node toadd2 = new BacktrackingBST.Node(0,null);
        BacktrackingBST.Node toadd3 = new BacktrackingBST.Node(9,null);
        BacktrackingBST.Node toadd4 = new BacktrackingBST.Node(10,null);
        BacktrackingBST.Node toadd5 = new BacktrackingBST.Node(4,null);
        BacktrackingBST.Node toadd6 = new BacktrackingBST.Node(3,null);
        BacktrackingBST.Node toadd7 = new BacktrackingBST.Node(5,null);
        BacktrackingBST.Node toadd8 = new BacktrackingBST.Node(6,null);

        T.insert(toadd1);
        T.insert(toadd2);
        T.insert(toadd3);
        T.insert(toadd4);
        T.insert(toadd5);
        T.insert(toadd6);
        T.insert(toadd7);
        T.insert(toadd8);
        T.printPreOrder();
        T.delete(toadd5);
        T.printPreOrder();
        T.backtrack();
        T.printPreOrder();
    }
}




