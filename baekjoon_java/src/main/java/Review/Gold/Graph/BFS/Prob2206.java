package Review.Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 3(벽 부수고 이동하기)
 *
 * https://www.acmicpc.net/problem/2206
 * 
 * Solution: BFS
 * => 시작 지점부터 도착 지점까지 너비 우선 탐색으로 최단거리 구하기
 * => 각 좌표에 대해서 최단거리가 업데이트되면 해당 좌표로 부터 다시 탐색 시작
 *  => 각 좌표에 대해서, 현재 벽을 부순적이 있는지 없는지에 대해서 별도로 업데이트하면서 탐색
 *      => ex: (x, y)까지 가는데, 벽을 이미 한번 부수고 도착했을때랑, 벽을 부수지 않고 도착했을때랑 다르게 생각
 */
public class Prob2206 {

    static int n, m;
    static int[][] grid;
    static int[][][] dist; // dist[x][y][0] => (x, y)에 벽을 부수지 않고 도달한 최단 거리, dist[x][y][1] => (x, y)에 벽을 부수고 도달한 최단 거리
    static final int INF = 1000001;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        dist = new int[n][m][2];
        for(int y= 0; y < n; y++){
            String cur = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(String.valueOf(cur.charAt(x)));
                dist[y][x][0] = INF;
                dist[y][x][1] = INF;
            }
        }

        int answer = -1;
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 0, false, 1));
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;
            boolean broken = point.broken;
            int cnt = point.cnt;

            if (x == m - 1 && y == n - 1) {
                answer = cnt;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    continue;
                }

                if(broken){
                    if(dist[ny][nx][1] > cnt + 1){
                        if(grid[ny][nx] == 0){
                            dist[ny][nx][1] = cnt + 1;
                            queue.offer(new Point(nx, ny, broken, cnt + 1));
                        }
                    }
                }else{
                    if(grid[ny][nx] == 0){
                        if(dist[ny][nx][0] > cnt + 1){
                            dist[ny][nx][0] = cnt + 1;
                            queue.offer(new Point(nx, ny, broken, cnt + 1));
                        }
                    }else{
                        if(dist[ny][nx][1] > cnt + 1){
                            dist[ny][nx][1] = cnt + 1;
                            queue.offer(new Point(nx, ny, true, cnt + 1));
                        }
                    }

                }
            }
        }

        System.out.println(answer);
    }

    public static class Point{
        int x;
        int y;
        boolean broken;
        int cnt;

        public Point(int x, int y, boolean broken, int cnt) {
            this.x = x;
            this.y = y;
            this.broken = broken;
            this.cnt = cnt;
        }

    }
}
