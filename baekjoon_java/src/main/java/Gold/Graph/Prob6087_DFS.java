package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3 (레이저 통신)
 *
 * https://www.acmicpc.net/problem/6087
 *
 * Solution: DFS
 * But, 시간이 너무 오래걸림 -> 다익스트라로 풀이
 */
public class Prob6087_DFS {

    static int w, h;
    static char[][] grid;
    static int[][] dp;
    static int x1, y1, x2, y2;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        grid = new char[h][w];
        dp = new int[h][w];
        int cCount = 0;
        for (int y = 0; y < h; y++) {
            String curRow = br.readLine();
            for (int x = 0; x < w; x++) {
                grid[y][x] = curRow.charAt(x);
                dp[y][x] = Integer.MAX_VALUE;
                if(grid[y][x] == 'C'){
                    if (cCount == 0) {
                        x1 = x;
                        y1 = y;
                    } else {
                        x2 = x;
                        y2 = y;
                    }
                    cCount++;
                }
            }
        }
//        System.out.println("x1 = " + x1 + ", y1 = " + y1);
//        System.out.println("x2 = " + x2 + ", y2 = " + y2);

        dp[y1][x1] = 0;
        dfs(x1, y1, 0, -1);
        System.out.println(ans);
    }

    public static void dfs(int x, int y, int cnt, int dir) {
        if (x == x2 && y == y2) {
            ans = Math.min(ans, cnt);
            return;
        }
        if (dp[y][x] < cnt) {
            return;
        }
        dp[y][x] = Math.min(dp[y][x], cnt);
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= w || ny < 0 || ny >= h) {
                continue;
            }
            if (grid[ny][nx] == '*') {
                continue;
            }
            dfs(nx, ny, dir == i || dir == -1 ? cnt : cnt + 1, i);
        }
    }
}
