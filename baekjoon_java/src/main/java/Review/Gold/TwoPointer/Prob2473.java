package Review.Gold.TwoPointer;

import java.io.*;
import java.util.*;

public class Prob2473 {

    static int n;
    static long[] liq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        liq = new long[n];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            liq[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(liq);

        long a = 0L;
        long b = 0L;
        long c = 0L;
        long minSum = 3_000_000_001L;
        for(int mid = 1; mid < n - 1; mid++){
            int left = 0;
            int right = n - 1;
            while(left < right){
                if(left >= mid || right <= mid){
                    break;
                }
                long sum = liq[left] + liq[mid] + liq[right];
                if(Math.abs(sum) < Math.abs(minSum)){
                    minSum = sum;
                    a = liq[left];
                    b = liq[mid];
                    c = liq[right];
                }

                if(sum == 0){
                    break;
                }else if(sum > 0){
                    right--;
                }else{
                    left++;
                }
            }
        }


        System.out.println(String.format("%d %d %d", a, b, c));

    }
}

/*
5
-99 0 1 1 99
answer = -99 0 99

5
-4 -2 -1 2 3
answer = -2 -1 3


10
739762262 811870375 6349594 125931388 -812966640 469406576 -134787694 993470627 -907859954 -989831001
 answer=-134787694 6349594 125931388

 */