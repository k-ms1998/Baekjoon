package Gold.DP;

import java.util.*;
import java.io.*;

/**
 * Gold 3(내리막 길)
 *
 * https://www.acmicpc.net/problem/1520
 *
 * Solution: DP
 */

public class Prob1520 {

    static int n;
    static int m;

    static int[][] grid;
    static int[][] dp;
    static boolean[][] visited;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        dp = new int[n][m];
        visited = new boolean[n][m];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);
        System.out.println(dp[0][0]);
    }

    public static int dfs(int x, int y){
        if(x == m - 1 && y == n - 1){
            return 1;
        }

        if(visited[y][x]){
            return dp[y][x];
        }

        visited[y][x] = true;
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx < 0 || nx >= m || ny < 0 || ny >= n){
                continue;
            }

            if(grid[ny][nx] < grid[y][x]){
                dp[y][x] += dfs(nx, ny);
            }
        }

        return dp[y][x];
    }
}