package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(스타트 택시)
 *
 * https://www.acmicpc.net/problem/19238
 *
 * Solution: BFS
 * 1. 각 손님들의 도착 지점부터, 다른 손님들의 시작 지점까지의 최단 거리 계산해서 dists 에 저장
 *  -> dists[i][j] == i의 도착 지점 -> j의 시작 지점까지의 거리 / dists[i][j] == i의 도착 지점 -> i의 시작 지점 == i의 시작부터 도착 지점까지의 거리
 * 2. 초기 택시 위치로부터 가장 가까운 손님의 시작 지점 찾기
 * 3. 해당 지점 부터 시작해서 dists 를 참고해서 가장 가까운 손님들을 바로 찾아서 움직이고, 연료의 양을 업데이트
 * 
 */
public class Prob19238 {

    static int n, m, fuel;
    static int[][] grid;
    static int tx, ty;
    static Customer[] customers;
    static boolean[] arrived;
    static int[][] dists;

    static int[] difX = {1, -1, 0, 0};
    static int[] difY = {0, 0, 1, -1};
    static final int INF = 100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][n + 1];
        customers = new Customer[m];
        arrived = new boolean[m];
        dists = new int[m][m];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        ty = Integer.parseInt(st.nextToken());
        tx = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int sy = Integer.parseInt(st.nextToken());
            int sx = Integer.parseInt(st.nextToken());
            int dy = Integer.parseInt(st.nextToken());
            int dx = Integer.parseInt(st.nextToken());

            customers[i] = new Customer(sx, sy, dx, dy);
        }

        initDists();
        int idx = initNearest();
        int arrivedCnt = 0;
        while (fuel > 0) {
            arrived[idx] = true;
            ++arrivedCnt;
            fuel -= dists[idx][idx]; // src -> dst
            if (fuel < 0) {
                break;
            }
            fuel += (2*dists[idx][idx]); // 목적지에 도착했으니 충전

            /*
            현재 지점부터 가장 가까운 손님 구하기
             */
            int tmpIdx = idx;
            int minDist = INF;
            for (int i = 0; i < m; i++) {
                if (i == idx || arrived[i]) {
                    continue;
                }

                if (minDist > dists[idx][i]) {
                    minDist = dists[idx][i];
                    tmpIdx = i;
                } else if (minDist == dists[idx][i]) {
                    Customer prev = customers[tmpIdx];
                    Customer cur = customers[i];
                    if (prev.sy > cur.sy) {
                        tmpIdx = i;
                    } else if (prev.sy == cur.sy) {
                        if (prev.sx > cur.sx) {
                            tmpIdx = i;
                        }
                    }
                }
            }
            if (minDist == INF) {
                break;
            }
            idx = tmpIdx;
            fuel -= minDist; // 현재 택시의 위치 -> 가장 가까운 손님
        }

        System.out.println(arrivedCnt >= m ? fuel : -1);
    }

    /**
     * 초기의 택시 위치에서 가장 가까운 손님의 위치 찾기
     */
    public static int initNearest() {
        int[][] dist = new int[n + 1][n + 1];
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                dist[y][x] = INF;
            }
        }

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(tx, ty, 0));
        dist[ty][tx] = 0;

        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int x = cur.x;
            int y = cur.y;
            int d = cur.d;

            for (int j = 0; j < 4; j++) {
                int nx = x + difX[j];
                int ny = y + difY[j];

                if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                    continue;
                }
                if (grid[ny][nx] == 1) {
                    continue;
                }

                if (dist[ny][nx] > d + 1) {
                    dist[ny][nx] = d + 1;
                    queue.offer(new Info(nx, ny, d + 1));
                }
            }
        }

        int idx = 0;
        int minDist = INF;
        for (int i = 0; i < m; i++) {
            Customer customer = customers[i];
            int cx = customer.sx;
            int cy = customer.sy;
            int curDist = dist[cy][cx];

            if (minDist > curDist) {
                idx = i;
                minDist = curDist;
            } else if (minDist == curDist) {
                if (cy < customers[idx].sy) {
                    idx = i;
                } else if (cy == customers[idx].sy) {
                    if(cx < customers[idx].sx){
                        idx = i;
                    }
                }
            }
        }

        fuel -= dist[customers[idx].sy][customers[idx].sx];
        return idx;
    }

    /**
     * 각 손님의 도착지점에 대해서 다른 손님들의 시작 지점까지의 거리 계산
     */
    public static void initDists(){
        for (int i = 0; i < m; i++) {
            Customer customer = customers[i];
            int[][] dist = new int[n + 1][n + 1];
            for (int y = 1; y < n + 1; y++) {
                for (int x = 1; x < n + 1; x++) {
                    dist[y][x] = INF;
                }
            }

            Deque<Info> queue = new ArrayDeque<>();
            queue.offer(new Info(customer.dx, customer.dy, 0));
            dist[customer.dy][customer.dx] = 0;

            while (!queue.isEmpty()) {
                Info cur = queue.poll();
                int x = cur.x;
                int y = cur.y;
                int d = cur.d;

                for (int j = 0; j < 4; j++) {
                    int nx = x + difX[j];
                    int ny = y + difY[j];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        continue;
                    }
                    if (grid[ny][nx] == 1) {
                        continue;
                    }

                    if (dist[ny][nx] > d + 1) {
                        dist[ny][nx] = d + 1;
                        queue.offer(new Info(nx, ny, d + 1));
                    }
                }
            }

            for (int j = 0; j < m; j++) {
                Customer next = customers[j];
                dists[i][j] = dist[next.sy][next.sx];
            }
        }
    }

    public static class Info{
        int x;
        int y;
        int d;

        public Info(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", d=" + d + "}";
        }
    }
    public static class Customer{
        int sx;
        int sy;
        int dx;
        int dy;

        public Customer(int sx, int sy, int dx, int dy) {
            this.sx = sx;
            this.sy = sy;
            this.dx = dx;
            this.dy = dy;
        }
    }
}
