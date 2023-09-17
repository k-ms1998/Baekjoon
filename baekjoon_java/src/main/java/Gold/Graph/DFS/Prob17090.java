package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 3(미로 탈출하기)
 *
 * https://www.acmicpc.net/problem/17090
 *
 * Solution: DFS + DP
 * 1. 하나의 점에서 시작해서 정해진 경로에 따라 움직임
 * 2. 움직이다가 미로 밖으로 나가거나, 이미 방문한 좌표에 도달하면 멈추기
 * 3. 시간 단축을 위해 이미 방문한 좌표들에 대해서, 해당 좌표를 지났을때 미로 탈출가능한지 불가능한지 확인
 * -> check[y][x] == 1이면 탈출 가능, check[y][x] == -1이면 불가능, check[y][x] == 0 이면 아직 확인되지 않음
 * 4. visited[y][x]는 이미 방문한 칸들
 * -> (x, y)에서 visited[y][x]는 true 인데 check[y][x] == 0 이면, 이번 회차 경로 탐색 중에 (x, y)를 방문한 것
 *  -> 즉, 다시 (x, y)로 돌아왔기 때문에 해당 좌표를 지나면 미로 탈출이 불가능
 *      -> -1 반환
 */
public class Prob17090 {

    static int n, m;
    static char[][] grid;
    static int[][] check;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        check = new int[n][m];
        visited = new boolean[n][m];

        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = row.charAt(x);
            }
        }

        int answer = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                int result = dfs(x, y, visited);
                if (result == 1) {
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    public static int dfs(int x, int y, boolean[][] visited) {
        if(x < 0 || y < 0 || x >= m || y >= n){
            return 1;
        }
        if(visited[y][x]){
            if(check[y][x] == 1){
                return 1;
            }

            check[y][x] = -1;
            return -1;
        }
        if(check[y][x] != 0){
            return check[y][x];
        }

        visited[y][x] = true;
        if(grid[y][x] == 'U'){
            check[y][x] = dfs(x, y - 1, visited);
        }else if(grid[y][x] == 'R'){
            check[y][x] =  dfs(x + 1, y, visited);
        }else if(grid[y][x] == 'D'){
            check[y][x] =  dfs(x, y + 1, visited);
        }else {
            check[y][x] =  dfs(x - 1, y, visited);
        }

        return check[y][x];
    }
}
