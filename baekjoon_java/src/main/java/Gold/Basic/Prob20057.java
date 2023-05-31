package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(마법사 상어와 토네이도)
 *
 * https://www.acmicpc.net/problem/20057
 *
 * Solution: 구현(Simulation)
 */
public class Prob20057 {

    static int n;
    static int[][] grid;
    static int answer = 0;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        grid = new int[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        findAnswer(n/2, n/2, 0, 1);

        System.out.println(answer);
    }

    // d == 0 -> 왼쪽 방향, d == 1 -> 아래쪽 방향, d == 2 -> 오른쪽 방향, d == 3 -> 위쪽 방향
    public static void findAnswer(int x, int y, int d, int move){
        if(x <= 0 && y <= 0){
            return;
        }

        int nx = x;
        int ny = y;
        for(int k = 1; k <= move; k++){
            int sx = nx;
            int sy = ny;
            if(sx == 0 && sy == 0){
                return;
            }

            nx += dx[d];
            ny += dy[d];

            int ax = nx + dx[d];
            int ay = ny + dy[d];

            int ox = ax + dx[d];
            int oy = ay + dy[d];

            int movedSand = 0;
            int initSand = grid[ny][nx];
            grid[ny][nx] = 0;

            /**
             * 0             |         0         | (nx, ny - 2, 2%) |        0          | 0
             * 0             | (ax, ay - 1, 10%) | (nx, ny - 1, 7%) |  (sx, sy - 1, 1%) | 0
             * (ox, oy, 5%)  |     (ax, ay)      |    (nx, ny)      |     (sx, sy)      | 0
             * 0             | (ax, ay + 1, 10%) | (nx, ny + 1, 7%) | (sx, sy + 1, 1%)  | 0
             * 0             |         0         | (nx, ny + 2, 2%) |       0           | 0
             *
             */
            int[] percent = {1, 1, 2, 7, 7, 2, 10, 10, 5};
            if (d % 2 == 0) { // 왼쪽 또는 오른쪽 방향일때
                int[] ux = {sx, sx, nx, nx, nx, nx, ax, ax, ox};
                int[] uy = {sy - 1, sy + 1, ny - 2, ny - 1, ny + 1, ny + 2, ay - 1, ay + 1, oy};
                for (int i = 0; i < 9; i++) {
                    int tmpSand = initSand * percent[i] / 100;
                    movedSand += tmpSand;

                    int tx = ux[i];
                    int ty = uy[i];
                    if (checkPoint(tx, ty)) {
                        answer += tmpSand;
                    } else {
                        grid[ty][tx] += tmpSand;
                    }
                }
            } else { // 아래쪽 또는 위쪽 방향일때
                int[] ux = {sx - 1, sx + 1, nx - 2, nx - 1, nx + 1, nx + 2, ax - 1, ax + 1, ox};
                int[] uy = {sy, sy, ny, ny, ny, ny, ay, ay, oy};
                for (int i = 0; i < 9; i++) {
                    int tmpSand = initSand * percent[i] / 100;
                    movedSand += tmpSand;

                    int tx = ux[i];
                    int ty = uy[i];
                    if (checkPoint(tx, ty)) {
                        answer += tmpSand;
                    } else {
                        grid[ty][tx] += tmpSand;
                    }
                }
            }

            int alphaSand = initSand - movedSand;
            if(checkPoint(ax, ay)){
                answer += alphaSand;
            }else{
                grid[ay][ax] += alphaSand;
            }
        }

        int nextD = (d + 1) % 4;
        // 아래 방향에서 오른쪽 방향으로 변할때, 위쪽 방향에서 왼쪽 방행으로 변할때 토네이도의 이동거리가 1씩 증가함
        findAnswer(nx, ny, nextD, nextD == 1 || nextD == 3 ? move : move + 1);
    }

    public static boolean checkPoint(int x, int y) {
        if(x < 0 || y < 0 || x >= n || y >= n){
            return true;
        }

        return false;
    }
}
