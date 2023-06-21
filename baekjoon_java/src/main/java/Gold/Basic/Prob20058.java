package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(마법사 상어와 파이어스톰)
 *
 * https://www.acmicpc.net/problem/20058
 *
 * Solution: Simulation(구현)
 * -> 사각형에서 회전하기 중요
 */
public class Prob20058 {

    static int n, q;
    static int size;
    static int[][] grid;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int count = 0;
    static int bulk = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        size = (int) Math.pow(2, n);

        grid = new int[size][size];
        for (int y = 0; y < size; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < size; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < q; i++){
            int l = Integer.parseInt(st.nextToken());
            rotateGrid(l);
            meltGrid();
//            printGrid();
        }
        getCountAndBulk();

        System.out.println(count);
        System.out.println(bulk);
    }

    public static void getCountAndBulk() {
        boolean[][] visited = new boolean[size][size];
        for (int sy = 0; sy < size; sy++) {
            for (int sx = 0; sx < size; sx++) {
                if(grid[sy][sx] > 0){
                    count += grid[sy][sx];

                    if(!visited[sy][sx]){
                        int tmp = 1;
                        Deque<Point> queue = new ArrayDeque();
                        queue.offer(new Point(sx, sy));
                        visited[sy][sx] = true;

                        while(!queue.isEmpty()){
                            Point cur = queue.poll();
                            int x = cur.x;
                            int y = cur.y;

                            for(int i = 0; i < 4; i++){
                                int nx = x + dx[i];
                                int ny = y + dy[i];

                                if (nx < 0 || ny < 0 || nx >= size || ny >= size) {
                                    continue;
                                }

                                if(grid[ny][nx] > 0 && !visited[ny][nx]){
                                    queue.offer(new Point(nx, ny));
                                    visited[ny][nx] = true;
                                    tmp++;
                                }
                            }
                        }

                        bulk = Math.max(bulk, tmp);
                    }
                }
            }
        }
    }



    public static void meltGrid() {
        int[][] tmp = new int[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if(grid[y][x] > 0){
                    int cnt = 0;
                    for(int i = 0; i < 4; i++){
                        int nx = x + dx[i];
                        int ny = y + dy[i];

                        if (nx < 0 || ny < 0 || nx >= size || ny >= size) {
                            continue;
                        }

                        if(grid[ny][nx] > 0){
                            cnt++;
                        }
                    }
                    if(cnt < 3){
                        tmp[y][x]++;
                    }
                }
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid[y][x] -= tmp[y][x];
                if (grid[y][x] < 0) {
                    grid[y][x] = 0;
                }
            }
        }

    }

    public static void rotateGrid(int l) {
        int r = (int) Math.pow(2, l);
        int[][] tmp = new int[size][size];
        for(int sy = 0; sy < size; sy += r){
            for (int sx = 0; sx < size; sx += r) {
                for (int y = 0; y < r; y++) {
                    for (int x = 0; x < r; x++) {
                        int nx = sx + r - 1 - y; // nx = 시작 x좌표 + 크기 - 1 - y
                        int ny = sy + x; // 시작 y좌표 + x

                        tmp[ny][nx] = grid[y + sy][x + sx];
                    }
                }
            }
        }

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                grid[y][x] = tmp[y][x];
            }
        }
    }

    public static void printGrid() {
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("------");
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
