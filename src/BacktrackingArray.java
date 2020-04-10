public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer minimum;
    private Integer maximum;
    private Integer size;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        minimum = Integer.MAX_VALUE;
        maximum = Integer.MIN_VALUE;
        this.size=0;
    }

    @Override
    public Integer get(int index){
        // TODO: implement your code here
    }

    @Override
    public Integer search(int x) {
        // TODO: implement your code here
    }

    @Override
    public void insert(Integer x) {         //if (insert) push inserted value & "insert"
                                            // remember minimum & maximum
                                            // increase size
        // TODO: implement your code here
    }

    @Override
    public void delete(Integer index) { //push deleted value + "delete"
                                        //decrease size
                                        // arr[index]==min|max, min|max=pre|successor
        // TODO: implement your code here
    }

    @Override
    public Integer minimum() {
        return minimum;
    }

    @Override
    public Integer maximum() {
        return maximum;
    }

    @Override
    public Integer successor(Integer index) {
        // TODO: implement your code here
    }

    @Override
    public Integer predecessor(Integer index) {
        // TODO: implement your code here
    }

    @Override
    public void backtrack() {             //Object = stack.pop, if equals "delete" insert(stack.pop), else delete(
        // TODO: implement your code here
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        // TODO: implement your code here
    }
}
