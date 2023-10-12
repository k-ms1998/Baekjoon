package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 1(연결)
 *
 * https://www.acmicpc.net/problem/5022
 *
 * Solution: BFS
 * 1. a1 -> a2로 가는 최단거리 구하기
 * 2. 최단거리로 갈 수 있는 경로들 구하기
 * 3. 각 경로에 대해서 b1->b2를 최단 거리로 갈 수 있는 거리 구하기
 * 4. Reset
 * 5. b1 -> b2로 가는 최단거리 구하기
 * 6. 최단거리로 갈 수 있는 경로들 구하기
 * 7. 각 경로에 대해서 a1->a2를 최단 거리로 갈 수 있는 거리 구하기
 *
 * 정담 = Min(1~3을 통해 구한 거리, 5~7을 통해 구한 거리)
 */
public class Prob5022 {

    static int answer;
    static final int INF = 10000000;

    static int n, m;
    static int ax1, ay1, ax2, ay2, bx1, by1, bx2, by2;
    static boolean[][] visited;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ax1 = Integer.parseInt(st.nextToken());
        ay1 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ax2 = Integer.parseInt(st.nextToken());
        ay2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        bx1 = Integer.parseInt(st.nextToken());
        by1 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        bx2 = Integer.parseInt(st.nextToken());
        by2 = Integer.parseInt(st.nextToken());

        answer = INF;
        findAnswer(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2); // a1->a2를 먼저 구한 후 b1->b2를 구했을때
        findAnswer(bx1, by1, bx2, by2, ax1, ay1, ax2, ay2); // b1->b2를 먼저 구한 후 a1->a2를 구했을때

        System.out.println(answer == INF ? "IMPOSSIBLE" : answer);
    }

    public static void findAnswer(int sx1, int sy1, int dx1, int dy1, int sx2, int sy2, int dx2, int dy2){
        int[][] dist = new int[m + 1][n + 1];
        for(int y = 0; y < m + 1; y++){
            for(int x = 0; x < n + 1; x++){
                dist[y][x] = INF;
            }
        }

        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(sx1, sy1, 0));
        dist[sy1][sx1] = 0;

        while(!queue.isEmpty()){
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            if(x == dx1 && y == dy1){
                continue;
            }

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx > n || ny > m){
                    continue;
                }

                if((nx == sx2 && ny == sy2) || (nx == dx2 && ny == dy2)){
                    continue;
                }

                if(dist[ny][nx] > d + 1){
                    dist[ny][nx] = d + 1;
                    queue.offer(new Point(nx, ny, d + 1));
                }
            }
        }

        int first = dist[dy1][dx1];

        //dfs로 경로 찾을려고 하면 시간초과 발생
        List<Route> list = new ArrayList<>();
        Deque<Route> routes = new ArrayDeque<>();
        routes.add(new Route(dx1, dy1, dist[dy1][dx1], String.valueOf(dx1), String.valueOf(dy1)));
        while(!routes.isEmpty()){
            Route r = routes.poll();
            int x = r.x;
            int y = r.y;
            int d = r.d;
            String xs = r.xs;
            String ys = r.ys;

            if(x == sx1 && y == sy1){
                list.add(r);

                continue;
            }

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx > n || ny > m){
                    continue;
                }

                if(dist[ny][nx] == d - 1){
                    dist[ny][nx] = -1; // -1로 바꿔주지 않으면 중복으로 같은 지점을 확인 할 수 있어서 메모리 초과 발생
                    routes.offer(new Route(nx, ny, d - 1, xs + " " + nx, ys + " " + ny));
                }
            }
        }

        for(Route r : list){
            String xs = r.xs;
            String ys = r.ys;

            StringTokenizer xst = new StringTokenizer(xs);
            StringTokenizer yst = new StringTokenizer(ys);

            visited = new boolean[m + 1][n + 1];
            for(int i = 0; i < first + 1; i++){
                int xx = Integer.parseInt(xst.nextToken());
                int yy = Integer.parseInt(yst.nextToken());

                visited[yy][xx] = true;
            }

            dist = new int[m + 1][n + 1];
            for(int y = 0; y < m + 1; y++){
                for(int x = 0; x < n + 1; x++){
                    dist[y][x] = INF;
                }
            }

            Deque<Point> queue2 = new ArrayDeque<>();
            queue2.offer(new Point(sx2, sy2, 0));
            dist[sy2][sx2] = 0;

            while(!queue2.isEmpty()){
                Point p = queue2.poll();
                int x = p.x;
                int y = p.y;
                int d = p.d;

                if(x == dx2 && y == dy2){
                    continue;
                }

                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx > n || ny > m){
                        continue;
                    }
                    if(visited[ny][nx]){
                        continue;
                    }

                    if(dist[ny][nx] > d + 1){
                        dist[ny][nx] = d + 1;
                        queue2.offer(new Point(nx, ny, d + 1));
                    }
                }
            }
            int second = dist[dy2][dx2];

            answer = Math.min(answer, first + second);
        }

    }

    public static class Point{
        int x;
        int y;
        int d;

        public Point(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    public static class Route{
        int x;
        int y;
        int d;
        String xs;
        String ys;

        public Route(int x, int y, int d, String xs, String ys){
            this.x = x;
            this.y = y;
            this.d = d;
            this.xs = xs;
            this.ys = ys;
        }

        @Override
        public String toString() {
            return "Route{" +
                    "x=" + x +
                    ", y=" + y +
                    ", d=" + d +
                    ", xs=" + xs +
                    ", ys=" + ys +
                    '}';
        }
    }

}