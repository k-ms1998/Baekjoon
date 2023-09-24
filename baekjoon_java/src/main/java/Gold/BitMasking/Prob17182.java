package Gold.BitMasking;

import java.io.*;
import java.util.*;

/**
 * Gold 3(우주 탐사선)
 *
 * https://www.acmicpc.net/problem/17182
 *
 * Solution: BitMasking
 * 1. 비트바스킹으로 지금까지 방문한 노드드들 기록
 * 2 현재까지 방문한 노들 상태에서 다음 노드를 확인할때 최소비용확인
 * -> ex: 현재 0, 1, 4 노드를 확인한 상태이고 다음 확인할 노드가 3이면 -> visited[10011][3] 확인
 * 3. 최소비용을 업데이트 할 수 있으면 visited 업데이트하고 다음 노드로 이동
 */
public class Prob17182 {

    static int n, k;
    static int[][] grid;
    static int[][] visited;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new int[1 << n][n];
        for (int y = 0; y < (1 << n); y++) {
            for (int x = 0; x < n; x++) {
                visited[y][x] = Integer.MAX_VALUE;
            }
        }

        dfs(1 << k, 0, k);
        System.out.println(answer);
    }

    public static void dfs(int bit, int cnt, int node) {
        if (bit >= (1 << n) - 1) {
            answer = Math.min(answer, cnt);
            return;
        }

        for(int i = 0; i < n; i++){
            if(node != i){
                int nextBit = bit | (1 << i);
                if(visited[nextBit][i] > cnt + grid[node][i]){
                    visited[nextBit][i] = cnt + grid[node][i];
                    dfs(nextBit, cnt + grid[node][i], i);
                }
            }
        }
    }
}
