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
        int[] stored = new int[arr.length];
        for (int i=0;i<size;i++){
            stored[i]=arr[i];
        }
        stack.push(stored);
        stack.push("insert");
        arr[size]=x;
        size++;
        bubblesort(arr);
    }

    //sorting a sorted array with 1 element unsorted at the end of the array
    private void bubblesort(int[] arr){
        for (int i=size-1;i>0;i--){
            if (arr[i]<arr[i-1]){
                int temp= arr[i];
                arr[i]=arr[i-1];
                arr[i-1]=temp;
            }
        }
    }


    @Override
    public void delete(Integer index) {
        if (rangecheck(index)) {
            int[] stored = new int[arr.length];
            for (int i = 0; i < size; i++) {
                stored[i] = arr[i];
            }
            stack.push(stored);
            stack.push("delete");
            for (int i = index; i < size - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[size - 1] = 0;
            size--;
        }
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
        for (int i=index-1;i<size;i--){
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
            Object what = stack.pop();
            Object previousarr = stack.pop();
            if (what.equals("delete")) {
                size = size + 1;
                arr = (int[]) previousarr;
            } else if (what.equals("insert")) {
                size = size - 1;
                arr = (int[]) previousarr;
            }
        }
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        String toprint = ""+arr[0];
        for (int i=1; i<size;i++){
            toprint= toprint + " " + arr[i];
        }
        System.out.print(toprint);
    }

    private boolean rangecheck(int index){
        return (index >=0 & index<size);
    }

    public static void main(String[] args) {
        Stack st = new Stack();
        BacktrackingSortedArray A = new BacktrackingSortedArray(st,100);
        for (int i=0;i<20;i++){
            A.insert((int)Math.pow(-1,i)*i);
        }
        A.print();
        for (int i=0;i<5;i++){
            A.delete(i+10);
            A.insert(i+102);
        }
        A.print();
        for (int i=0;i<10;i++){
            A.backtrack();
            A.print();
        }
    }
}
