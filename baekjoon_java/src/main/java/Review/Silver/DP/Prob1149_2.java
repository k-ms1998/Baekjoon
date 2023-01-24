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
public class Prob1149_2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] grid = new int[n][3];
        int[][] dp = new int[n][3];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            grid[i][0] = Integer.parseInt(st.nextToken());
            grid[i][1] = Integer.parseInt(st.nextToken());
            grid[i][2] = Integer.parseInt(st.nextToken());
        }
        dp[0][0] = grid[0][0];
        dp[0][1] = grid[0][1];
        dp[0][2] = grid[0][2];

        for(int i = 1; i < n; i++){
            dp[i][0] = grid[i][0] + Math.min(dp[i-1][1], dp[i-1][2]);
            dp[i][1] = grid[i][1] + Math.min(dp[i-1][0], dp[i-1][2]);
            dp[i][2] = grid[i][2] + Math.min(dp[i-1][0], dp[i-1][1]);
        }

        System.out.println(Math.min(dp[n-1][0], Math.min(dp[n-1][1], dp[n-1][2])));
    }
}
