public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer size;                 //pointer for the next empty cell

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
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
        stack.push("insert");             //indicates the action to be done in case of backtrack
        arr[size] = x;
        size++;
    }


    @Override
    public void delete(Integer index) {
        if (index<size & index>=0) {                //index is legal
            stack.push(arr[index]);                 //keeping the value that will be deleted
            stack.push(index);                      //keeping the index of the cell which the value was inside
            stack.push("delete");             //indicates the action to be done in case of backtrack
            arr[index] = arr[size - 1];             //filling the new empty cell with the value from the last cell
            arr[size - 1] = 0;
            size--;
        }
    }

    @Override
    public Integer minimum() {
        if (size == 0)
            return -1;
        Integer output = 0;
        Integer curr = arr[0];
        for (int i=0; i<size; i++){
            if (arr[i] < curr){
                curr = arr[i];
                output = i;
            }
        }
        return output;
    }

    @Override
    public Integer maximum() {
        if (size == 0)
            return -1;
        Integer output = 0;
        Integer curr = arr[0];
        for (int i=0; i<size; i++){
            if (arr[i] > curr){
                curr = arr[i];
                output = i;
            }
        }
        return output;
    }

    @Override
    public Integer successor(Integer index){
        Integer curr = maximum();
        if ((index == curr) | size ==0 )                       //no succssesor for maxIndex or arr is empty
            return -1;
        for (int i = 0; i<size; i++) {
            if ((arr[i] > arr[index]) & (arr[i] < arr[curr])) {
                curr = i;
            }
        }
        return curr;
    }

    @Override
    public Integer predecessor(Integer index) {
        Integer curr = minimum();
        if ((index == curr) | size == 0)                 //no predeccessor for minValue or arr is empty
            return -1;
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
                size--;
            }
            else if (action.equals("delete")) {           //undo last deletion
                Integer index = (Integer) stack.pop();
                Integer value = (Integer) stack.pop();
                arr[size] = arr[index];                   //returns the value to the last cell
                arr[index] = value;                       //fill the empty cell with the deleted value
                size++;                                   //updating size
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

}
