package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(움직이는 미로 탈출)
 *
 * https://www.acmicpc.net/problem/16954
 *
 *
 * Solution: Simulation(Deque)
 */
public class Prob16954 {

    static String[] grid = new String[8];
    static int[] dx = {0, 0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static boolean[][][] visited = new boolean[65][8][8];

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int y = 0; y < 8; y++){
            grid[y] = br.readLine();
        }

        int answer = 0;
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 7, 0, grid));
        while(!queue.isEmpty()){
//            System.out.println("queue=" + queue);
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            int t = p.t;
            String[] g = p.g;

            if(visited[t][y][x]){
                continue;
            }
            visited[t][y][x] = true;

            if(g[y].charAt(x) == '#'){
                continue;
            }
            if(x == 7 && y == 0){
                answer = 1;
                break;
            }

            String[] moved = moveGrid(g);
            for(int d = 0; d < 9; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(nx < 0 || ny < 0 || nx >= 8 || ny >= 8){
                    continue;
                }

                if(g[ny].charAt(nx) == '.'){
                    queue.offer(new Point(nx, ny, t + 1, moved));
                }
            }


        }

        System.out.println(answer);

    }

    public static String[] moveGrid(String[] g){
        String[] moved = new String[8];
        for(int y = 7; y >= 1; y--){
            moved[y] = g[y - 1];
        }
        moved[0] = "........";

        return moved;
    }

    public static class Point{
        int x;
        int y;
        int t;
        String[] g;

        public Point(int x, int y, int t, String[] g){
            this.x = x;
            this.y = y;
            this.t = t;
            this.g = g;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}
