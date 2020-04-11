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
        this.size = 0;
    }

    @Override
    public Integer get(int index) {
        return arr[index];
    }

    @Override
    public Integer search(int x) {
        Integer output = -1;
        if (x < minimum | x > maximum)
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
        arr[size] = x;
        size++;
        if (x < minimum) {//if x is new minimum
            minimum = x;
        }
        if (x > maximum) {//if x is new maximum
            maximum = x;
        }
    }


    @Override
    public void delete(Integer index) {
        stack.push(arr[index]);
        stack.push(minimum);
        stack.push(maximum);
        stack.push(index);
        stack.push("delete");
        Integer temp = arr[index];
        if (temp == minimum)
            minimum = successor(index);
        else if (temp == maximum) {
            maximum = predecessor(index);
        }
        arr[index] = arr[size - 1];
        arr[size - 1] = 0;
        size--;
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
        if (arr[index] == maximum) {
            throw new RuntimeException("maximum, none successor");
        }
        Integer curr = maximum;
        for (int i = 0; i < size; i++) {
            if ((arr[i] > arr[index]) & (arr[i] < curr)) {
                curr = arr[i];
            }
        }
        return curr;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (arr[index] == minimum()) {
            throw new RuntimeException("minimum, none predecessor");
        }
        Integer curr = minimum;
        for (int i = 0; i < size; i++) {
            if ((arr[i] < arr[index]) & (arr[i] > curr)) {
                curr = arr[i];
            }
        }
        return curr;
    }

    @Override
    public void backtrack() {
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
                System.out.println(arr[i]);
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
        System.out.println(A.predecessor(2));
        System.out.println(A.successor(A.minimum));
        }
    }

