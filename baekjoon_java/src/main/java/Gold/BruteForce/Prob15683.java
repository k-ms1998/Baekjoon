package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 4(감시)
 *
 * https://www.acmicpc.net/problem/15683
 *
 * Solution: Brute Force
 */
public class Prob15683 {

    static int n, m;
    static int[][] finalGrid;
    static int cctvCnt = 0;
    static Cctv[] cctvs =  new Cctv[8];

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        finalGrid = new int[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                finalGrid[y][x] = Integer.parseInt(st.nextToken());
                if(1 <= finalGrid[y][x] && finalGrid[y][x] <= 5){
                    cctvs[cctvCnt] = new Cctv(x, y, finalGrid[y][x]);
                    ++cctvCnt;
                }
            }
        }

        observe(0, finalGrid);

        System.out.println(ans);
    }

    public static void observe(int depth, int[][] grid) {
        if (depth == cctvCnt) {
            ans = Math.min(ans, countBlanks(grid));
            return;
        }

        int dirSize = 4;
        if (cctvs[depth].type == 5) {
            dirSize = 1;
        } else if (cctvs[depth].type == 2) {
        }
        for (int i = 0; i < dirSize; i++) {
            Cctv cctv = cctvs[depth];
            int[] updateDirs = new int[4];
            int updateDirsSize = 0;
            if (cctv.type == 1) {
                updateDirs[0] = i;
                updateDirsSize = 1;
            } else if (cctv.type == 2) {
                updateDirs[0] = i;
                updateDirs[1] = (i + 2) % 4;
                updateDirsSize = 2;
            }else if (cctv.type == 3) {
                updateDirs[0] = i;
                updateDirs[1] = (i + 1) % 4;
                updateDirsSize = 2;
            }else if (cctv.type == 4) {
                updateDirs[0] = i;
                updateDirs[1] = (i + 1) % 4;
                updateDirs[2] = (i + 3) % 4;
                updateDirsSize = 3;
            }else if (cctv.type == 5) {
                updateDirs[0] = 0;
                updateDirs[1] = 1;
                updateDirs[2] = 2;
                updateDirs[3] = 3;
                updateDirsSize = 4;
            }

            updateGrid(depth, grid, updateDirs, updateDirsSize);
            observe(depth + 1, grid);
            undoGrid(depth, grid, updateDirs, updateDirsSize);
        }
    }

    public static void updateGrid(int idx, int[][] grid, int[] updateDirs, int updateDirsSize) {
        Cctv cctv = cctvs[idx];
        for (int i = 0; i < updateDirsSize; i++) {
            int nx = cctv.x;
            int ny = cctv.y;
            int curDir = updateDirs[i];
            while (true) {
                nx += dx[curDir];
                ny += dy[curDir];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    break;
                }
                if (grid[ny][nx] == 6) {
                    break;
                }

                if(grid[ny][nx] <= 0){
                    --grid[ny][nx];
                }
            }
        }
    }

    public static void undoGrid(int idx, int[][] grid, int[] updateDirs, int updateDirsSize) {
        Cctv cctv = cctvs[idx];
        for (int i = 0; i < updateDirsSize; i++) {
            int nx = cctv.x;
            int ny = cctv.y;
            int curDir = updateDirs[i];
            while (true) {
                nx += dx[curDir];
                ny += dy[curDir];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    break;
                }
                if (grid[ny][nx] == 6) {
                    break;
                }

                if (grid[ny][nx] <= -1) {
                    ++grid[ny][nx];
                }
            }
        }
    }

    public static int countBlanks(int[][] grid) {
        int cnt = 0;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (grid[y][x] == 0) {
                    ++cnt;
                }
            }
        }

        return cnt;
    }

    public static class Cctv{
        int x;
        int y;
        int type;

        public Cctv(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}