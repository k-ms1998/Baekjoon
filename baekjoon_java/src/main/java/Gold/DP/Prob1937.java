package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 3(욕심쟁이 판다)
 *
 * https://www.acmicpc.net/problem/1937
 *
 * Solution: DP + DFS
 * Brute Force 로 풀이시 시간초과 발생 -> 그러므로, DP로 풀이
 */
public class Prob1937 {

    static int n;
    static int[][] grid;
    static int[][] dp;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        dp = new int[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                int cost = Integer.parseInt(st.nextToken());
                grid[y][x] = cost;
                dp[y][x] = 1;
            }
        }

        for (int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++){
                if (dp[y][x] > 1) {
                    continue;
                }
                ans = Math.max(ans, dfs(x, y));
            }
        }

//        printGrid(dp);
        System.out.println(ans);
    }

    /**
     * (x, y)로 부터 시작해서, 최대로 움직일 수 있는 칸을 dp[y][x]에 저장
     * 그러므로, dp[y][x]에는 항상 (x, y)에서 시작해서 최대로 움직일 수 있는 칸의 수를 저장
     * ex:
     * grid : 5 10   => dp: 3 2
     *        1 15          4 1
     */
    public static int dfs(int x, int y) {
        /**
         * dp[y][x] > 1 이면 이미 (x,y) 좌표를 탐색했었음
         * 탐색을 한번 거친 상태이면, dp[y][x]에는 (x,y)로 부터 시작해서 최대로 움직일 수 있는 칸의 수가 이미 저장되어 있음
         * => 그러므로, 또 탐색할 필요 X
         */
        if (dp[y][x] > 1) {
            return dp[y][x];
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                continue;
            }

            if (grid[ny][nx] > grid[y][x]) {
                /**
                 * dp[y][x] = Math.max(dp[y][x], dfs(nx, ny) + 1)
                 * => dp[y][x]에는 (x, y)에서 시작해서 최대로 움직일 수 있는 칸을 나타냄
                 *  dp[ny][nx] + 1 은 (x, y)에서 (nx, ny)로 이동하고, (nx, ny) 로 부터 최대로 움직일 수 있는 칸
                 *  dp[y][x] = dp[nx1][ny1] + 1
                 *   -> dp[nx1][ny1] = dp[nx2][ny2] + 1
                 *       -> dp[nx2][ny2] = dp[nx3][ny3] + 1
                 *          ......
                 */
                dp[y][x] = Math.max(dp[y][x], dfs(nx, ny) + 1);
            }
        }

        return dp[y][x];
    }

    public static void printGrid(int[][] g) {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                System.out.print(g[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("---------- ----------");
    }
}
