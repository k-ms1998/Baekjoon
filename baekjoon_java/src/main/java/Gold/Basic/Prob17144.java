package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/17144
 *
 * Solution: 구현/시뮬레이션
 */
public class Prob17144 {

    static int r;
    static int c;
    static int t;
    static int[][] grid;

    static int puriXA;
    static int puriYA;
    static int puriXB;
    static int puriYB;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        int airCleanerFlag = 0;
        grid = new int[r][c];
        for (int y = 0; y < r; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < c; x++) {
                int num = Integer.parseInt(st.nextToken());
                grid[y][x] = num;
                if (num == -1) {
                    if (airCleanerFlag == 0) {
                        puriXA = x;
                        puriYA = y;
                    } else {
                        puriXB = x;
                        puriYB = y;
                    }
                    airCleanerFlag++;
                }
            }
        }
        int curT = 0;
        while (curT < t) {
            blowDust();
            curT++;
        }

        System.out.println(countDust());
    }

    public static void blowDust() {
        grid = spreadDust();
        moveDust();
    }

    /**
     * 미세먼지를 확산시키고, 확산 시킨 grid 를 return하기
     * @return
     */
    public static int[][] spreadDust() {
        int[] tx = {1, -1, 0, 0};
        int[] ty = {0, 0, 1, -1};

        int[][] tmpGrid = new int[r][c];
        tmpGrid[puriYA][puriXA] = -1;
        tmpGrid[puriYB][puriXB] = -1;
        for (int y = 0; y < r; y++) {
            for (int x = 0; x < c; x++) {
                int curDustAmount = grid[y][x];
                if (curDustAmount == -1) {
                    continue;
                }

                int spreadDustAmount = curDustAmount / 5;
                int spreadCnt = 0;
                for (int i = 0; i < 4; i++) {
                    int nx = x + tx[i];
                    int ny = y + ty[i];

                    if (nx >= 0 && nx < c && ny >= 0 && ny < r) {
                        if(grid[ny][nx] != -1){
                            tmpGrid[ny][nx] += spreadDustAmount;
                            spreadCnt++;
                        }
                    }
                }
                tmpGrid[y][x] += (curDustAmount - spreadDustAmount * spreadCnt);
            }
        }

        return tmpGrid;
    }

    public static void moveDust() {
        /**
         * 공기 청정기의 좌표: (curXA, curYA), (curXB, curYB)
         */
        int curXA = puriXA;
        int curYA = puriYA - 1;
        int curXB = puriXB;
        int curYB = puriYB + 1;

        /**
         * 공기 청정기 바람이 부는 반대 방향으로 미세먼지 업데이트
         */
        int[] dirXA = {0, 1, 0, -1};
        int[] dirYA = {-1, 0, 1, 0};
        int[] dirXB = {0, 1, 0, -1};
        int[] dirYB = {1, 0, -1, 0};

        /**
         * 윗부분 옮기기
         */
        int dirA = 0;
        while(true){
            if (curXA == puriXA && curYA == puriYA) {
                break;
            }

            int nxA = curXA + dirXA[dirA];
            int nyA = curYA + dirYA[dirA];

            /**
             * 방향 바꾸기
             */
            if (nxA < 0 || nxA >= c || nyA < 0 || nyA > puriYA) {
                dirA++;
                continue;
            }

            /**
             * grid[nyA][nxA] == -1 이며 공기 청정기
             * 공기 청정기에서 나오는 바람은 미세먼지가 0
             * => grid[curYA][curXA] = 0
             */
            if(grid[nyA][nxA] == -1){
                grid[curYA][curXA] = 0;
                break;
            }
            grid[curYA][curXA] = grid[nyA][nxA];

            curXA = nxA;
            curYA = nyA;
        }

        int dirB = 0;
        while(true){
            if (curXB == puriXB && curYB == puriYB) {
                break;
            }

            int nxB = curXB + dirXB[dirB];
            int nyB = curYB + dirYB[dirB];

            /**
             * 방향 바꾸기
             */
            if (nxB < 0 || nxB >= c || nyB < puriYB || nyB >= r) {
                dirB++;
                continue;
            }
            /**
             * grid[nyB][nxB] == -1 이며 공기 청정기
             * 공기 청정기에서 나오는 바람은 미세먼지가 0
             * => grid[curYB][curXB] = 0
             */
            if(grid[nyB][nxB] == -1){
                grid[curYB][curXB] = 0;
                break;
            }
            grid[curYB][curXB] = grid[nyB][nxB];

            curXB = nxB;
            curYB = nyB;
        }
    }

    public static int countDust() {
        int cnt = 2;
        for (int y = 0; y < r; y++) {
            for (int x = 0; x < c; x++) {
                cnt += grid[y][x];
            }
        }

        return cnt;
    }
}
