package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(통나무 옮기기)
 *
 * https://www.acmicpc.net/problem/1938
 *
 * Solution: Simulation(구현)
 * 1. dp[y][x][d] -> 통나무의 주심 좌표가 (x, y)이고 d 방향으로 놓여져 있을때 최단 횟수내로 도달 가능한 횟수
 * 2. 목표 위치의 좌표에서 목표 방향으로 놓여져 있을때의 값 출력
 */
public class Prob1938 {

    static int n;
    static char[][] grid;
    static Point[] wood = new Point[3];
    static Point[] end = new Point[3];
    static int[][][] dp;
    static final int INF = 100000000;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        grid = new char[n][n];
        dp = new int[n][n][2];
        int woodIdx = 0;
        int endIdx = 0;
        for(int y = 0; y < n; y++){
            String curRow = br.readLine();
            for(int x = 0; x < n; x++){
                dp[y][x][0] = INF;
                dp[y][x][1] = INF;

                grid[y][x] = curRow.charAt(x);
                if(grid[y][x] == 'B'){
                    wood[woodIdx] = new Point(x, y);
                    woodIdx++;
                }
                if(grid[y][x] == 'E'){
                    end[endIdx] = new Point(x, y);
                    endIdx++;
                }
            }
        }

        Deque<Info> queue = new ArrayDeque<>();
        int curD = checkD(wood);
        queue.offer(new Info(curD, 0, wood[1].x, wood[1].y));
        dp[wood[1].y][wood[1].x][curD] = 0;

        while(!queue.isEmpty()){
            Info info = queue.poll();
            int d = info.d;
            int cnt = info.cnt;
            int x = info.x;
            int y = info.y;

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    continue;
                }
                if(checkWood(nx, ny, d)){
                    continue;
                }
                if(grid[ny][nx] == '1'){
                    continue;
                }

                if(dp[ny][nx][d] > cnt + 1){
                    dp[ny][nx][d] = cnt + 1;
                    queue.offer(new Info(d, cnt + 1, nx, ny));
                }
            }

            if(turnable(x, y)){
                int nextD = (d + 1) % 2;
                if(dp[y][x][nextD] > cnt + 1){
                    dp[y][x][nextD] = cnt + 1;
                    queue.offer(new Info(nextD, cnt + 1, x, y));
                }
            }
        }

        int endX = end[1].x;
        int endY = end[1].y;
        int endD = checkD(end);
        int answer = dp[endY][endX][endD];

        System.out.println(answer == INF ? 0 : answer);
    }

    public static boolean checkWood(int x, int y, int d) {
        if(d == 0){
            int ny1 = y - 1;
            int ny2 = y + 1;

            if(ny1 < 0 || ny1 >= n){
                return true;
            }
            if(ny2 < 0 || ny2 >= n){
                return true;
            }
            if(grid[ny1][x] == '1'){
                return true;
            }
            if(grid[ny2][x] == '1'){
                return true;
            }
        }else{
            int nx1 = x - 1;
            int nx2 = x + 1;

            if(nx1 < 0 || nx1 >= n){
                return true;
            }
            if(nx2 < 0 || nx2 >= n){
                return true;
            }
            if(grid[y][nx1] == '1'){
                return true;
            }
            if(grid[y][nx2] == '1'){
                return true;
            }
        }

        return false;
    }

    public static boolean turnable(int x, int y) {
        int[] tx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] ty = {-1, -1, 0, 1, 1, 1, 0, -1};

        for(int i = 0; i < 8; i++){
            int nx = x + tx[i];
            int ny = y + ty[i];

            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                return false;
            }

            if(grid[ny][nx] == '1'){
                return false;
            }

        }
        return true;
    }

    public static int checkD(Point[] points) {
        Point a = points[0];
        Point b = points[1];
        Point c = points[2];

        if(a.x == b.x && b.x == c.x){ // x가 같으면 -> 세로로 배치되어 있음
            return 0;
        }else{
            return 1;
        }

    }

    public static class Info{
        int d;
        int cnt;
        int x;
        int y;

        public Info(int d, int cnt, int x, int y) {
            this.d = d;
            this.cnt = cnt;
            this.x = x;
            this.y = y;
        }
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
