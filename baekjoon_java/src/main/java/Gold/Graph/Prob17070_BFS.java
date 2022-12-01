package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 5(파이프 옮기기 1)
 * 
 * https://www.acmicpc.net/problem/17070
 * 
 * Solution: BFS
 * But, 시간 단축하기 위해서는 DP로 풀이
 */
public class Prob17070_BFS {

    static int n;
    static int[][] grid;
    static int[][] dp;

    static int[] dx = {1, 1, 0};
    static int[] dy = {0, 1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        dp = new int[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }
        dp[0][0] = 1;
        bfs();
        System.out.println(dp[n-1][n-1]);
    }

    public static void bfs() {
        Deque<Pipe> queue = new ArrayDeque<>();
        queue.offer(new Pipe(1, 0, 0));

        while (!queue.isEmpty()) {
            Pipe pipe = queue.poll();
            int x = pipe.x;
            int y = pipe.y;
            int dir = pipe.dir;

            dp[y][x]++;

            for (int i = 0; i < 3; i++) {
                if ((dir == 0 && i == 2) || (dir == 2 && i == 0)) {
                    continue;
                }

                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                    continue;
                }

                if (i == 0 || i == 2) {
                    if (grid[ny][nx] == 1) {
                        continue;
                    }
                }else{
                    if (grid[ny][nx] == 1 || grid[y][nx] == 1 || grid[ny][x] == 1) {
                        continue;
                    }
                }

                queue.offer(new Pipe(nx, ny, i));
            }
        }
    }

    public static class Pipe {
        int x;
        int y;
        int dir;

        public Pipe(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Pipe{" +
                    "x=" + x +
                    ", y=" + y +
                    ", dir=" + dir +
                    '}';
        }
    }
}
