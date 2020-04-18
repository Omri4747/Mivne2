import java.util.Arrays;

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int output = -1;
        int counter = 1; //counts the steps forward (without backwards)
        for (int i = 0; i < arr.length; i++, counter++) {
            myStack.push(i);
            if (arr[i] == x) {
                output = i; //value x was found
                break;
            } else if (counter % fd == 0) { //indicates the time to start the backwards loop
                for (int j = 0; !myStack.isEmpty() && j <= bk; j++)
                    i = (int) (myStack.pop());
            }
        }
        return output;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int output = -1;
        boolean found = false;
        int index = arr.length / 2;
        int low = 0;
        int high = arr.length - 1;
        while (!found & low <= high) {
            int step = isConsistent(arr);    //checks how many steps to go back
            if (step == 0) {                   //the array is consistent
                if (arr[index] == x) {       // value x was found
                    output = index;
                    found = true;
                }
                else if (arr[index] < x) {
                    myStack.push(low);        //push low index for future backtracking
                    low = index + 1;
                    index = (high + low) / 2;
                }
                else {
                    myStack.push(high);        //push high index for future backtracking
                    high = index - 1;
                    index = (low + high) / 2;
                }
            }
            else {                             //the array is not consistent
                for (int j = 0; !myStack.isEmpty() && j < step; j++) {
                    int i = (int) myStack.pop();
                    if (i < index)              // low was changed in the previous step
                        low = i;
                    else                       // high was changed in the previous step
                        high = i;
                    index = (high + low) / 2;
                }
            }
        }
        return output;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;
        if (res > 0)
            return (int) Math.round(res / 10);
        else
            return 0;
    }
}