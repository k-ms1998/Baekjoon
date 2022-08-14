package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/9084
 *
 * Solution: DP
 */
public class Prob9084 {

    static int n;
    static int[] coins;
    static int target;

    static int[] dp;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            n = Integer.parseInt(br.readLine());

            coins = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                coins[j] = Integer.parseInt(st.nextToken());
            }

            target = Integer.parseInt(br.readLine());

            dp = new int[target + 1];

            /**
             * ex: coins = [2, 3], target = 6
             * dp:           0 1 2 3 4 5 6
             * 초기:          1 0 0 0 0 0 0
             * coins[0](==2):1 0 1 0 1 0 1   => dp[2] = dp[2] + dp[0] = 1; dp[4] = dp[4] + dp[2] -> (dp[4] 는 현재 4를 만들 수 있는 방법)+ (2에 coins[0]을 추가해서 만들 수 있는 방법)
             * coins[1](==4):1 0 1 0 2 0 2   => dp[6] = dp[6] + dp[2] -> (현재 6을 만들 수 있는 방법 + 2에 coins[1] 를 하나 더 추가해서 만들 수 있는 방법)
             *                                                          -> 현재 coin 은 4 이므로, 4를 이용해서 6을 만들려면 2가 필요한 상황. 즉, 2를 만들 수 있는 방법에 4를 추가하면 6이 되는 상황
             *                                                             -> 그러므로, 2를 만들 수 있는 방법을 추가해주면 됨
             */
            dp[0] = 1;
            for (int c = 0; c < n; c++) {
                int coin = coins[c];
                for (int d = 0; d < target + 1; d++) {
                    if (coin <= d) {
                        dp[d] += dp[d -coin];
                    }
                }
            }

            ans.append(dp[target] + "\n");
        }

        System.out.println(ans);
    }
}
