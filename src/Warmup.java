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
        int from=0;
        int to=arr.length-1;
        Stack stepstack = new Stack();
        while(!found & from<=to){
            if (isConsistent(arr)==0) {
                myStack.push(index);
                if (arr[index] == x) {
                    output = index;
                    found = true;
                } else if (arr[index] < x) {
                    from = index + 1;
                    stepstack.push(from);
                    index = (from + to) / 2;
                } else {
                    to = index - 1;
                    stepstack.push(to);
                    index = (from + to) / 2;
                }
            }
            else{
                int step=0;
                if (!stepstack.isEmpty()) {
                    step = (int) stepstack.pop();
                }
                if (!myStack.isEmpty())
                    index = (int) myStack.pop();
                if (step+1>=index)
                    to = 2*index+from;
                else
                    from = 2*index-to;
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
