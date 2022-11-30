package Review.Silver.DP;

import java.io.*;
import java.util.*;

/**
 * Silver 1(RGB 거리)
 *
 * https://www.acmicpc.net/problem/1149
 *
 * Solution: DP
 */
public class Prob1149 {

    static int n;
    static int[][] num;
    static int[][] dp;

    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new int[n + 1][3];
        dp = new int[n + 1][3];
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                num[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = INF;
            }
        }

        /**
         * dp[n][0] -> n 번째 집을 빨간색으로 칠했을때
         * dp[n][1] -> n 번째 집을 초록색으로 칠했을때
         * dp[n][2] -> n 번째 집을 파란색으로 칠했을때
         */
        dp[1][0] = num[1][0];
        dp[1][1] = num[1][1];
        dp[1][2] = num[1][2];
        for (int i = 2; i < n + 1; i++) {
            /**
             * dp[i][0] -> i번째 집을 빨간색으로 칠할려고 할때 최솟값
             *  => 이때, i-1번째 집이 빨간색이면 안되기 때문에, (i-1번째 집을 초록색 또는 파란색으로 칠하고) + (i 번째 집을 빨간색으로 칠했을때) 의 최솟값
             */
            dp[i][0] = Math.min(dp[i-1][1] + num[i][0], dp[i-1][2] + num[i][0]);
            dp[i][1] = Math.min(dp[i-1][0] + num[i][1], dp[i-1][2] + num[i][1]);
            dp[i][2] = Math.min(dp[i-1][0] + num[i][2], dp[i-1][1] + num[i][2]);
        }

        System.out.println(Math.min(dp[n][0], Math.min(dp[n][1], dp[n][2])));
    }
}
