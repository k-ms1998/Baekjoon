package Gold.DP;

import java.util.Scanner;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/2133
 *
 * Solution: DP(점화식):
 * dp[n] = dp[n-2] * dp[2] + 2*dp[n-4] + 2*dp[n-6] + .... + 2*dp[0]
 */
public class Prob2133 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int ans = 0;
        int[] dp = new int[n + 1];
        int[] dpSum = new int[n + 1];
        if (n % 2 == 0) {
            /**
             * f(2) = 3, f(4) = 11, f(6) = 41
             */
            dp[0] = 1;
            dpSum[0] = 2;
            if (n >= 2) {
                dp[2] = 3;
                dpSum[2] = 8;
            }
//            if(n >= 4){
//                dp[4] = 11;
//                dpSum[4] = 30;
//            }
            for (int i = 4; i < n + 1; i += 2) {
                /**
                 * dp[6] = dp[4]*dp[2] + 2*dp[2] + 2*d[0]
                 *       = 33 + 6 + 2
                 *       = 41
                 */
                dp[i] = dp[i - 2] * dp[2] + dpSum[i - 4];
                dpSum[i] = 2 * dp[i] + dpSum[i - 2];
            }
            ans = dp[n];
        }

        System.out.println(ans);
    }
}
