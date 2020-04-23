public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    private boolean retrack;          //indicates that backtrack was applied before executing retrack


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
            if (curr.key == x)           //x found
                break;
            else {
                if (x < curr.key)        //x should be in the left sub tree
                    curr = curr.left;
                else                     //x should be in the right sub tree
                    curr = curr.right;
            }
        }
        return curr;
    }

    public void insert(BacktrackingBST.Node z) {
        retrack = false;                             //can't execute retrack after insert
        stack.push(z);                               //keeping the node for future backtrack
        stack.push("insert");
        Node y = null;                               //will be parent of z
        Node x = root;                               //first node to start the search to put z
        while (x != null) {                          //a "tour" in the tree to find the correct place for z, until x==null
            y = x;
            if (z.key < x.key)                      //z should be inserted in the left sub tree
                x = x.left;
            else                                    //z should be inserted in the right sub tree
                x = x.right;
        }
        z.parent = y;
        if (y == null)
            root = z;                              //tree was empty
        else if (z.key < y.key)                    //z should be left son of y
            y.left = z;
        else                                       //z should be right son of y
            y.right = z;
    }

    //the delete function is separated into 5 parts:
    //1) (delete) which only updates the retrack field and calls for delete main.
    //2) (delete main) which checks if x has no sons,one son, or two son, and calls for the matched function.
    //in addition, delete main is responsible for pushing the elements that we want to keep for future backtracking
    //3) (deleteZeroSons) deletes leafs from the tree
    //4) (deleteOneSon) deletes nodes with exactly one son
    //5) (deleteTwoSons) delete nodes with exactly two sons
    public void delete(Node x) {
        retrack = false;                  //can't execute retrack after delete
        deleteMain(x);                    //
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
            retrack = true;                   //indicates that its legal to execute retrack
            String action = (String) stack.pop();
            if (action.equals("insert")) {
                Node toRemove = (Node) stack.pop();
                redoStack.push(toRemove);
                redoStack.push("insert");
                deleteZeroSons(toRemove);
            }
            else {
                insertbacktrack(action);      //reversing delete
            }
            System.out.println("backtracing performed");
        }
    }

    @Override
    public void retrack() {
        if (retrack & !redoStack.isEmpty()) {               //backtrack was the last action & at least one action (insert or delete) was applied
            String action = (String) redoStack.pop();
            if (action.equals("insert")) {                  //last action was backtracking insert (in fact, delete), so we need to reverse it by inserting back
                Node toReturn = (Node) redoStack.pop();
                if (toReturn.parent == null)                //toReturn was root
                    root = toReturn;
                else if (toReturn.key < toReturn.parent.key)//toReturn was a left son
                    toReturn.parent.left = toReturn;
                else                                        //toReturn was a right son
                    toReturn.parent.right = toReturn;
                //pushing elements to the backtrack stack for future backtracking
                stack.push(toReturn);
                stack.push("insert");
            }                                               //last action was backtracking delete0 or delete1 (in fact, insert),
            else if (action.equals("delete"))               //so we need to reverse it by deleting zeroSon or oneSon
                deleteMain((Node) redoStack.pop());
            else {                                          //last action was backtracking delete2, so we need to delete using deleteTwoSons function
                Node mySuccessor = (Node) redoStack.pop();
                Node toDel = (Node) redoStack.pop();
                stack.push(toDel);
                stack.push(mySuccessor.parent);
                stack.push("delete2");
                deleteTwoSons(toDel,mySuccessor);
            }
        }
    }

    public void printPreOrder() {
        System.out.println(root.printPreOrder(""));   //calls assistance function in class node
    }

    @Override
    public void print() {
        printPreOrder();
    }

    // assistance functions
    //insert without pushing element to the stack
    private void deleteMain (BacktrackingBST.Node x){
        if (x.left == null & x.right == null) {          //x has no children (leaf)
            deleteZeroSons(x);
            stack.push(x);
            stack.push("delete0");
        }
        else if (x.left != null & x.right != null) {     //x has two children
            Node successor = successor(x);               //the successor of x is the candidate to replace x
            stack.push(x);
            stack.push(successor.parent);                //the ancestor of the successor before the change
            stack.push("delete2");
            deleteTwoSons(x,successor);
        }
        else {                                           //x has one children
            deleteOneSon(x);
            stack.push(x);
            stack.push("delete1");
        }
    }

    private void deleteZeroSons(Node toDel){     //toDel is a leaf
        if (toDel.parent == null)                //toDel==root
            root = null;
        else if (toDel.key < toDel.parent.key)   //toDel was a left son
            toDel.parent.left = null;
        else                                     //toDel was a right son
            toDel.parent.right = null;
    }

    private void deleteOneSon(Node toDel){
        if (toDel.left != null) {                   //toDel have only left son
            if (toDel.parent == null)
                root = toDel.left;
            else if (toDel.key < toDel.parent.key)  //toDel is left son
                toDel.parent.left = toDel.left;
            else                                    //toDel is right son
                toDel.parent.right = toDel.left;

            toDel.left.parent = toDel.parent;
        }
        else {                                      //toDel has only right son
            if (toDel.parent == null)
                root = toDel.right;
            else if (toDel.key < toDel.parent.key)  //toDel is left son
                toDel.parent.left = toDel.right;
            else                                    //toDel is right son
                toDel.parent.right = toDel.right;

            toDel.right.parent = toDel.parent;
        }
    }

    private void deleteTwoSons(Node toDel, Node mySuccessor){
        //first step- cutting successor of the tree by using the previous delete functions
        //invariant - in this scenario successor never has a left son
        if (mySuccessor.right == null)                        //successor is a leaf
            deleteZeroSons(mySuccessor);

        else if (mySuccessor.right != null)                  //successor has right son
            deleteOneSon(mySuccessor);

        //second step - changing mySuccessor's fields to toDel's fields
        mySuccessor.parent = toDel.parent;
        mySuccessor.right = toDel.right;
        mySuccessor.left = toDel.left;
        //third step- updating the fields of mySuccessor's sons and parent
        mySuccessor.right.parent = mySuccessor;
        mySuccessor.left.parent = mySuccessor;
        if (mySuccessor.parent == null)                      //mySuccessor == root
            root = mySuccessor;
        else if (mySuccessor.key < mySuccessor.parent.key)   //mySuccessor should be left son
            mySuccessor.parent.left = mySuccessor;
        else
            mySuccessor.parent.right = mySuccessor;          //mySuccessor should be left son
    }

    //this function is an assistance function for the backtracking of delete.
    //the target is to reverse delete in O(1) runtime complexity, and without pushing the stuck unwanted elements
    //it is separated into 3 parts:
    //1) (delete0) backtracking a deletion of a leaf
    //2) (delete1) backtracking a deletion of a node that had exactly on son
    //3) (delete2) backtracking a deletion of a node that had exactly two sons
    private void insertbacktrack(String action){
        //first option- backtracking the deletion of a leaf
        if (action.equals("delete0")){
            Node toinsert = (Node)stack.pop();          //necessarily the top value in the stack
            if (toinsert.parent==null)                  //toInsert was root!
                root=toinsert;
            else if (toinsert.key< toinsert.parent.key) //toInsert was a left son
                toinsert.parent.left=toinsert;
            else                                        //toInsert was a right son
                toinsert.parent.right=toinsert;
            //pushing element for future retrack
            redoStack.push(toinsert);
            redoStack.push("delete");
        }
        //second option- backtracking the deletion of a node that had one son
        else if (action.equals("delete1")) {
            Node toinsert = (Node) stack.pop();
            if (toinsert.parent==null)                  //toInsert was the root
                root=toinsert;
            else if (toinsert.key<toinsert.parent.key)  //toInsert was the left son
                toinsert.parent.left = toinsert;
            else                                        //toInsert was the right son
                toinsert.parent.right= toinsert;

            if (toinsert.left != null)                        //toInsert had a left son
                toinsert.left.parent = toinsert;
            else                                            //toInsert had a right son
                toinsert.right.parent = toinsert;
            //pushing element for future retrack
            redoStack.push(toinsert);
            redoStack.push("delete");
        }
        //third option- backtracking the deletion of a node that had two sons
        else{
            Node wbf = (Node) stack.pop();            //wbf stands for 'will be father' & necessarily the top value in the stack
            Node toinsert = (Node) stack.pop();       //necessarily the second value in the stack
            Node mySuccessor = toinsert.left.parent;  //the previous fields were still remain
            //pushing elements for future retrack
            redoStack.push(toinsert);
            redoStack.push(mySuccessor);
            redoStack.push("delete2");
            if (wbf.key==toinsert.key) {             //the successor was the right son of toInsert
                mySuccessor.parent = toinsert;       //notice: wbf is toInsert
                //updating toInsert & mySuccessor fields
                toinsert.left.parent = toinsert;
                mySuccessor.left=null;
                toinsert.right=mySuccessor;
                if (toinsert.parent==null)          //if toInsert was root
                    root=toinsert;
                else if (toinsert.parent.key > toinsert.key)   //toInsert was left son
                    toinsert.parent.left = toinsert;
                else                                           //toInsert was right son
                    toinsert.parent.right = toinsert;
            }
            else {                                 //the successor was not the right son of toInsert (was left son of wbf)
                //returning mySuccessor to it's original place
                mySuccessor.parent=wbf;
                mySuccessor.right=wbf.left;
                mySuccessor.left=null;
                wbf.left=mySuccessor;
                if (mySuccessor.right != null)
                    mySuccessor.right.parent = mySuccessor;
                //updating toInsert's fields
                toinsert.right.parent=toinsert;
                toinsert.left.parent=toinsert;
                if (toinsert.parent==null)                     //toInsert was root
                    root=toinsert;
                else if (toinsert.key<toinsert.parent.key)     //toInsert was left son
                    toinsert.parent.left=toinsert;
                else                                           //toInsert was right son
                    toinsert.parent.right=toinsert;
            }
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
            if (left == null)          //this, is the minimum :)
                return this;
            else
                return left.minimum(); //minimum is the left node in the left sub tree
        }

        private Node maximum() {
            if (right == null)          //this, is the minimum :)
                return this;
            else
                return right.maximum(); //maximum is the right node in the right sub tree
        }

        private String printPreOrder(String output) {
            output = output + this.key;                               //Me
            if (left != null)
                output = left.printPreOrder(output + " ");    //My left sub tree
            if (right != null)
                output = right.printPreOrder(output + " ");   //My right sub tree
            return output;
        }
    }
}




