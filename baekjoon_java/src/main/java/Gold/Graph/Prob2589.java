package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/2589
 *
 * Solution: BFS + Brute Force
 * 1. S -> D 까지의 최단 거리를 구하기 위해서 BFS 사용 (DFS 는 항상 최단 거리를 보장하지 않음)
 * 2. 지도를 (0,0) 부터 마지막 좌표까지 탐색하면서, 'L' 인 경우에 해당 좌표부터 도달 가능한 모든 'L' 들 까지의 최단 거리 구하기
 * 3. 최단거리 구하는 과정에서 ans 의 값을 구한 값중 최댓값으로 업데이트 시키면서 ans 구하기
 */
public class Prob2589 {

    static int r;
    static int c;
    static char[][] grid;
    static boolean[][] visited;

    static Integer[] tx = {1, -1, 0, 0};
    static Integer[] ty = {0, 0, 1, -1};

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        grid = new char[r][c];
        for (int y = 0; y < r; y++) {
            String str = br.readLine();
            for (int x = 0; x < c; x++) {
                grid[y][x] = str.charAt(x);
            }
        }

        for (int y = 0; y < r; y++) {
            for (int x = 0; x < c; x++) {
                if (grid[y][x] == 'L') {
                    visited = new boolean[r][c];
                    bfs(x, y, 0);
                }
            }
        }

        System.out.println(ans);
    }

    static void bfs(int x, int y, int cost) {
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y, 0));
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            Pos cur = queue.poll();
            int curX = cur.x;
            int curY = cur.y;
            int curCost = cur.cost;

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 0 && nx < c && ny >= 0 && ny < r) {
                    if (grid[ny][nx] == 'L' && visited[ny][nx] == false) {
                        visited[ny][nx] = true;
                        ans = Math.max(ans, curCost + 1);
                        queue.offer(new Pos(nx, ny, curCost + 1));
                    }
                }
            }

        }
    }

    static class Pos{
        int x;
        int y;
        int cost;

        public Pos(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
