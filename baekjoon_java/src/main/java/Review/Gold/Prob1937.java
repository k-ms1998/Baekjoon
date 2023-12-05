package Review.Gold;

import java.io.*;
import java.util.*;

public class Prob1937 {

    static int n;
    static int[][] grid;
    static int[][] cost;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        cost = new int[n][n];

        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                answer = Math.max(answer, dfs(x, y));
            }
        }

        System.out.println(answer);
    }

    public static int dfs(int x, int y){
        if(cost[y][x] > 0){
            return cost[y][x];
        }

        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                continue;
            }

            if(grid[ny][nx] > grid[y][x]){
                cost[y][x] = Math.max(cost[y][x], dfs(nx, ny));
            }

        }

        cost[y][x]++;
        return cost[y][x];
    }
}
