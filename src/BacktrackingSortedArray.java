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
            throw new ArrayIndexOutOfBoundsException();
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        int output = -1;
        boolean found = false;
        int index = size / 2;
        int low = 0;
        int high = size - 1;
        while (!found & low <= high) {
            if (arr[index] == x) {
                output = index;
                found = true;
            } else if (arr[index] < x) {        //x is in the right part of the index
                low = index + 1;
                index = (high + low) / 2;
            } else {                            //x is in the left part of the index
                high = index - 1;
                index = (low + high) / 2;
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
        int index = search(x);
        if (index==-1){             //array doesn't contain x
            stack.push(stored);
            stack.push("insert");
            arr[size]=x;
            size=size+1;
            bubblesort(arr);
        }
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
        if (!rangecheck(index))
            throw new ArrayIndexOutOfBoundsException();
        int[] stored = new int[arr.length];
        for (int i=0;i<size;i++){
            stored[i]=arr[i];
        }
        stack.push(stored);
        stack.push("delete");
        for (int i=index;i<size-1;i++){
            arr[i]=arr[i+1];
        }
        size=size-1;
    }

    @Override
    public Integer minimum() {
        return arr[0];
    }

    @Override
    public Integer maximum() {
        return arr[size-1];
    }

    @Override
    public Integer successor(Integer index) {
        if (index==size-1)
            throw new IllegalArgumentException("no successor");
        return arr[index+1];
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index==0)
            throw new IllegalArgumentException("no predecessor");
        return arr[index-1];
    }

    @Override
    public void backtrack() {
        Object what = stack.pop();
        Object previousarr = stack.pop();
        if (what.equals("delete")){
            size=size+1;
            arr=(int[])previousarr;
        }
        else if (what.equals("insert")){
            size=size-1;
            arr=(int[])previousarr;
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
        System.out.println(toprint);
    }

    private boolean rangecheck(int index){
        return (index >=0 & index<size);
    }

    public static void main(String[] args) {
        Stack st = new Stack();
        BacktrackingSortedArray A = new BacktrackingSortedArray(st,100);
        for (int i=0;i<100;i++){
            A.insert((i*22)%100);
        }
        A.print();
        A.delete(5);
        A.print();
        A.backtrack();
        A.print();
    }
}
