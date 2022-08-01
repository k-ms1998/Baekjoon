package Gold.Backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Prob1987 {

    static int r;
    static int c;
    static String[][] board;
    static int ans = 0;

    /**
     * 상하좌우 이동
     */
    static Integer[] dx = {-1, 1, 0, 0};
    static Integer[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        r = Integer.valueOf(conditions[0]); // row
        c = Integer.valueOf(conditions[1]); // column

        board = new String[r][c];
        for (int y = 0; y < r; y++) {
            String[] currentRow = br.readLine().split("");
            board[y] = currentRow;
        }

        Map<String, Boolean> visited = new HashMap<>();
        visited.put(board[0][0], true);
        backTrack(0, 0, visited, 1);
        System.out.println(ans);
    }

    public static void backTrack(int x, int y, Map<String, Boolean> visited, int curCnt) {
//        System.out.println("visited = " + visited);
        if (curCnt > ans) {
            ans = curCnt;
        }

        for (int i = 0; i < 4; i++) {
            int tx = x + dx[i];
            int ty = y + dy[i];

            if (tx >= 0 && tx < c && ty >= 0 && ty < r) {
                if (!visited.containsKey(board[ty][tx])) {
                    visited.put(board[ty][tx], false);
                }
                if (visited.get(board[ty][tx]) == false) {
                    visited.put(board[ty][tx], true);
                    backTrack(tx, ty, visited, curCnt + 1);
                    visited.put(board[ty][tx], false);
                }
            }
        }
    }
}
