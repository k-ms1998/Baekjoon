package Silver.Graph;

import java.io.*;
import java.util.*;

/**
 * Silver 2(외판원 순회 2)
 *
 * https://www.acmicpc.net/problem/10971
 *
 * Solution: TSP(Traveling Salesman Problem), DFS, BruteForce
 * 1. 싸이클이 무조건 존재(즉, 어느 지점에서 시작하던 시작한 지점으로 무조건 돌아올 수 있음) => 어느 지점에서 시작해도 한바퀴 도는데 걸리는 최소 거리는 항상 동일
 * 2. 편의를 위해 첫번째 지점에서 시작하고, 모든 지점을 방문한 이후에, 다시 시작 지점으로 왔을때 걸린 총 거리의 최솟값을 구함
 */
public class Prob10971 {

    static int n;
    static int[][] roads;

    static boolean[] visited;

    static int ans = Integer.MAX_VALUE;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        roads = new int[n][];
        visited = new boolean[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            roads[i] = new int[n];
            for(int j = 0; j < n; j++){
                int num = Integer.parseInt(st.nextToken());
                roads[i][j] = num;
            }
        }

        dfs(0, 0, 0);

        System.out.println(ans);
    }

    public static void dfs(int y, int cost, int visitedCnt){
        if(cost >= ans){
            /*
            시간 단축을 위한 조건: 현재거리가 이미 한 바퀴를 다 돌아온 거리보다 크거나 같으면 더 이상 탐색할 필요 X
             */
            return;
        }
        if(visitedCnt == n){
            /*
            visitedCnt == n -> 총 방문한 지점의 갯수 == n 개, 즉 모든 지점을 방문 완료한 상태
             */
            if(y == 0){
                ans = Math.min(ans, cost);
            }

            return;
        }

        for(int x = 0; x < n; x++){
            if(roads[y][x] == 0 || ans <= cost + roads[y][x]){
                continue;
            }

            if(!visited[x]){
                visited[x] = true;
                dfs(x, cost + roads[y][x], visitedCnt + 1);
                visited[x] = false;
            }
        }
    }
}