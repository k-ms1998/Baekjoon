package Silver.DP;

import java.util.List;
import java.util.Scanner;

/**
 * Silver 1
 *
 * Solution: DP
 */
public class Prob2156 {

    static int n;
    static int[] wines;
    static int[] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        wines = new int[n];
        dp = new int[n];
        for (int i = 0; i < n; i++) {
            int wine = sc.nextInt();
            wines[i] = wine;
            dp[i] = wine;
        }

        if(n > 1){
            dp[1] = wines[0] + wines[1];
            if (n > 2) {
                dp[2] = Math.max(dp[1], Math.max(dp[0] + wines[2], wines[1] + wines[2]));
            }

            for (int i = 3; i < n; i++) {
                /**
                 * 1. OOX
                 * 2. OXO
                 * 3. XOO
                 */
                dp[i] = Math.max(dp[i - 1], Math.max(dp[i-2] + wines[i], dp[i-3] + wines[i-1] + wines[i]));
            }

        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (ans < dp[i]) {
                ans = dp[i];
            }
        }
        System.out.println(ans);
    }
}
