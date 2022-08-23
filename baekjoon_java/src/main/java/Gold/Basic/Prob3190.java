package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/3190
 *
 * !! 뱀의 길이와 본인과 충돌할때를 확인하는 것이 중요 !!
 *
 * Solution: 구현
 */
public class Prob3190 {

    static int n;
    static int k;
    /**
     * grid: 사과의 위치 && 뱀의 위치를 저장할 2차원 배열 (0 == empty, 1 == apple, 2 == )
     * dirMap: 특정 좌표에서 Head 가 움직인 방향 저장(0, 1, 2, 3) -> Tail 의 위치를 업데이트 시켜줄 때, 어느 방향으로 가야되는지 알려주기 위해
     *                                                      -> Head 가 먼저 움직이고, 똑같은 위치에서 똑같은 방향으로 Tail 이 움직이기 때문에
     * dir: 뱀의 방향 변환 정보 저장
     */
    static int[][] grid;
    static int[][] dirMap;
    static int l;
    static Deque<Map.Entry<Integer, String>> dir = new ArrayDeque<>();

    static int headX = 1;
    static int headY = 1;
    static int tailX = 1;
    static int tailY = 1;
    static int curT = 0;
    /**
     * curD = 0: 북 -> (x, y-1)
     * curD = 1: 동 -> (x + 1, y) -> Default
     * curD = 2: 남 -> (x, y+1)
     * curD = 3: 서 -> (x - 1, y)
     */
    static int curD = 1;
    static int[] tx = {0, 1, 0, -1};
    static int[] ty = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        grid = new int[n + 1][n + 1];
        dirMap = new int[n + 1][n + 1];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());

            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            /**
             * 0: Empty
             * 1: Apple
             * 2: Snake
             */
            grid[y][x] = 1;
        }

        int l = Integer.parseInt(br.readLine());
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());

            int t = Integer.parseInt(st.nextToken());
            String d = st.nextToken();

            dir.offer(Map.entry(t, d));
        }

        /**
         * 시작할때 뱀의 위치를 2로 업데이트
         */
        grid[headY][headX] = 2;
        while (true) {
            /**
             * 방향을 전환할 시점인지 확인
             */
            if (!dir.isEmpty()) {
                Map.Entry<Integer, String> nextDir = dir.peek();
                int nextT = nextDir.getKey();
                String nextD = nextDir.getValue();

                if (curT == nextT) {
                    dir.poll();
                    if (nextD.equals("L")) {
                        curD = (curD - 1 + 4) % 4;
                    } else {
                        curD = (curD + 1) % 4;
                    }
                    continue;
                }
            }
            /**
             * 현재 Head 위치의 dirMap 업데이트
             */
            dirMap[headY][headX] = curD;
            curT++;

            /**
             * 바라보는 방향으로 움직일때
             */
            int nx = headX + tx[curD];
            int ny = headY + ty[curD];
            if (nx < 1 || nx > n || ny < 1 || ny > n) {
                /**
                 * 벽에 충돌
                 */
//                System.out.println("WALL");
                break;
            }


            if (grid[ny][nx] == 2) {
                /**
                 * 자기 자신과 충돌
                 */
//                System.out.println("SELF");
                break;
            } else if (grid[ny][nx] == 0) {
                /**
                 * 충돌도 없고, 사과도 없는 칸으로 움직일때:
                 * 1. Tail 도 한 칸 움직이기 때문에, 현재 Tail 이 있는 칸을 0으로 업데이트
                 * 2. Tail 의 새로운 위치 찾고 업데이트
                 * 3. 움직인 Tail 의 칸을 2로 업데이트
                 */
                grid[tailY][tailX] = 0;

                int tailD = dirMap[tailY][tailX];
                tailX += tx[tailD];
                tailY += ty[tailD];

                grid[tailY][tailX] = 2;
            }
            /**
             * 다음 칸이 사과이면, tail 은 제자리에 있고, head 만 움직이면 되며, 이럴 경우 자동으로 뱀의 길이도 늘어남
             * ex: 2 2 1 -> 2 2 2 가 됨
             */

            /**
             * Head 의 위치 업데이트
             */
            headX = nx;
            headY = ny;
            grid[headY][headX] = 2;
        }

        System.out.println(curT);
    }

}
