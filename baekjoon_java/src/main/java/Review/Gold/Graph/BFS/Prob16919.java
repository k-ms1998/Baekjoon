package Review.Gold.Graph.BFS;

import java.io.*;
import java.util.*;

public class Prob16919 {

    static int r, c, n;
    static char[][] grid;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        Deque<Point> queue = new ArrayDeque<>();
        grid = new char[r][c];
        for(int y = 0; y < r; y++){
            String row = br.readLine();
            for(int x = 0; x < c; x++){
                grid[y][x] = row.charAt(x);
                if(grid[y][x] == 'O'){
                    queue.offer(new Point(x, y));
                }
            }
        }

        for(int t = 1; t < n; t++){
            if(t % 2 == 0){
                while(!queue.isEmpty()){
                    Point p = queue.poll();
                    int x = p.x;
                    int y = p.y;

                    grid[y][x] = '.';
                    for(int d = 0; d < 4; d++){
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if(nx < 0 || ny < 0 || nx >= c || ny >= r){
                            continue;
                        }

                        grid[ny][nx] = '.';
                    }
                }

                for(int y = 0; y < r; y++){
                    for(int x = 0; x < c; x++){
                        if(grid[y][x] == 'O'){
                            queue.offer(new Point(x, y));
                        }
                    }
                }
            }else{
                for(int y = 0; y < r; y++){
                    for(int x = 0; x < c; x++){
                        grid[y][x] = 'O';
                    }
                }
            }
        }

        printAnswer(grid);
    }

    public static void printAnswer(char[][] g){
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

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}