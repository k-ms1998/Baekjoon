package Gold.BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/14500
 *
 * Solution: Brute Force + DFS
 *
 */
public class Prob14500 {

    static int n;
    static int m;
    static Integer[][] grid;
    static boolean[][] visited;

    static int[] tx = {1, -1, 0, 0};
    static int[] ty = {0, 0, 1, -1};

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new Integer[n][m];
        visited = new boolean[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        /**
         * 각 좌표를 탐색하면서, 각 좌표에 놓을 수 있는 모든 테트로미노를 놓고, 놓인 칸에 쓰인 수들의 합 구하기
         */
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                visited[y][x] = true;
                dfs(x, y, 1, grid[y][x]);
                visited[y][x] = false;
            }
        }

        System.out.println(ans);
    }

    static void dfs(int x, int y, int depth, int cnt) {
        if (depth == 4) {
            ans = Math.max(ans, cnt);
            return;
        }

        /**
         * 각 테트로미노는 정사각형 4개를 이용해서 만들고, 어떤 식으로 회전을 하더라도 현재 좌표에서 상하좌우로 움직여서 만드는 것
         * 단, (ㅜ, ㅓ, ㅗ, ㅏ) 모양의 테트로미노는 따로 만드는 과정이 필요 ( depth == 2 일때 )
         */
        for (int i = 0; i < 4; i++) {
            int nx = x + tx[i];
            int ny = y + ty[i];

            if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                if (visited[ny][nx] == false) {
                    visited[ny][nx] = true;
                    dfs(nx, ny, depth + 1, cnt + grid[ny][nx]);
                    if(depth == 2){
                        dfs(x, y, depth + 1, cnt + grid[ny][nx]);
                    }
                    visited[ny][nx] = false;
                }
            }
        }

    }
}
