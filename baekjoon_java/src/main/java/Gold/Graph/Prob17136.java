package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(색종이 붙이기)
 *
 * https://www.acmicpc.net/problem/17136
 *
 * Solution: DFS + Brute Force + Backtracking
 * 1. 1이 적힌 모든 칸들을 덮을수 있는 모든 방법 탐색(Brute Force)
 * 2. 각 1이 적힌 칸에 대해서, 크기가 1~5까지인 색종이들에 대해서 각각 사용가능한지 판별
 *  -> 사용 가능하기 위해서는, 해당 크기의 색종이가 경계 밖으로 나가지 않고, 0이 적힌 칸들은 덮지 않아야 한다
 *  -> 해당 크기의 색종이가 사용가능하면, 1이 적힌 칸들을 0으로 바꾼후, 다음 1이 적힌 칸으로 이동
 *      -> 모든 1이 적힌 칸을 탐색 완료하면, 다시 1에서 0으로 바꾼 칸들을 0에서 1로 바꾼다(DFS + Backtracking)
 */
public class Prob17136 {

    static int[][] grid = new int[10][10];
    static Pos[] cover;
    static boolean[] visited;
    static int ans = 0;
    static int[] dx = {0, 1, 2, 3, 4};
    static int[] dy = {0, 1, 2, 3, 4};
    static int[] cnt = {5, 5, 5, 5, 5}; // 각 크기의 색종이들에 대해서 사용 가능한 갯수 저장
    static int uncoveredCnt = 0;
    static final int INF = 100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * 1이 적힌 칸들을 cover 에 저장
         */
        cover = new Pos[100];
        for (int y = 0; y < 10; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < 10; x++) {
                int num = Integer.parseInt(st.nextToken());
                if (num == 1) {
                    grid[y][x] = 1;
                    cover[uncoveredCnt] = new Pos(x, y);
                    uncoveredCnt++;
                }
            }
        }
        visited = new boolean[uncoveredCnt];

        ans = INF;
        if (uncoveredCnt == 0){
            ans = 0;
        }else{
            findAnswer(0, 0);
        }

        System.out.println(ans == INF ? -1 :  ans);
    }

    /**
     * 1이 적힌 칸들을 색종이들로 덮을 수 있는 판별
     * @param idx: 현재 탐색 중인 1이 적힌 칸의 인덱스 값
     * @param used: 현재까지 1이 적힌 칸들을 덮는데 사용된 색종이들의 수
     */
    public static void findAnswer(int idx, int used) {
        /*
        ans 값이 현재까지 사용된 색종이 수 보다 작으면 더 이상 탐색할 필요 X
         */
        if (ans <= used) {
            return;
        }
        /*
        현재 탐색 중인 1이 적힌 칸들중 마지막 칸까지 탐색을 완료 했으면 더 이상 탐색할 필요 X
         */
        if (idx >= uncoveredCnt) {
            if (checkFinalAnswer()) {
                ans = Math.min(ans, used);
            }

            return;
        }
        
        Pos cur = cover[idx];
        int x = cur.x;
        int y = cur.y;

        /**
         * 현재 칸이 이미 탐색한 칸이거나 현재 칸에 적힌 숫자가 0이면 다음 칸 탐색
         */
        if (visited[idx] || grid[y][x] == 0) {
            findAnswer(idx + 1, used);
        }else{
            /**
             * 현재 칸이 아직 덮이지 않은 칸이면 각 크기의 색종이 대해서 사용 가능한지 판별
             */
            for (int i = 0; i < 5; i++) {
                if (cnt[i] <= 0) {
                    continue;
                }
                int nx = x + dx[i];
                int ny = y + dy[i];

                /*
                현재 크기가 작은 색종이 부터 확인 중인데, 현재 색종이가 경계를 벗어나면 현재 색종이보다 뒤차례에 있는 색종이들도 모두 경계를 벗어남
                 */
                if (nx >= 10 || ny >= 10) {
                    return;
                }

                if (checkPaper(x, y, nx, ny)) {
                    updatePaper(x, y, nx, ny, idx, i);
                    findAnswer(idx + 1, used + 1);
                    undoUpdate(x, y, nx, ny, idx, i);
                }
            }
        }
    }

    public static boolean checkPaper(int sx, int sy, int dx, int dy) {
        for (int y = sy; y <= dy; y++) {
            for (int x = sx; x <= dx; x++) {
                if (grid[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void updatePaper(int sx, int sy, int dx, int dy, int idx, int i) {
        visited[idx] = true;
        cnt[i]--;
        for (int y = sy; y <= dy; y++) {
            for (int x = sx; x <= dx; x++) {
                grid[y][x] = 0;
            }
        }
    }

    public static void undoUpdate(int sx, int sy, int dx, int dy, int idx, int i) {
        visited[idx] = false;
        cnt[i]++;
        for (int y = sy; y <= dy; y++) {
            for (int x = sx; x <= dx; x++) {
                grid[y][x] = 1;
            }
        }
    }

    public static boolean checkFinalAnswer() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (grid[y][x] == 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void printGrid() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

    public static class Pos{
        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
