import java.util.Arrays;

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int output = -1;
        int counter = 1;
        for (int i=0;i<arr.length;i++,counter++){
            myStack.push(i);
            if (arr[i]==x) {
                output = i;
                break;
            }
            else if (counter%fd==0){
                for (int j = 0; !myStack.isEmpty() && j <= bk; j++)
                    i = (int) (myStack.pop());
                }
                System.out.println(i);
            }
        return output;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int output = -1;
        boolean found = false;
        int index =arr.length/2;
        int low=0;
        int high=arr.length-1;
//        Stack stepstack = new Stack();
        while(!found & low<=high){
            int step = isConsistent(arr);
            if (step==0) {
                myStack.push(index);
                if (arr[index] == x) {
                    output = index;
                    found = true;
                } else if (arr[index] < x) {
                    myStack.push(low);
                    low = index + 1;
                    index = (high + low) / 2;
                } else {
                    myStack.push(high);
                    high = index - 1;
                    index = (low + high) / 2;
                }
            }
            else{

                if (step+1>=index)
                    high = 2*index+low;
                else
                    low = 2*index-high;
                }
            }
        return output;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
     public static void main(String[] args) {
//        int[] arr = new int[100];
//        for (int i=0;i<arr.length;i++){
//            double res =Math.random()*100;
//            arr[i]= (int)res%100;
        int[] arr= {0,1,2,3,4,5,6,7,8,9,10};
        Stack myStack = new Stack();
         System.out.println(backtrackingSearch(arr,8,2,1,myStack));
    }
}
