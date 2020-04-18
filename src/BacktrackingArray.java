public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer minimum;              //pointer for the index of the minValue
    private Integer maximum;              //pointer for the index of the minValue
    private Integer size;                 //pointer for the next empty cell

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        minimum = -1;
        maximum = size;
        this.size = 0;
    }

    @Override
    public Integer get(int index) {
        if (index>=size | index<0)
            return null;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        Integer output = -1;
        if (x<arr[minimum] | x>arr[maximum])       //value is not in the array
            return output;
        for (int i = 0; i < size; i++) {
            if (arr[i] == x) {
                output = i;
                break;
            }
        }
        return output;
    }

    @Override
    public void insert(Integer x) {
        stack.push(minimum);                    //keeping the current minIndex
        stack.push(maximum);                    //keeping the current maxIndex
        stack.push("insert");             //indicates the action to be done in case of backtrack
        if (size==0){                           //if array is empty
            arr[0]=x;
            minimum=0;
            maximum=0;
            size++;
        }
        else {                                   //array is not empty
            arr[size] = x;                       //inserting x to the first empty cell in the array
            size++;
            if (x < arr[minimum]) {              //if x is the new minimum
                minimum = size - 1;
            }
            if (x > arr[maximum]) {              //if x is the new maximum
                maximum = size - 1;
            }
        }
    }


    @Override
    public void delete(Integer index) {
        if (index<size & index>=0) {                //index is legal
            stack.push(arr[index]);                 //keeping the value that will be deleted
            stack.push(minimum);                    //keeping current minIndex
            stack.push(maximum);                    //keeping current maxIndex
            stack.push(index);                      //keeping the index of the cell which the value was inside
            stack.push("delete");             //indicates the action to be done in case of backtrack
            if (index == minimum)
                minimum = successor(index);
            else if (index == maximum) {
                maximum = predecessor(index);
            }
            arr[index] = arr[size - 1];             //filling the new empty cell with the value from the last cell
            arr[size - 1] = 0;
            size--;
        }
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
        if (index == maximum) {                     //no successor for maxValue
            return -1;
        }
        Integer curr = maximum;
        for (int i = 0; i < size; i++) {
            if ((arr[i] > arr[index]) & (arr[i] < arr[curr])) {
                curr = i;
            }
        }
        return curr;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index == minimum()) {                   //no predeccessor for minValue
            return -1;
        }
        Integer curr = minimum;
        for (int i = 0; i < size; i++) {
            if ((arr[i] < arr[index]) & (arr[i] > arr[curr])) {
                curr = i;
            }
        }
        return curr;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {                          //ensures that action(insert or delete) was applied before
            String action = (String) stack.pop();
            if (action.equals("insert")) {               //undo last insertion
                arr[size - 1] = 0;                       //in each insertion we add the value to the last index
                //updating 3 fields:
                size--;
                maximum = (Integer) stack.pop();
                minimum = (Integer) stack.pop();
            }
            else if (action.equals("delete")) {           //undo last deletion
                Integer index = (Integer) stack.pop();
                //updating minIndex & maxIndex
                maximum = (Integer) stack.pop();
                minimum = (Integer) stack.pop();
                Integer value = (Integer) stack.pop();
                arr[size] = arr[index];                   //returns the value to the last cell
                arr[index] = value;                       //fill the empty cell with the deleted value
                size++;                                   //updating size
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
}
