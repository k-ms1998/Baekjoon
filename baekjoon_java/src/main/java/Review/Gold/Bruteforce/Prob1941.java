package Review.Gold.Bruteforce;

import java.io.*;
import java.util.*;

public class Prob1941 {

    static char[][] grid = new char[5][5];
    static int answer = 0;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int y = 0; y < 5; y++){
            String row = br.readLine();
            for(int x = 0; x < 5; x++){
                grid[y][x] = row.charAt(x);
            }
        }

        findComb(0, 0, 0);
        System.out.println(answer);
    }

    public static void findComb(int depth, int idx, int bit){
        if(depth >= 7){
            int size = 0;
            int sx = -1;
            int sy = -1;
            int[][] tmp = new int[5][5];
            for(int i = 0; i < 25; i++){
                if((bit & (1 << i)) == (1 << i)){
                    int y = i / 5;
                    int x = i % 5;
                    tmp[y][x] = 1;
                    size++;
                    sx = x;
                    sy = y;
                }
            }
            if(size < 7){
                return;
            }


            int yCnt = 0;
            int sCnt = 0;
            Deque<Point> queue = new ArrayDeque<>();
            queue.offer(new Point(sx, sy));
            while(!queue.isEmpty()){
                Point p = queue.poll();
                int x = p.x;
                int y = p.y;

                if(tmp[y][x] == 0){
                    continue;
                }

                tmp[y][x] = 0;
                if(grid[y][x] == 'Y'){
                    yCnt++;
                }else{
                    sCnt++;
                }

                for(int i = 0; i < 4; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx >= 5|| ny >= 5){
                        continue;
                    }

                    if(tmp[ny][nx] == 1){
                        queue.offer(new Point(nx, ny));
                    }
                }

            }
            if(yCnt + sCnt == 7){
                if(sCnt >= 4){
                    answer++;
                }
            }

            return;
        }

        for(int i = idx; i < 25; i++){
            findComb(depth + 1, i + 1, bit | (1 << i));
        }
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