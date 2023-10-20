package Gold.Backtracking;

import java.io.*;
import java.util.*;

/**
 * Gold 4(무기 공학)
 *
 * https://www.acmicpc.net/problem/18430
 *
 * Solution: BachTracking + 완전탐색
 */
public class Prob18430 {

    static int n, m;
    static int[][] grid;
    static int[][][] block = {
            {{-1, 0}, {0, 1}},
            {{-1, 0}, {0, -1}},
            {{1, 0}, {0, -1}},
            {{1, 0}, {0, 1}}
    };
    static int[][] visited;
    static int answer = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        visited = new int[n][m];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        findAnswer(0, 0, 0);
        System.out.println(answer);
    }

    public static void findAnswer(int x, int y, int cnt){
        if(x >= m){
            findAnswer(0, y + 1, cnt);
            return;
        }
        if(y >= n){
            answer = Math.max(answer, cnt);

            return;
        }

        for(int i = 0; i < 4; i++){
            int ax = x + block[i][0][0];
            int ay = y + block[i][0][1];
            int bx = x;
            int by = y;
            int cx = x + block[i][1][0];
            int cy = y + block[i][1][1];

            if(bounds(ax, ay) || bounds(cx, cy)){
                continue;
            }

            if(visited[ay][ax] == 0 && visited[by][bx] == 0 && visited[cy][cx] == 0){
                visited[ay][ax] = i + 1;
                visited[by][bx] = i + 1;
                visited[cy][cx] = i + 1;

                findAnswer(x + 1, y, cnt + grid[ay][ax] + 2*grid[by][bx] + grid[cy][cx]);

                visited[ay][ax] = 0;
                visited[by][bx] = 0;
                visited[cy][cx] = 0;
            }

        }

        findAnswer(x + 1, y, cnt);

    }

    public static boolean bounds(int x, int y){
        return x < 0 || y < 0 || x >= m || y >= n;
    }

    public static void printGrid(int[][] tmp){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                System.out.print(tmp[y][x] + "");
            }
            System.out.println();
        }
        System.out.println("----------");
    }
}