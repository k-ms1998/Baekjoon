package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * N×N크기의 땅이 있고, 땅은 1×1개의 칸으로 나누어져 있다. 각각의 땅에는 나라가 하나씩 존재하며, r행 c열에 있는 나라에는 A[r][c]명이 살고 있다.
 * 인접한 나라 사이에는 국경선이 존재한다. 모든 나라는 1×1 크기이기 때문에, 모든 국경선은 정사각형 형태이다.
 * 오늘부터 인구 이동이 시작되는 날이다.
 *
 * 인구 이동은 하루 동안 다음과 같이 진행되고, 더 이상 아래 방법에 의해 인구 이동이 없을 때까지 지속된다.
 *
 * 국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하라면, 두 나라가 공유하는 국경선을 오늘 하루 동안 연다.
 * 위의 조건에 의해 열어야하는 국경선이 모두 열렸다면, 인구 이동을 시작한다.
 * 국경선이 열려있어 인접한 칸만을 이용해 이동할 수 있으면, 그 나라를 오늘 하루 동안은 연합이라고 한다.
 * 연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
 * 연합을 해체하고, 모든 국경선을 닫는다.
 * 각 나라의 인구수가 주어졌을 때, 인구 이동이 며칠 동안 발생하는지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N, L, R이 주어진다. (1 ≤ N ≤ 50, 1 ≤ L ≤ R ≤ 100)
 *
 * 둘째 줄부터 N개의 줄에 각 나라의 인구수가 주어진다. r행 c열에 주어지는 정수는 A[r][c]의 값이다. (0 ≤ A[r][c] ≤ 100)
 *
 * 인구 이동이 발생하는 일수가 2,000번 보다 작거나 같은 입력만 주어진다.
 *
 * 출력
 * 인구 이동이 며칠 동안 발생하는지 첫째 줄에 출력한다.
 *
 * Solution: BFS
 */
public class Prob16234 {
    static int n;
    static int l;
    static int r;
    static Integer[][] grid;
    static boolean[][] visited;

    static Integer[] tx = {1, 0, -1, 0};
    static Integer[] ty = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        grid = new Integer[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        /**
         * 1. 모든 국가 간의 인구 차이가 l 이상 r 이하 일때까지 검사
         * 2. 모든 국가 검사
         * 3. 모든 국가에 대해서 국경선을 열어야 하는 국가들을 BFS 로 탐색 (연결된 노드들을 찾는 것과 비슷한 개념)
         *  -> 3-1. (A,B), (C,D,E) 이런식으로 연결되어 있을 수 있기 떄문에, (A,B)랑 (C,D,E) 따로 인구수 Update
         * 4. 모든 국가에 대해서 국경선을 열아햐 하는 국가가 없을 경우에 END
         */
        int ans = 0;
        while (true) {
            int flag = 0;
            visited = new boolean[n][n];
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {
                    if(visited[y][x] == false){
                        flag += bfs(x, y);
                    }
                }
            }

            if (flag == 0) {
                break;
            }
            ans++;
        }

        System.out.println(ans);
    }

    public static int bfs(int x, int y){
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y));
        visited[y][x] = true;

        int population = 0;
        Stack<Pos> alliance = new Stack<>();
        while (!queue.isEmpty()) {
            Pos currentPos = queue.poll();
            int curX = currentPos.x;
            int curY = currentPos.y;

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                    int diff = Math.abs(grid[curY][curX] - grid[ny][nx]);
                    if (diff >= l && diff <= r && visited[ny][nx] == false) {
                        if (population == 0) {
                            population += grid[curY][curX];
                            alliance.push(new Pos(curX, curY));
                        }
                        queue.offer(new Pos(nx, ny));
                        population += grid[ny][nx];
                        alliance.push(new Pos(nx, ny));
                        visited[ny][nx] = true;
                    }
                }
            }
        }

        if (alliance.size() > 1) {
            int size = alliance.size();
            int updatedPopulation = population / size;
            while (!alliance.isEmpty()) {
                Pos pos = alliance.pop();
                int posX = pos.x;
                int posY = pos.y;

                grid[posY][posX] = updatedPopulation;
            }

            return size;
        }

        return 0;
    }

    static class Pos{
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{ " + x + " : " + y + " }";
        }
    }
}
