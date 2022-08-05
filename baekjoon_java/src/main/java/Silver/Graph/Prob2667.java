package Silver.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 1
 *
 * Solution: BFS || DFS
 */
public class Prob2667 {

    static int n;
    static Integer[][] grid;
    static List<Integer> groups = new ArrayList<>();
    static StringBuilder ans = new StringBuilder();

    static Integer[] tx = {1, -1, 0, 0};
    static Integer[] ty = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        grid = new Integer[n][n];
        for (int y = 0; y < n; y++) {
            String curRow = br.readLine();
            for (int x = 0; x < n; x++) {
                grid[y][x] = curRow.charAt(x) - '0';
            }
        }

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == 1) {
                    grid[y][x] = 0;
                    bfs(x, y);
                }
            }
        }

        ans.append(groups.size() + "\n");
        Collections.sort(groups);
        for (Integer g : groups) {
            ans.append(g + "\n");
        }
        System.out.println(ans);
    }

    public static void bfs(int x, int y) {
        int cnt = 1;
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y));
        while(!queue.isEmpty()){
            Pos curPos = queue.poll();
            int curX = curPos.x;
            int curY = curPos.y;

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                    if (grid[ny][nx] == 1) {
                        cnt++;
                        queue.offer(new Pos(nx, ny));
                        grid[ny][nx] = 0;
                    }
                }
            }
        }
        groups.add(cnt);
    }

    static class Pos{
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
