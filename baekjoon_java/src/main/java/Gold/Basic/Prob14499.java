package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/14499
 *
 * Solution: 구현, 시뮬레이션
 */
public class Prob14499 {

    static int n;
    static int m;
    static int sx;
    static int sy;
    static int k;

    static int[][] grid;
    /**
     *   2
     * 4 1 3
     *   5
     *   6
     * =>
     *   2
     * 4 1 3 6
     *   5
     *   6
     *  dice[0] = {4, 1, 3, 6}
     *  dice[1] = {2, 1, 5, 6}
     *  !! top = dice[0][1] == dice[1][1] !!
     *  !! bottom = dice[0][3] == dice[1][3] !!
     */
    static int[][] dice = new int[2][4];

    /**
     * 동쪽은 1, 서쪽은 2, 북쪽은 3, 남쪽은 4 => idx = 1, 2, 3, 4
     */
    static int[] tx = {0, 1, -1, 0, 0};
    static int[] ty = {0, 0, 0, -1, 1};

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * 입력받을때 주사위의 y(sy)좌표, x(sx)좌표 순으로 입력받음
         */
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());
        sx = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < k; i++) {
            Integer com = Integer.parseInt(st.nextToken());

            int nx = sx + tx[com];
            int ny = sy + ty[com];

            if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                continue;
            }

            sx = nx;
            sy = ny;
            moveDice(com);
            ans.append(dice[0][1]);
            ans.append("\n");
            int num = grid[ny][nx];
            if (num == 0) {
                /**
                 * 바닥이 0이면 주사위의 숫자를 바닥에 복사
                 */
                grid[ny][nx] = dice[0][3];
            } else {
                /**
                 * 바닥이 0이 아니면 바닥의 숫자를 주사위에 복사
                 * 주사위 바닥면 = ver[3]
                 */
                dice[0][3] = num;
                dice[1][3] = num;
                grid[ny][nx] = 0;
            }
        }

        System.out.println(ans);
    }

    /**
     *   2
     * 4 1 3 6
     *   5
     *   6
     * 동쪽은 1, 서쪽은 2, 북쪽은 3, 남쪽은 4
     */
    public static void moveDice(int com) {
        if(com == 1){
            /**
             * 동쪽으로 이동
             *   2
             * 6 4 1 3
             *   5
             *   3
             *
             * dice[0] = {4, 1, 3, 6} -> dice[0] = {6, 4, 1, 3}
             * &&
             * dice[0][1] == dice[1][1] && dice[0][3] == dice[1][3] 이 만족해야되기 때문에 dice[1][1] && dice[1][3] 업데이트
             */
            int tmp = dice[1][3];
            dice[1][3] = dice[1][2];
            dice[1][2] = dice[1][1];
            dice[1][1] = dice[1][0];
            dice[1][0] = tmp;

            dice[0][1] = dice[1][1];
            dice[0][3] = dice[1][3];

        } else if (com == 2) {
            /**
             * 서쪽으로 이동
             *   2
             * 1 3 6 4
             *   5
             *   4
             *
             * dice[0] = {4, 1, 3, 6} -> dice[0] = {1, 3, 6, 4}
             * &&
             * dice[0][1] == dice[1][1] && dice[0][3] == dice[1][3] 이 만족해야되기 때문에 dice[1][1] && dice[1][3] 업데이트
             */
            int tmp = dice[1][0];
            dice[1][0] = dice[1][1];
            dice[1][1] = dice[1][2];
            dice[1][2] = dice[1][3];
            dice[1][3] = tmp;

            dice[0][1] = dice[1][1];
            dice[0][3] = dice[1][3];

        } else if (com == 3) {
            /**
             * 북쪽으로 이동
             *   1
             * 4 5 3
             *   6
             *   2
             *
             * dice[1] = {2, 1, 5, 6} -> dice[1] = {1, 5, 6, 2}
             * &&
             * dice[0][1] == dice[1][1] && dice[0][3] == dice[1][3] 이 만족해야되기 때문에 dice[0][1] && dice[0][3] 업데이트
             */
            int tmp = dice[0][0];
            dice[0][0] = dice[0][1];
            dice[0][1] = dice[0][2];
            dice[0][2] = dice[0][3];
            dice[0][3] = tmp;

            dice[1][1] = dice[0][1];
            dice[1][3] = dice[0][3];
        } else {
            /**
             * 남쪽으로 이동
             *   6
             * 4 2 3
             *   1
             *   5
             *
             *  dice[1] = {2, 1, 5, 6} -> dice[1] = {6, 2, 1, 5}
             * &&
             * dice[0][1] == dice[1][1] && dice[0][3] == dice[1][3] 이 만족해야되기 때문에 dice[0][1] && dice[0][3] 업데이트
             */
            int tmp = dice[0][3];
            dice[0][3] = dice[0][2];
            dice[0][2] = dice[0][1];
            dice[0][1] = dice[0][0];
            dice[0][0] = tmp;

            dice[1][1] = dice[0][1];
            dice[1][3] = dice[0][3];
        }
    }
}
