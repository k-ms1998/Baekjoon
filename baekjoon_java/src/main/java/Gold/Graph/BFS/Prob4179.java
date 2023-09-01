package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 4(불!)
 *
 * https://www.acmicpc.net/problem/4179
 *
 * Solution: BFS
 * 1. 불이 확산됐을때 각 좌표에 확산되는데 걸리는 최소 시간 구하기 (fire[][])
 * 2. 지훈이가 움직일때 각 좌표를 최단거리로 갈 수 있는 시간 구하기(dist[][])
 * 3. 지훈이가 움직일때 미로의 가장자리에 있으면 exits에 가장자리 좌표 추가
 * 4. 각 가장자리 좌표에 지훈이 도달하는데 걸리는 시간이 불이 해당 좌표까지 확산되는 시간보다 작으면 탈출 가능
 */
public class Prob4179 {

    static int r, c;
    static int[][] org;
    static int jx, jy;
    static int answer;
    static final int INF = 100000000;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int[][] dist;
    static int[][] fire;
    static List<Point> exits = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        answer = INF;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        org = new int[r][c];
        dist = new int[r][c];
        fire = new int[r][c];
        for(int y = 0; y < r; y++){
            String row = br.readLine();
            for(int x = 0; x < c; x++){
                dist[y][x] = INF;
                fire[y][x] = INF;

                char c = row.charAt(x);
                if(c == '#'){
                    org[y][x] = -2;
                }else if(c == 'J'){
                    jx = x;
                    jy = y;
                    org[y][x] = -1;
                }else if(c == '.'){
                    org[y][x] = -1;
                }else if(c == 'F'){
                    org[y][x] = 0;
                }
            }
        }

        spreadFire();
        moveJ();

        for(Point p : exits){
            int x = p.x;
            int y = p.y;
            int d = p.d;

            if(dist[y][x] < fire[y][x]){
                answer = Math.min(answer, dist[y][x] + 1);
            }
        }
        System.out.println(answer == INF ? "IMPOSSIBLE" : answer);
    }

    public static void moveJ() {
        Deque<Point> queue = new ArrayDeque<>();
        dist[jy][jx] = 0;
        queue.offer(new Point(jx, jy, 0));
        while(!queue.isEmpty()){
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= c || ny >= r){
                    if(dist[y][x] >= d){
                        exits.add(new Point(x, y, d));
                    }
                    continue;
                }
                if(org[ny][nx] != -2){
                    if(dist[ny][nx] > d + 1){
                        dist[ny][nx] = d + 1;
                        queue.offer(new Point(nx, ny, d + 1));
                    }
                }

            }

        }
    }

    public static void spreadFire() {
        Deque<Point> queue = new ArrayDeque<>();
        for(int y = 0; y < r; y++){
            for(int x = 0; x < c; x++){
                if(org[y][x] == 0){
                    queue.offer(new Point(x, y, 0));
                    fire[y][x] = 0;
                }
            }
        }

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= c || ny >= r){
                    continue;
                }
                if(org[ny][nx] != -2){
                    if(fire[ny][nx] > d + 1){
                        fire[ny][nx] = d + 1;
                        queue.offer(new Point(nx, ny, d + 1));
                    }
                }
            }
        }
    }

    public static void printGrid(int[][] grid) {
        for(int y = 0; y < r; y++){
            for(int x = 0;x < c; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
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

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", d=" + d +
                    '}';
        }
    }

}
