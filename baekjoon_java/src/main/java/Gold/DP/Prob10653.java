package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 3(마라톤 2)
 *
 * https://www.acmicpc.net/problem/10653
 *
 * Solution: DP
 */
public class Prob10653 {

    static int n, k;
    static Point[] points;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        points = new Point[n + 1];
//        points[0] = new Point(1001, 1001);
        dp = new int[n + 1][k + 1];
        for(int i = 0; i < n + 1; i++){
            for(int j = 0; j < k + 1; j++){
                dp[i][j] = -1;
            }
        }

        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
        }

        System.out.println(dfs(n, k));
    }

    public static int dfs(int n, int k) {
        if (dp[n][k] != -1)
            return dp[n][k];

        if (n == 1)
            return 0;

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= k; i++) {
            if (1 <= n - 1 - i) {
                Point a = points[n - i - 1];
                Point b = points[n];
                ans = Math.min(ans, dfs(n - i - 1, k - i) + getDist(a, b));
            }
        }

        dp[n][k] = ans;
        return dp[n][k];
    }

    public static int getDist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
