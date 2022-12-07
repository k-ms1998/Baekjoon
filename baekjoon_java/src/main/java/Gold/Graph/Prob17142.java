package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(연구소 3)
 *
 * https://www.acmicpc.net/problem/17142
 *
 * Solution: BFS + Brute Force + Combination
 * -> 너비 우선 탐색으로 바이러스 확산 시킴(BFS)
 * -> 초반에 활성화 가능 바이러스들의 모든 조합들에 대해서 걸리는 시간 계산(Brute Force)
 * -> 모든 바이러스에 대해서, m개로 조합할 수 있는 Combination 계산(Combination)
 */
public class Prob17142 {

    static int n, m;
    static int[][] grid;
    static List<Pos> virus = new ArrayList<>();
    static Pos[] active;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        active = new Pos[m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                if (grid[y][x] == 2) {
                    virus.add(new Pos(x, y, 0));
                }
            }
        }

        ans = n * n;
        activateVirus(0, 0);
        System.out.println(ans == n * n ? -1 : ans);
    }

    /**
     * !! 모든 바이러스에 대해서, m 가지로 만들 수 있는 모든 조합 계산 !!
     * 재귀를 이용해서 m의 값이 무엇을 입력받던 조합을 구할 수 있도록 풀이
     *
     * @param idx -> active 에 추가할 바이러스의 인덱스 값
     * @param depth -> 현재 깊이를 나타냄(깊이랑 현재 활성화 시킬 바이러스의 순번과 같음)
     *
     * ex) m == 3이거, 바이라스가 인덱스 0부터 4까지 있을때:
     * (0, 1, 2) -> (0, 1, 3) -> (0, 1, 4) -> (0, 2, 3) -> (0, 2, 4) -> (0, 3, 4)
     * (1, 2, 3) -> (1, 2, 4) -> (1, 3, 4)
     * (2, 3, 4)
     */
    public static void activateVirus(int idx, int depth) {
        if (depth == m) {
            /**
             * 활성화 시킨 바이러스의 갯수가 총 m개 이면, 해당 바이러스들을 활성화 했을때
             * 바이러스가 모든 칸에 확산되는데 걸리는 시간 계산
             */
            spreadVirus();
            return;
        }

        for (int i = idx; i < virus.size(); i++) {
            active[depth] = virus.get(i);
            activateVirus(i + 1, depth + 1);
        }
    }

    /**
     * 바이러스 확산 시키기(BFS)
     */
    private static void spreadVirus() {
        Deque<Pos> queue = new ArrayDeque<>();
        int maxTime = 0;
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            Pos activeVirus = active[i];
            queue.offer(activeVirus);
            visited[activeVirus.y][activeVirus.x] = true;
        }

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            int x = pos.x;
            int y = pos.y;
            int t = pos.t;

            /**
             * 현재 칸이 바이러스 칸이면 maxTime 업데이트 X
             * 바이러스이 있는 칸들은 바이러스를 확산할 필요가 없기 때문에
             */
            if(grid[y][x] != 2){
                maxTime = Math.max(maxTime, t);
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) {
                    continue;
                }
                if (grid[ny][nx] == 1 || visited[ny][nx]) {
                    continue;
                }

                visited[ny][nx] = true;
                queue.offer(new Pos(nx, ny, t + 1));
            }
        }

        /**
         * 모든 빈칸들이 바이러스가 확산이 됐는지 확인
         */
        if (allInfected(visited)) {
            ans = Math.min(ans, maxTime);
        }

    }

    public static boolean allInfected(boolean[][] visited) {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (!visited[y][x]) {
                    if (grid[y][x] == 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static class Pos {
        int x;
        int y;
        int t;

        public Pos(int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }

        @Override
        public String toString() {
            return "Pos->{x=" + x + ", y=" + y + "}";
        }
    }
}
