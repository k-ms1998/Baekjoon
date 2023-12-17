package Review.Gold.TwoPointer;

import java.io.*;
import java.util.*;

public class Prob1806 {

    static int n, s;
    static long[] sums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        sums = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            sums[i] = sums[i - 1] + Integer.parseInt(st.nextToken());
        }

        int left = 0;
        int right = n;
        while(left <= right){
            int mid = (left + right) / 2;
            if(check(mid)){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        System.out.println(left > n ? 0 : left);
    }

    public static boolean check(int size) {
        for(int i = 1; i < n + 1; i++){
            int j = i + size - 1;
            if(j > n){
                return false;
            }

            long cur = sums[j] - sums[i - 1];
            if(cur >= s){
                return true;
            }
        }

        return false;
    }
}
/*
10 100000
1 2 3 4 5 6 7 8 9 10
 */
