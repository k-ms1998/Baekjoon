package Review.Gold.Floyd;

import java.io.*;
import java.util.*;

public class Prob1507_1 {

    static int n;
    static int[][] grid;
    static int[][] dist;
    static final int INF = 100_000_000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n + 1][n + 1];
        dist = new int[n + 1][n + 1];

        for(int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < n + 1; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
                dist[y][x] = grid[y][x];
            }
        }

        for(int r = 1; r < n + 1; r++){
            for(int s = 1; s < n + 1; s++){
                for(int d = 1; d < n + 1; d++){
                    if(r == s || s == d || d == r){
                        continue;
                    }

                    if(grid[s][d] == grid[s][r] + grid[r][d]) {
                        dist[s][d] = 0;
                    }
                }
            }
        }

        if(checkDist()){
            System.out.println(-1);
        }else{
            int answer = 0;
            for(int y = 1; y < n + 1; y++){
                for(int x = 1; x < n + 1; x++){
                    answer += dist[y][x] == INF ? 0 : dist[y][x];
                }
            }
            System.out.println(answer/2);
        }

    }

    public static boolean checkDist() {
        int[][] tmp = new int[n + 1][n + 1];
        for(int y = 1; y < n + 1; y++){
            for(int x = 1; x < n + 1; x++){
                if(dist[y][x] != 0){
                    tmp[y][x] = dist[y][x];
                }else {
                    tmp[y][x] = INF;
                }
            }
        }

        for(int r = 1; r < n + 1; r++){
            for(int s = 1; s < n + 1; s++){
                for(int d = 1; d < n + 1; d++){
                    if(r == s || s == d || d == r){
                        continue;
                    }

                    tmp[s][d] = Math.min(tmp[s][d], tmp[s][r] + tmp[r][d]);
                }
            }
        }

        for(int y = 1; y < n + 1; y++){
            for(int x = 1; x < n + 1; x++){
                if (tmp[y][x] == INF) {
                    tmp[y][x] = 0;
                }
                if(tmp[y][x] != grid[y][x]){
                    return true;
                }
            }
        }
        return false;
    }
}
