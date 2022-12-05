package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 5(파이프 옮기기 1)
 *
 * https://www.acmicpc.net/problem/17070
 *
 * Solution: DP
 */
public class Prob17070_DP {

    static int n;
    static int[][] grid;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n + 1][n + 1];
        dp = new int[n + 1][n + 1][3];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        dp[1][2][0] = 1;
        for (int y = 1; y < n + 1; y++) {
            for (int x = 3; x < n + 1; x++) {
                if (grid[y][x] == 1) {
                    continue;
                }
                /**
                 * dp[y][x][0]: (x, y) 좌표에서 가로로 파이프를 놓을때
                 *  => 가로로 놓는 것이면, 파이프의 시작 위치인 (x-1, y) 에서 가로로 놓는 것
                 *      => 그러므로, (x-1, y)에서 가로 또는 대각선으로 놓였을 경우 (x, y)에 가로로 놓을 수 있음
                 *          => dp[y][x][0] = dp[y][x-1][0] + dp[y][x-1][1]
                 *
                 * dp[y][x][1]: (x, y) 좌표에서 대각선으로 파이프를 놓을때
                 *  => 가로로 놓는 것이면, 파이프의 시작 위치인 (x-1, y-1) 에서 대각선으로 놓는 것
                 *      => 그러므로, (x-1, y-1)에서 가로, 세로, 대각선으로 놓였을 경우 (x, y)에 대각선으로 놓을 수 있음
                 *          => dp[y][x][1] = dp[y-1][x-1][0] + dp[y-1][x-1][1] + dp[y-1][x-1][2]
                 *
                 * dp[y][x][2]: (x, y) 좌표에서 세로로 파이프를 놓을때
                 *  => 가로로 놓는 것이면, 파이프의 시작 위치인 (x, y-1) 에서 세로로 놓는 것
                 *      => 그러므로, (x, y-1)에서 세로 또는 대각선으로 놓였을 경우 (x, y)에 세로로 놓을 수 있음
                 *          => dp[y][x][2] = dp[y-1][x][1] + dp[y-1][x][2]
                 */
                dp[y][x][0] = dp[y][x - 1][0] + dp[y][x - 1][1];
                dp[y][x][2] = dp[y - 1][x][1] + dp[y - 1][x][2];
                if(grid[y-1][x] != 1 && grid[y][x-1] != 1){
                    dp[y][x][1] = dp[y-1][x - 1][0] + dp[y - 1][x - 1][1] + dp[y - 1][x-1][2];
                }
            }
        }

        System.out.println(dp[n][n][0] + dp[n][n][1] + dp[n][n][2]);
    }
}
