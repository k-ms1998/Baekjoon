package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(게임)
 *
 * https://www.acmicpc.net/problem/1103
 *
 * Solution: DFS + Backtracking
 * 1. (1,1)부터 시작해서 상하좌우 방향으로 이동
 * 2. 이때, 싸이클이 발생하면 -1을 출력해야 하므로, 싸이클이 존재하는지 확인
 *  -> 싸이클이 존재하기 위해서는 이미 방문한 노드에 다시 방문하게 되면 싸이클 발생
 *  -> 이를 판별하기 위해서는 깊이 우선 탐색 활용
 * 3. 시간초과 발생을 예방하기 위해, count 배열에 해당 노드까지 도달했을때 최대로 동전을 움직인 횟수를 저장
 *  -> 만약에, 현재까지 움직인 동전의 횟수가 count에 저장된 값보다 작으면, 현재 위치로 부터 더 탐색할 필요가 없기 때문에  return
 */
public class Prob1103 {

    static int n, m;
    static int[][] grid;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int ans = 0;
    static boolean[][] visited;
    static int[][] count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][m + 1];
        visited = new boolean[n + 1][m + 1];
        count = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            String curRow = br.readLine();
            for (int x = 1; x < m + 1; x++) {
                char c = curRow.charAt(x - 1);
                if (c == 'H') {
                    grid[y][x] = 0;
                }else{
                    grid[y][x] = Integer.parseInt(String.valueOf(c));
                }
            }
        }

        moveCoin(1, 1, 1, 0);
        System.out.println(ans);
    }

    public static boolean moveCoin(int x, int y, int cnt, int dir) {
        if (x <= 0 || y <= 0 || x > m || y > n) {
            return false;
        }
        if (grid[y][x] == 0) {
            return false;
        }
        if(visited[y][x]){
            return true;
        }
        if (count[y][x] >= cnt) {
            return false;
        }

        ans = Math.max(ans, cnt);
        count[y][x] = cnt;
        visited[y][x] = true;
        int dist = grid[y][x];
        for (int i = 0; i < 4; i++) {
            int nx = x + dist * dx[i];
            int ny = y + dist * dy[i];

            if(moveCoin(nx, ny, cnt + 1, i)){
                ans = -1;
                return true;
            }
        }
        visited[y][x] = false;

        return false;
    }
}