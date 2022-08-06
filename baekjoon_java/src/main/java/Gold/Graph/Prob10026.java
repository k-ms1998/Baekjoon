package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Gold 5:
 *
 * 문제
 * 적록색약은 빨간색과 초록색의 차이를 거의 느끼지 못한다. 따라서, 적록색약인 사람이 보는 그림은 아닌 사람이 보는 그림과는 좀 다를 수 있다.
 * 크기가 N×N인 그리드의 각 칸에 R(빨강), G(초록), B(파랑) 중 하나를 색칠한 그림이 있다.
 * 그림은 몇 개의 구역으로 나뉘어져 있는데, 구역은 같은 색으로 이루어져 있다.
 * 또, 같은 색상이 상하좌우로 인접해 있는 경우에 두 글자는 같은 구역에 속한다. (색상의 차이를 거의 느끼지 못하는 경우도 같은 색상이라 한다)
 *
 * 예를 들어, 그림이 아래와 같은 경우에
 * RRRBB
 * GGBBB
 * BBBRR
 * BBRRR
 * RRRRR
 * 적록색약이 아닌 사람이 봤을 때 구역의 수는 총 4개이다. (빨강 2, 파랑 1, 초록 1) 하지만, 적록색약인 사람은 구역을 3개 볼 수 있다. (빨강-초록 2, 파랑 1)
 * 그림이 입력으로 주어졌을 때, 적록색약인 사람이 봤을 때와 아닌 사람이 봤을 때 구역의 수를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N이 주어진다. (1 ≤ N ≤ 100)
 * 둘째 줄부터 N개 줄에는 그림이 주어진다.
 *
 * 출력
 * 적록색약이 아닌 사람이 봤을 때의 구역의 개수와 적록색약인 사람이 봤을 때의 구역의 수를 공백으로 구분해 출력한다.
 *
 * Solution : BFS
 */
public class Prob10026 {

    static int n;
    static int ansNormal;
    static int ansColorBlind;
    static char WHITE = 'W';
    static char[][] normalGrid;
    static char[][] colorBlindGrid;

    static Integer[] tx = {1, -1, 0, 0};
    static Integer[] ty = {0, 0, 1, -1};

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.valueOf(br.readLine());

        normalGrid = new char[n][n];
        colorBlindGrid = new char[n][n];
        for (int y = 0; y < n; y++) {
            String currentRow = br.readLine();
            for (int x = 0; x < n; x++) {
                char curC = currentRow.charAt(x);

                normalGrid[y][x] = curC;
                if (curC == 'G') {
                    colorBlindGrid[y][x] = 'R';
                } else {
                    colorBlindGrid[y][x] = curC;
                }
            }
        }
        ansNormal = bfs(normalGrid);
        ans.append(" ");
        ansColorBlind = bfs(colorBlindGrid);

        System.out.println(ans);
    }

    static public int bfs(char[][] grid) {
        int cnt = 0;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] != WHITE) {
                    char curC = grid[y][x];
                    grid[y][x] = WHITE;

                    Deque<Pos> queue = new ArrayDeque<>();
                    queue.offer(new Pos(x, y));
                    cnt++;
                    while (!queue.isEmpty()) {
                        Pos curPos = queue.poll();
                        int curX = curPos.x;
                        int curY = curPos.y;

                        for (int i = 0; i < 4; i++) {
                            int nx = curX + tx[i];
                            int ny = curY + ty[i];

                            if(nx >= 0 && nx < n && ny >= 0 && ny < n){
                                if (grid[ny][nx] == curC) {
                                    queue.offer(new Pos(nx, ny));
                                    grid[ny][nx] = WHITE;
                                }
                            }
                        }
                    }

                }
            }
        }
        ans.append(cnt);
        return cnt;
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
