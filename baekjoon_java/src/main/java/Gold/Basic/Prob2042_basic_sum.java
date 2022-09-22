package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(구간 합 구하기)
 *
 * https://www.acmicpc.net/problem/2042
 *
 * Solution: 누적합
 */
public class Prob2042_basic_sum {

    static int n;
    static int m;
    static int k;

    static long[] num;
    static long[] dp;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        num = new long[n + 1];
        dp = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(br.readLine());
            dp[i] = dp[i - 1] + num[i];
        }
        for(int i = 0; i < m + k; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());

            if(a == 1){
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());

                long diff = c - num[b];
                num[b] = c;
                for(int j = b; j < n + 1; j++){
                    dp[j] += diff;
                }
            }else{
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                long sum = dp[c] - dp[b - 1];
                ans.append(sum).append("\n");
            }
        }

        System.out.println(ans);
    }
}
