package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 5(토마토)
 *
 * https://www.acmicpc.net/problem/7569
 *
 * Solution: BFS
 * 단순 BFS 문제
 * 다만, 탐색할 인접 좌표들이 위, 아래도 추가되어서 총 6개의 좌표를 탐색
 * 익지 않은 토마토의 갯수(unRipeCnt)에 따라서 모든 토마토가 익었는지 확인 가능
 */
public class Prob7569 {

    static int m, n, h;
    static int[][][] grid;
    static Deque<Tomato> queue = new ArrayDeque<>();

    static int unripeCnt = 0;
    static int[] dx = {1, -1, 0, 0, 0, 0};
    static int[] dy = {0, 0, 1, -1, 0, 0};
    static int[] dz = {0, 0, 0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        /*
        입력
         */
        grid = new int[h][n][m];
        for (int z = 0; z < h; z++) {
            for (int y = 0; y < n; y++) {
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < m; x++){
                    grid[z][y][x] = Integer.parseInt(st.nextToken());
                    if (grid[z][y][x] == 1) {
                        queue.offer(new Tomato(x, y, z, 0));
                    } else if (grid[z][y][x] == 0) {
                        unripeCnt++;
                    }
                }
            }
        }

        System.out.println(findAnswer());
    }

    public static int findAnswer(){
        int ans = 0;
        while (!queue.isEmpty()) {
            Tomato tomato = queue.poll();
            int x = tomato.x;
            int y = tomato.y;
            int z = tomato.z;
            int t = tomato.t;

            ans = Math.max(ans, t);
            for (int i = 0; i < 6; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nz = z + dz[i];

                if(nx < 0 || ny < 0 || nz < 0 || nx >= m || ny >= n || nz >= h){
                    continue;
                }

                /*
                인접한 칸이 익지 않은 토마토
                해당 경우에만 탐색을 하면 됨
                 */
                if (grid[nz][ny][nx] == 0) {
                    unripeCnt--;
                    grid[nz][ny][nx] = 1;
                    queue.offer(new Tomato(nx, ny, nz, t + 1));
                }
            }
        }

        return unripeCnt == 0 ? ans : -1;
    }

    public static class Tomato{
        int x;
        int y;
        int z;
        int t;

        public Tomato(int x, int y, int z, int t) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.t = t;
        }
    }
}
