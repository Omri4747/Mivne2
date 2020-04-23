public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer size;       //pointer to the next empty cell

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.size=0;
    }
    @Override
    public Integer get(int index){
        if (!rangecheck(index))          //if index is illegal
            return null;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        int output = -1;
        boolean found = false;
        int middle = size/2;
        int low = 0;
        int high = size-1;
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
        stack.push(toInsert(x));         //using the assistance function below, & pushing the index that returns from it
        stack.push("insert");
    }

    //sorting a sorted array with 1 element unsorted at the end of the array
    // and return the index it was inserted
    private int toInsert(Integer x){
        arr[size]=x;                          //adding the value to the end of the array
        size++;                               //updating size
        int i=size-1;
        while (i>0 && x<arr[i-1]){            //finds the correct cell to insert x
            arr[i]=arr[i-1];                  //moves all the elements one cell to the right
            arr[i-1]=x;
            i--;
        }
        return i;
    }

    @Override
    public void delete(Integer index) {
        if (rangecheck(index)) {              //checks index is legal
            stack.push(arr[index]);           //push the value that will be deleted
            stack.push("delete");
            toDelete(index);                  //using the assistance function below
        }
    }

    private void toDelete (Integer index){
        for (int i = index; i < size - 1; i++) {    //moves all the elements from the right side of value arr[index], one cell to the left
            arr[i] = arr[i + 1];
        }
        arr[size - 1] = 0;                          //deleting the duplicated value that created in the last two cells
        size--;                                     //updating size
    }

    @Override
    public Integer minimum() {
        if (size==0)            //empty array
            return -1;
        else
            return 0;          //minimum is always in cell 0 in a sorted array
    }

    @Override
    public Integer maximum() {
        if (size==0)           //empty array
            return -1;
        else
            return size-1;    //maximum is always in the last cell in a sorted array
    }

    @Override
    public Integer successor(Integer index) {
        Integer output = -1;
        //checks legal index, successor of maximum, there are more than one occurrence of maximum
        if (!rangecheck(index) || (index==size-1) | arr[index]==arr[size-1])
            return output;

        for (int i=index+1;i<size;i++){     //finds the first occurrence of value different than arr[index]
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
        //checks legal index, predecessor of minimum, there are more than one occurrence of minimum
        if (!rangecheck(index) || (index==0) | arr[index]==arr[0])
            return output;
        for (int i=index-1;i>=0;i--){        //finds the first occurrence of value different than arr[index]
            if (arr[index]>arr[i]) {
                output = i;
                break;
            }
        }
        return output;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {                     //ensures that action(insert or delete) was applied before
            Object action = stack.pop();
            Integer value = (Integer) stack.pop();
            if (action.equals("delete")) {
                toInsert(value);                    //backtracking delete is inserting, using assistance function
            }
            else if (action.equals("insert")) {
                toDelete(value);                    //backtracking insert is deleting, using assistance function
            }
            System.out.println("backtracing performed");
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

    //assistance function to ensure index is legal
    private boolean rangecheck(int index){
        return (index >=0 & index<size);
    }

}
