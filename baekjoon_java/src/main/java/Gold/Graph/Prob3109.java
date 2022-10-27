package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(빵집)
 *
 * https://www.acmicpc.net/problem/3109
 */
public class Prob3109 {

    static int h;
    static int w;
    static char[][] grid;
    static boolean[][] visited;

    static int[] dy = {-1, 0, 1};

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        grid = new char[h][w];
        visited = new boolean[h][w];
        for (int y = 0; y < h; y++) {
            String curRow = br.readLine();
            for(int x = 0; x < w; x++){
                grid[y][x] = curRow.charAt(x);
            }
        }

        for(int y = 0; y < h; y++){
            visited[y][0] = true;
            if(dfs(0, y)){
                ++ans;
            }
        }
//        printGrid();

        System.out.println(ans);
    }

    public static boolean dfs(int x, int y){
        if(x == w - 1){
            return true;
        }

        for (int i = 0; i < 3; i++) {
            int nx = x + 1;
            int ny = y + dy[i];

            if (ny < 0 || ny >= h) {
                continue;
            }

            if(grid[ny][nx] == '.'){
                if (!visited[ny][nx]) {
                    visited[ny][nx] = true;
                    if(dfs(nx, ny)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void printGrid(){
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
}
