package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(말이 되고픈 원숭이)
 *
 * https://www.acmicpc.net/problem/1600
 *
 * Solution: BFS
 * 3차원 배열로 (x, y)좌표에 k번의 말 움직임으로 이동해서 도달 했는지 안했는지 판별하는 visited 를 이용해서 풀이(visited[y][x][k])
 */
public class Prob1600 {

    static int k;
    static int w, h;
    static int[][] grid;
    static boolean[][][] visited;

    /*
     * dx, dy: 인접한 칸으로 움직일 수 있는 좌표
     */
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    /*
     * hx, hy: 말의 움직임으로 움직일 수 있는 좌표
     */
    static int[] hx = {1, 2, 2, 1, -1, -2, -2, -1};
    static int[] hy = {-2, -1, 1, 2, 1, 2, -1, -2};
    static final int INF = 100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        k = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        grid = new int[h][w];
        visited = new boolean[h][w][k + 1];
        for (int y = 0; y < h; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < w; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(findRoute());
    }

    /**
     * BFS 로 도착지점까지 최단으로 걸리는 시간 계산
     * 이때, 방문한 지점을 기록하는 visited 는 3차원 배열
     * visited[y][x][k] -> (x, y) 좌표에서 말의 이동 방법을 k번 사용해서 방문했는지 안했는지 기록
     *
     */
    public static int findRoute() {
        Deque<Monkey> queue = new ArrayDeque<>();
        queue.offer(new Monkey(0, 0, 0, 0));
        visited[0][0][0] = true;
        int ans = INF;

        while (!queue.isEmpty()) {
            Monkey monkey = queue.poll();
            int x = monkey.x;
            int y = monkey.y;
            int cnt = monkey.cnt;
            int dist = monkey.dist;

            /**
             * 도착지점에 도달할때마다 ans 업데이트
             */
            if (x == w - 1 && y == h - 1) {
                ans = Math.min(ans, dist);
                continue;
            }

            /**
             * 원숭이가 인접한 칸으로 움직일때
             */
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                    if (grid[ny][nx] != 1) {
                        if (!visited[ny][nx][cnt]) {
                            visited[ny][nx][cnt] = true;
                            queue.offer(new Monkey(nx, ny, cnt, dist + 1));
                        }
                    }
                }
            }
            /**
             * 원숭이가 말의 움직임으로 움직일때
             */
            if (cnt < k) {
                for (int i = 0; i < 8; i++) {
                    int nx = x + hx[i];
                    int ny = y + hy[i];

                    if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                        if (grid[ny][nx] != 1) {
                            if (!visited[ny][nx][cnt + 1]) {
                                visited[ny][nx][cnt + 1] = true;
                                queue.offer(new Monkey(nx, ny, cnt + 1, dist + 1));
                            }
                        }
                    }
                }
            }
        }

        /*
        ans 가 INF 이면 도착지점에 도달 불가능
         */
        return ans != INF ? ans : -1;
    }

    public static class Monkey{
        int x;
        int y;
        int cnt;
        int dist;

        public Monkey(int x, int y, int cnt, int dist) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.dist = dist;
        }
    }
}
