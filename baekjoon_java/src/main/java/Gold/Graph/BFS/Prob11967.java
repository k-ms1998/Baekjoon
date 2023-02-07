package Gold.Graph.BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 2(불켜기)
 *
 * https://www.acmicpc.net/problem/11967
 *
 * !! 중요 !! => 해당 방을 방문하지 않았더라도, 불은 켤 수 있기 때문에, 방문한 위치들의 갯수가 아니라 불이 켜져 있는 방들의 갯수가 정답
 * Solution: BFS
 * 1. (1,1) 부터 시작
 * 2. 현재 지점에서 킬 수 있는 불들 켜기
 *  -> 불을 켤때, 인접한 점들을 위치들을 확인
 *      -> 만약, 인접한 점들 중에서 방문한 곳이 있다면, 이번에 불을 켠 위치도 도달 가능한 지잠
 *          => 덱에 이번에 불을 켠 위치 추가해서, 해당 위치로 부터도 탐색 시작
 * 3. 인접한 점들로 이동 가능한지 확인하고, 이동 가능하면  덱에 넣기
 * 4. 덱에서 각 점을 뺀 다음 2~3 반복
 * 5. 덱이 비어 있으면 종료
 *  -> 불이 켜져 있는 모든 방 확인
 */
public class Prob11967 {

    static int n, m;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static List<Info>[][] lightSwitch;
    static boolean[][] on;
    static boolean[][] visited;
    static int ans = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        lightSwitch = new List[n + 1][n + 1];
        for (int y = 0; y < n + 1; y++) {
            for (int x = 0; x < n + 1; x++) {
                lightSwitch[y][x] = new ArrayList<>();
            }
        }
        on = new boolean[n + 1][n + 1];
        visited = new boolean[n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            lightSwitch[y][x].add(new Info(a, b));
        }

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(1, 1));
        on[1][1] = true;
        visited[1][1] = true;
        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int x = cur.x;
            int y = cur.y;

            /**
             * 현재 위치로부터 불을 켤 수 있는 방들의 불 켜기
             */
            for (Info next : lightSwitch[y][x]) {
                int nextX = next.x;
                int nextY = next.y;

                if (on[nextY][nextX]) {
                    continue;
                }

                /**
                 * 새로 불을 켠 방에 인접한 방들 확인
                 * 인접한 방 중, 하나라도 방문한 적이 있는 방이면 이번에 불을 켠 방도 도달 가능
                 * => 덱에 추가해서, 탐색하기
                 */
                on[nextY][nextX] = true;
                for (int i = 0; i < 4; i++) {
                    int nx = nextX + dx[i];
                    int ny = nextY + dy[i];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        continue;
                    }
                    if (on[ny][nx]) {
                        if(visited[ny][nx]){
                            visited[nextY][nextX] = true;
                            queue.offer(new Info(nextX, nextY));
                            break;
                        }
                    }
                }
            }

            /**
             * 현재 위치로 부터 인접한 점들에 도달 가능한지 확인
             * 도달 가능하면 덱에 추가
             */
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                    continue;
                }
                if (on[ny][nx]) {
                    if(!visited[ny][nx]){
                        visited[ny][nx] = true;
                        queue.offer(new Info(nx, ny));
                    }
                }
            }
        }

        /**
         * 불이 켜져 있는 모든 위치들 확인
         */
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (on[y][x]) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }

    public static class Info{
        int x;
        int y;

        public Info(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{x:" + x + ", y:" + y + "}";
        }
    }
}