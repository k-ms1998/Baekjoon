package Silver.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Silver 1
 *
 * 문제
 * N×M크기의 배열로 표현되는 미로가 있다.
 * 1	0	1	1	1	1
 * 1	0	1	0	1	0
 * 1	0	1	0	1	1
 * 1	1	1	0	1	1
 * 미로에서 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다.
 * 이러한 미로가 주어졌을 때, (1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오.
 * 한 칸에서 다른 칸으로 이동할 때, 서로 인접한 칸으로만 이동할 수 있다.
 * 위의 예에서는 15칸을 지나야 (N, M)의 위치로 이동할 수 있다. 칸을 셀 때에는 시작 위치와 도착 위치도 포함한다.
 *
 * 입력
 * 첫째 줄에 두 정수 N, M(2 ≤ N, M ≤ 100)이 주어진다. 다음 N개의 줄에는 M개의 정수로 미로가 주어진다. 각각의 수들은 붙어서 입력으로 주어진다.
 *
 * 출력
 * 첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.
 *
 * Solution: BFS + DP
 */
public class Prob2178 {

    static Integer[][] grid;
    static Integer[][] visited;
    static int n;
    static int m;

    static Integer[] tx = {1, -1, 0, 0};
    static Integer[] ty = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new Integer[n + 1][m + 1];
        visited = new Integer[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            String currentRow = br.readLine();
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = currentRow.charAt(x-1) - '0';
                visited[y][x] = Integer.MAX_VALUE;
            }
        }

        bfs();
        System.out.println(visited[n][m]);
    }

    public static void bfs() {
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(1, 1));
        visited[1][1] = 1;

        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();
            int curX = curPos.x;
            int curY = curPos.y;
            int cnt = visited[curY][curX];

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 1 && nx <= m && ny >= 1 && ny <= n) {
                    if (grid[ny][nx] == 1) {
                        if (visited[ny][nx] > cnt + 1) {
                            visited[ny][nx] = cnt + 1;
                            queue.offer(new Pos(nx, ny));
                        }
                    }
                }
            }
        }
    }

    static class Pos{
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
