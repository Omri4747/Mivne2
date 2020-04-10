public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int output=-1;
        int counter =0;
        for (int i=0;i<arr.length;i++){
            myStack.push(arr[i]);
            if (arr[i]==x) {
                output = arr[i];
                break;
            }
            else if ((i+counter)%fd==0){
               i=i-bk;
               for (int j=0;!myStack.isEmpty()&&j<bk;j++){
                   myStack.pop();
               }
            }
        }
        return output;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        // TODO: implement your code here
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}
