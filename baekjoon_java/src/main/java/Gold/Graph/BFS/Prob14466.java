package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 4(소가 길을 건너간 이유 6)
 *
 * https://www.acmicpc.net/problem/14466
 *
 * Solution: BFS(Flood)
 * 1. 각 지점들에 대해서 서로 도로로 연결되어 있으면 bitmask로 표현
 * 2. 각 소의 위치로 부터 도달 가능한 모든 점들 찾기(BFS/Flood)
 *  -> 이때, 방문하는 각 위치에 대해서 상하좌우로 움직일때 도로로 연결되어 있는지 확인
 *      -> 도로로 연결되어 있으면 방문 못함
 * 3. 각 소로 부터 다른 소들의 좌표에 도달 가능한지 확인
 *  -> 도달하지 못하면 answer 증가
 */
public class Prob14466 {

    static int n, k, r;
    static int[][] road;
    static Point[] cows;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    // right, down, left, up

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        road = new int[n + 1][n + 1];
        for(int y = 0; y < n + 1; y++){
            for(int x = 0; x < n + 1; x++){
                road[y][x] = -1;
            }
        }
        for(int i = 0; i < r; i++){
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            if(road[r1][c1] == -1){
                road[r1][c1] = 0;
            }
            if(road[r2][c2] == -1){
                road[r2][c2] = 0;
            }
            for(int j = 0; j < 4; j++){
                int rn = r1 + dy[j];
                int rc = c1 + dx[j];

                if(rn == r2 && rc == c2){
                    road[r1][c1] = road[r1][c1] | (1 << j);
                    road[r2][c2] = road[r2][c2] | (1 << ((j + 2) % 4));
                    break;
                }
            }
        }

        cows = new Point[k];
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            cows[i] = new Point(x, y);
        }

        int answer = 0;
        for (int i = 0; i < k; i++) {
            boolean[][] visited = new boolean[n + 1][n + 1];

            Point start = cows[i];
            visited[start.y][start.x] = true;
            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(start.x, start.y));
            while (!queue.isEmpty()) {
                Point cur = queue.poll();
                int x = cur.x;
                int y = cur.y;

                for(int j = 0; j < 4; j++){
                    int nx = x + dx[j];
                    int ny = y + dy[j];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        continue;
                    }
                    if(visited[ny][nx]){
                        continue;
                    }

                    if(road[y][x] != -1){
                        if((road[y][x] & (1 << j)) != (1 << j)){
                            visited[ny][nx] = true;
                            queue.offer(new Point(nx, ny));
                        }
                    }else{
                        visited[ny][nx] = true;
                        queue.offer(new Point(nx, ny));
                    }
                }
            }

            for (int j = i + 1; j < k; j++) {
                Point cow = cows[j];
                int x = cow.x;
                int y = cow.y;
                if(!visited[y][x]){
//                    System.out.println(cows[i] + "<->" + cows[j]);

                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{x=" + x + ", y=" + y + "}";
        }
    }

}
