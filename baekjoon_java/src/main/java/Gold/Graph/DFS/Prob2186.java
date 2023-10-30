package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 3(문자판)
 *
 * https://www.acmicpc.net/problem/2186
 *
 * Solution: DFS + DP
 */
public class Prob2186 {

    static int n, m, k;
    static char[][] grid;
    static String target;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int[][][] v;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = row.charAt(x);
            }
        }

        target = br.readLine();
        char start = target.charAt(0);
        int size = target.length();

        v = new int[size][n][m];
        for(int i = 0; i < size; i++){
            for(int y = 0; y < n; y++){
                for(int x = 0; x < m; x++){
                    v[i][y][x] = - 1;
                }
            }
        }


        int answer = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(grid[y][x] == start && v[0][y][x] == -1){
                    int res = dfs(x, y, 0, size);
                    answer += res;
                }
            }
        }

        System.out.println(answer);
    }

    public static int dfs(int x, int y, int depth, int end){
        if(depth + 1 >= end){
            return 1;
        }
        if(v[depth][y][x] != -1){
            return v[depth][y][x];
        }


        int res = 0;
        char next = target.charAt(depth + 1);
        for(int kk = 1; kk <= k; kk++){
            for(int i = 0; i < 4; i++){
                int nx = x + kk*dx[i];
                int ny = y + kk*dy[i];

                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                    continue;
                }

                if(grid[ny][nx] == next){
                    res += dfs(nx, ny, depth + 1, end);
                }

            }
        }
        v[depth][y][x] = res;

        return res;
    }
}
