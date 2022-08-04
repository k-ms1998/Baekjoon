package Silver.Graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Silver 1
 *
 * 문제
 * 체스판 위에 한 나이트가 놓여져 있다. 나이트가 한 번에 이동할 수 있는 칸은 아래 그림에 나와있다. 나이트가 이동하려고 하는 칸이 주어진다.
 * 나이트는 몇 번 움직이면 이 칸으로 이동할 수 있을까?
 *
 * 입력
 * 입력의 첫째 줄에는 테스트 케이스의 개수가 주어진다.
 * 각 테스트 케이스는 세 줄로 이루어져 있다. 첫째 줄에는 체스판의 한 변의 길이 l(4 ≤ l ≤ 300)이 주어진다.
 * 체스판의 크기는 l × l이다. 체스판의 각 칸은 두 수의 쌍 {0, ..., l-1} × {0, ..., l-1}로 나타낼 수 있다.
 * 둘째 줄과 셋째 줄에는 나이트가 현재 있는 칸, 나이트가 이동하려고 하는 칸이 주어진다.
 *
 * 출력
 * 각 테스트 케이스마다 나이트가 최소 몇 번만에 이동할 수 있는지 출력한다.
 *
 * Solution: BFS + DP
 */
public class Prob7562 {

    static Integer[] tx = {1, 2, 2, 1, -1, -2, -2, -1};
    static Integer[] ty = {2, 1, -1, -2, -2, -1, 1, 2};
    static Integer[][] visited;

    static int l;
    static int sx;
    static int sy;
    static int dx;
    static int dy;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.valueOf(br.readLine());
        for (int i = 0; i < t; i++) {
            l = Integer.valueOf(br.readLine());
            visited = new Integer[l][l];
            for (int y = 0; y < l; y++) {
                for(int x = 0; x < l; x++){
                    visited[y][x] = Integer.MAX_VALUE;
                }
            }

            st = new StringTokenizer(br.readLine());
            sx = Integer.parseInt(st.nextToken());
            sy = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            dx = Integer.parseInt(st.nextToken());
            dy = Integer.parseInt(st.nextToken());

            bfs();
            System.out.println(visited[dy][dx]);
        }
    }

    public static void bfs() {
        Coord start = new Coord(sx, sy, 0);
        visited[sy][sx] = 0;

        Deque<Coord> queue = new ArrayDeque<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            Coord currentCoord = queue.poll();
            int x = currentCoord.getX();
            int y = currentCoord.getY();
            int cnt = currentCoord.getCnt();

            for (int i = 0; i < 8; i++) {
                int nx = x + tx[i];
                int ny = y + ty[i];

                /**
                 * nx: 다음으로 이동할 x 좌표
                 * ny: 다음으로 이동할 y 좌표
                 * 
                 * 1. nx 또는 ny 의 좌표가 범위 밖에 있으면 무시
                 * 2. (nx, ny) 좌표의 최단 횟수가 현재 Cost 보다 작으면 무시
                 */
                if (nx >= l || ny >= l || nx < 0 || ny < 0) {
                    continue;
                }
                if (visited[ny][nx] <= cnt + 1) {
                    continue;
                }

                visited[ny][nx] = cnt + 1;
                queue.offer(new Coord(nx, ny, cnt + 1));

            }
        }
    }

    static class Coord{
        int x;
        int y;
        int cnt;

        public Coord(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getCnt() {
            return cnt;
        }
    }

}
