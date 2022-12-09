package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(벽 부수고 이동하기 2)
 *
 * https://www.acmicpc.net/problem/14442
 *
 * Solution: BFS
 * Problem 1600(말이 되고픈 원숭이)와 매우 유사한 문제
 * BFS로 풀이해서 최단 거리를 구하지만, 각 좌표(x, y)에 대해서 벽을 부순 횟수에 따라서 방문 했는지 안했는지 기록
 * visited[y][x][k]는 좌표(x, y)를 벽을 k번 부숴서 도달했는지 안했는지 저장하고 있음
 */
public class Prob14442 {

    static int n, m, k;
    static int[][] grid;
    static boolean[][][] visited;

    static final int INF = 10000000;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            String curRow = br.readLine();
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = Integer.parseInt(String.valueOf(curRow.charAt(x - 1)));
            }
        }

        System.out.println(findShortestRoute());
    }

    public static int findShortestRoute() {
        Deque<Player> queue = new ArrayDeque<>();
        visited = new boolean[n + 1][m + 1][k + 1];
        int ans = INF;

        queue.offer(new Player(1, 1, 0, 1));
        visited[1][1][0] = true;

        while (!queue.isEmpty()) {
            Player player = queue.poll();
            int x = player.x;
            int y = player.y;
            int smashed = player.smashed;
            int dist = player.dist;

            if (x == m && y == n) {
                ans = Math.min(ans, dist);
                return ans;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx <= 0 || ny <= 0 || nx > m || ny > n) {
                    continue;
                }

                /**
                 * 인접한 좌표가 벽일때, 현재 벽을 부순 횟수가 k번 미만이면 벽을 더 부술수 있음
                 * 그러므로, 인접한 좌표의 벽을 부수기
                 */
                if (grid[ny][nx] == 1) {
                    if (smashed < k) {
                        if(!visited[ny][nx][smashed + 1]){
                            visited[ny][nx][smashed + 1] = true;
                            queue.offer(new Player(nx, ny, smashed + 1, dist + 1));
                        }
                    }
                }else{
                    /**
                     * 인접한 좌표가 벽이 아닐때는, 해당 좌표를 smashed 만큼 벽을 부쉈을때 방문하지 않았으면 방문
                     */
                    if(!visited[ny][nx][smashed]){
                        visited[ny][nx][smashed] = true;
                        queue.offer(new Player(nx, ny, smashed, dist + 1));
                    }
                }
            }
        }

        return ans == INF ? -1 : ans;
    }

    public static class Player{
        int x;
        int y;
        int smashed;
        int dist;

        public Player(int x, int y, int smashed, int dist) {
            this.x = x;
            this.y = y;
            this.smashed = smashed;
            this.dist = dist;
        }
    }
}
