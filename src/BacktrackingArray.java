//public class BacktrackingArray implements Array<Integer>, Backtrack {
//    private Stack stack;
//    private int[] arr;
//    private Integer minimum;
//    private Integer maximum;
//    private Integer size;
//
//    // Do not change the constructor's signature
//    public BacktrackingArray(Stack stack, int size) {
//        this.stack = stack;
//        arr = new int[size];
//        minimum = Integer.MAX_VALUE;
//        maximum = Integer.MIN_VALUE;
//        this.size=0;
//    }
//
//    @Override
//    public Integer get(int index){
//        // TODO: implement your code here
//    }
//
//    @Override
//    public Integer search(int x) {
//        Integer output = -1;
//        if (x<minimum | x>maximum)
//            return output;
//        for (int i=0;i<size;i++){
//            if (arr[i]==x) {
//                output = i;
//                break;
//            }
//        }
//        return output;
//    }
//
//    @Override
//    public void insert(Integer x) {         //if (insert) push inserted value & "insert"
//                                            // remember minimum & maximum
//                                            // increase size
//        // TODO: implement your code here
//    }
//
//    @Override
//    public void delete(Integer index) { //push deleted value + "delete"
//                                        //decrease size
//                                        // arr[index]==min|max, min|max=pre|successor
//        if (index>=size | index<0)
//            throw new IllegalArgumentException("index out of bounds");
//        stack.push(arr[index]);
//        stack.push("delete");
//        Integer temp = arr[index];
//        if (temp==minimum)
//            minimum=successor(index);
//        else if (temp==maximum)
//            maximum=predecessor(index);
//        arr[index]=arr[size-1];
//        arr[size-1]=0;
//        size=size-1;
//    }
//
//    @Override
//    public Integer minimum() {
//        return minimum;
//    }
//
//    @Override
//    public Integer maximum() {
//        return maximum;
//    }
//
//    @Override
//    public Integer successor(Integer index) {
//        // TODO: implement your code here
//    }
//
//    @Override
//    public Integer predecessor(Integer index) {
//        // TODO: implement your code here
//    }
//
//    @Override
//    public void backtrack() {             //Object = stack.pop, if equals "delete" insert(stack.pop), else delete(
//        // TODO: implement your cod e here
//    }
//
//    @Override
//    public void retrack() {
//        // Do not implement anything here!!
//    }
//
//    @Override
//    public void print() {
//        String toprint = ""+arr[0];
//        for (int i=1; i<size;i++){
//            toprint= toprint + " " + arr[i];
//        }
//        System.out.println(toprint);
//    }
//}
