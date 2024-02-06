package Gold.Basic.TwoPointer;

import java.io.*;
import java.util.*;

public class Prob2283 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n;
    static long k;

    static long[] tmp = new long[1_000_002];
    static int maxNumber = 0;

    public static void main(String args[]) throws IOException{
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        for(int idx = 0; idx < n; idx++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            tmp[start + 1] += 1;
            tmp[end + 1] += -1;
            maxNumber = Math.max(maxNumber, end);
        }

        for(int i = 1; i <= maxNumber; i++){
            tmp[i] += tmp[i-1];
        }

        int left = 0;
        int right = 0;
        long sum = 0;
        while(right <= maxNumber){
            // System.out.println("left=" + left + ", right=" + right + ", sum=" + sum);
            if(sum < k){
                right++;
                sum += tmp[right];
            }else if(sum > k){
                left++;
                sum -= tmp[left];
            }else{
                break;
            }
        }

        if(right > maxNumber){
            left = 0;
            right = 0;
        }
        System.out.println(String.format("%d %d", left, right));
    }
}