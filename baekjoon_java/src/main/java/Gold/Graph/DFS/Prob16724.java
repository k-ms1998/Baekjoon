package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 3(피리 부는 사나이)
 *
 * https://www.acmicpc.net/problem/16724
 *
 * Solution: DFS + Flood
 * 1. 모든 점에서 탐색 시작
 * 2. 각 점에서 움직을 수 있는 방향으로, 계속 이동하면서 하나로 묶기(DFS + Flood)
 * 3. 각 점에서 시작해서, 이미 방문한 위치에 도착하면 탐색 멈추고, 지금까지 지나온 경로에 대해서 island 업데이트
 * -> 가장 마지막에 방문한 좌표의 값을 업데이트
 * 4. 다시 한번 island 를 다시 살피면서, 나타난 unique 한 island 값들을 확인하기
 * -> unique 한 값들의 수가 정답
 */
public class Prob16724 {

    static int n, m;
    static char[][] grid;
    static boolean[][] visited;
    static int[][] island;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        visited = new boolean[n][m];
        island = new int[n][m];
        for(int y = 0; y < n; y++){
            String cur = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = cur.charAt(x);
            }
        }

        int num = 0;
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(!visited[y][x]){
                    num++;
                    island[y][x] = num;
                    updateVisited(x, y, num); // Flood -> 도달 가능한 모든 점들을 하나로 묶기
                }
            }
        }

        int cnt = 0;
        boolean[] used = new boolean[num + 1];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(!used[island[y][x]]){ // unique 한 값들의 개수 구하기
                    used[island[y][x]] = true;
                    ++cnt;
                }
            }
        }

        System.out.println(cnt);
    }

    public static int updateVisited(int x, int y, int num) {
        if(x < 0 || y < 0 || x >= m || y >= n){
            return num;
        }
        if(visited[y][x]){
            if(island[y][x] == 0){
                island[y][x] = num;
            }

            return island[y][x];
        }

        visited[y][x] = true;
        char c = grid[y][x];
        if (c == 'R') {
            island[y][x] = updateVisited(x + 1, y, num);
        }else if(c == 'D'){
            island[y][x] = updateVisited(x, y + 1, num);
        }else if(c == 'L'){
            island[y][x] = updateVisited(x - 1, y, num);
        }else{
            island[y][x] = updateVisited(x, y - 1, num);
        }

        return island[y][x];
    }

}
/*
3 4
DLLD
DRLU
RRRU
-> 2

3 4
DLLD
DDLU
RRRU
-> 1
 */