package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(미친 아두이노)
 *
 * https://www.acmicpc.net/problem/8972
 *
 * Solution: 구현(Simulation)
 */
public class Prob8972 {

    static int r, c;
    static int[] dx = {-1, -1, 0, 1, -1, 0, 1, -1, 0, 1};
    static int[] dy = {-1, 1, 1, 1, 0, 0, 0, -1, -1, -1};
    static Point jong;
    static List<Point> adu = new ArrayList<>();
    static char[][] grid;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        grid = new char[r][c];
        for (int y = 0; y < r; y++) {
            String row = br.readLine();
            for(int x = 0; x < c; x++){
                grid[y][x] = row.charAt(x);
                if(grid[y][x] == 'I'){
                    jong = new Point(x, y, false);
                }
                if(grid[y][x] == 'R'){
                    adu.add(new Point(x, y, false));
                }
            }
        }

        int caughtX = 0;
        boolean caught = false;
        String command = br.readLine();
        for (int i = 0; i < command.length(); i++) {
            if(caught){
                caughtX = i;
                break;
            }

            List<Integer>[][] moved = new List[r][c];
            for(int ty = 0; ty < r; ty++){
                for (int tx = 0; tx < c; tx++) {
                    moved[ty][tx] = new ArrayList<>();
                }
            }

            int d = Integer.parseInt(String.valueOf(command.charAt(i)));
            int x = jong.x;
            int y = jong.y;
            int nx = x + dx[d];
            int ny = y + dy[d];

            jong = new Point(nx, ny, false);

            for (int idx = 0; idx < adu.size(); idx++) {
                Point a = adu.get(idx);
                int ax = a.x;
                int ay = a.y;
                boolean destroyed = a.destroyed;
                if (destroyed) {
                    continue;
                }

                int fd = 0;
                int dist = r * c + 1;
                for (int j = 1; j < 10; j++) {
                    int anx = ax + dx[j];
                    int any = ay + dy[j];

                    if (anx < 0 || any < 0 || anx >= c || any >= r) {
                        continue;
                    }

                    int curDist = Math.abs(anx - jong.x) + Math.abs(any - jong.y);
                    if (dist > curDist) {
                        dist = curDist;
                        fd = j;
                    }
                    if (curDist == 0) {
                        caught = true;
                    }
                }

                int anx = ax + dx[fd];
                int any = ay + dy[fd];

                a.x = anx;
                a.y = any;
                moved[any][anx].add(idx);
            }

            for(int ty = 0; ty < r; ty++){
                for(int tx = 0; tx < c; tx++){
                    if(moved[ty][tx].size() == 0){
                        grid[ty][tx] = '.';
                    } else if (moved[ty][tx].size() == 1) {
                        grid[ty][tx] = 'R';
                    } else {
                        grid[ty][tx] = '.';
                        for(int idx : moved[ty][tx]){
                            Point p = adu.get(idx);
                            p.destroyed = true;
                        }
                    }
                }
            }
            grid[jong.y][jong.x] = 'I';
        }

        if(caught){
            System.out.println("kraj " + caughtX);
        }else{
            printGrid();
        }
    }

    public static void printGrid() {
        StringBuilder ans = new StringBuilder();
        for(int y = 0; y < r; y++){
            for(int x = 0; x < c; x++){
                ans.append(grid[y][x]);
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static class Point{
        int x;
        int y;
        boolean destroyed;

        public Point(int x, int y, boolean destroyed){
            this.x = x;
            this.y = y;
            this.destroyed = destroyed;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}