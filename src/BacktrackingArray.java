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
        minimum = -1;
        maximum = size;
        this.size = 0;
    }

    @Override
    public Integer get(int index) {
        if (index>=size | index<0)
            return -1;
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        Integer output = -1;
        if (x<arr[minimum] | x>arr[maximum])
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
        stack.push(minimum);
        stack.push(maximum);
        stack.push("insert");
        if (size==0){
            arr[0]=x;
            minimum=0;
            maximum=0;
            size++;
        }
        else {
            arr[size] = x;
            size++;
            if (x < arr[minimum]) {//if x is the new minimum
                minimum = size - 1;
            }
            if (x > arr[maximum]) {//if x is the new maximum
                maximum = size - 1;
            }
        }
    }


    @Override
    public void delete(Integer index) {
        if (index<size & index>=0) {
            stack.push(arr[index]);
            stack.push(minimum);
            stack.push(maximum);
            stack.push(index);
            stack.push("delete");
            if (index == minimum)
                minimum = successor(index);
            else if (index == maximum) {
                maximum = predecessor(index);
            }
            arr[index] = arr[size - 1];
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
        if (index == maximum) {
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
        if (index == minimum()) {
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
        if (!stack.isEmpty()) {
            String action = (String) stack.pop();
            if (action.equals("insert")) {
                arr[size - 1] = 0;
                size--;
                maximum = (Integer) stack.pop();
                minimum = (Integer) stack.pop();
            } else if (action.equals("delete")) {
                Integer index = (Integer) stack.pop();
                maximum = (Integer) stack.pop();
                minimum = (Integer) stack.pop();
                Integer value = (Integer) stack.pop();
                arr[size] = arr[index];
                arr[index] = value;
                size++;
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

    public static void main(String[] args) {
        Stack st = new Stack();
        BacktrackingArray A = new BacktrackingArray(st, 20);
        for (int i = 0; i < 4; i++) {
            A.insert(i);
        }
        A.print();
    }
}
