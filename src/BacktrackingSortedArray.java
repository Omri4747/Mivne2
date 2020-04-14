public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer size;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.size=0;
    }
    @Override
    public Integer get(int index){
        if (!rangecheck(index))
            return null;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        int output = -1;
        boolean found = false;
        int middle = size / 2;
        int low = 0;
        int high = size - 1;
        while (!found & low <= high) {
            if (arr[middle] == x) {
                output = middle;
                found = true;
            }
            else if (arr[middle] < x) {        //x is in the right part of the index
                low = middle + 1;
                middle = (high + low) / 2;
            }
            else {                            //x is in the left part of the index
                high = middle - 1;
                middle = (low + high) / 2;
            }
        }
        return output;
    }

    @Override
    public void insert(Integer x) {
        stack.push(toInsert(x));
        stack.push("insert");
    }

    //sorting a sorted array with 1 element unsorted at the end of the array
    // and return the index it was inserted
    private int toInsert(Integer x){
        arr[size]=x;
        size++;
        int i=size-1;
        while (i>0 && x<arr[i-1]){
            arr[i]=arr[i-1];
            arr[i-1]=x;
            i--;
        }
        return i;
    }


    @Override
    public void delete(Integer index) {
        if (rangecheck(index)) {
            stack.push(arr[index]);
            stack.push("delete");
            toDelete(index);
        }
    }

    private void toDelete (Integer index){
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[size - 1] = 0;
        size--;
    }

    @Override
    public Integer minimum() {
        if (size==0)
            return -1;
        else
            return 0;
    }

    @Override
    public Integer maximum() {
        if (size==0)
            return -1;
        else
            return size-1;
    }

    @Override
    public Integer successor(Integer index) {
        Integer output = -1;
        if (!rangecheck(index) || (index==size-1) | arr[index]==arr[size-1])
            return output;

        for (int i=index+1;i<size;i++){
            if (arr[index]<arr[i]) {
                output = i;
                break;
            }
        }
        return output;
    }

    @Override
    public Integer predecessor(Integer index) {
        Integer output = -1;
        if (!rangecheck(index) || (index==0) | arr[index]==arr[0])
            return output;
        for (int i=index-1;i>=0;i--){
            if (arr[index]>arr[i]) {
                output = i;
                break;
            }
        }
        return output;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            Object action = stack.pop();
            Integer value = (Integer) stack.pop();
            if (action.equals("delete")) {
                toInsert(value);
            }
            else if (action.equals("insert")) {
                toDelete(value);
            }
        }
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        for (int i = 0; i < size; i++) {
            if (i == size - 1)
                System.out.print(arr[i]);
            else
                System.out.print(arr[i] + " ");
        }
    }

    private boolean rangecheck(int index){
        return (index >=0 & index<size);
    }

    public static void main(String[] args) {
        Stack st = new Stack();
        BacktrackingSortedArray A = new BacktrackingSortedArray(st,100);
        for (int i=0;i<5;i++){
            A.insert(i);
        }
        A.print();
        System.out.println("\n");
        for (int i=0;i<5;i++){
            A.backtrack();
        }
        A.print();
//        System.out.println("\n");
//        for (int i=0;i<10;i++){
//            A.backtrack();
//            A.print();
//            System.out.println("\n");

    }
}
