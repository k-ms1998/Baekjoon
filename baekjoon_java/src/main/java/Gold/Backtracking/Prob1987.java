package Gold.Backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * 문제
 * 세로 R칸, 가로 C칸으로 된 표 모양의 보드가 있다. 보드의 각 칸에는 대문자 알파벳이 하나씩 적혀 있고, 좌측 상단 칸 (1행 1열) 에는 말이 놓여 있다.
 * 말은 상하좌우로 인접한 네 칸 중의 한 칸으로 이동할 수 있는데, 새로 이동한 칸에 적혀 있는 알파벳은 지금까지 지나온 모든 칸에 적혀 있는 알파벳과는 달라야 한다.
 * 즉, 같은 알파벳이 적힌 칸을 두 번 지날 수 없다.
 * 좌측 상단에서 시작해서, 말이 최대한 몇 칸을 지날 수 있는지를 구하는 프로그램을 작성하시오. 말이 지나는 칸은 좌측 상단의 칸도 포함된다.
 *
 * 입력
 * 첫째 줄에 R과 C가 빈칸을 사이에 두고 주어진다. (1 ≤ R,C ≤ 20) 둘째 줄부터 R개의 줄에 걸쳐서 보드에 적혀 있는 C개의 대문자 알파벳들이 빈칸 없이 주어진다.
 *
 * 출력
 * 첫째 줄에 말이 지날 수 있는 최대의 칸 수를 출력한다.
 *
 * Solution:
 * Backtracking + DFS
 * 
 */
public class Prob1987 {

    static int r;
    static int c;
    static char[][] board;
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

        board = new char[r][c];
        for (int y = 0; y < r; y++) {
            String currentRow = br.readLine();
            for (int x = 0; x < c; x++) {
                /**
                 * String -> char 로 변환 하기
                 */
                board[y][x] = currentRow.charAt(x);
            }
        }

        Boolean[] arr = new Boolean[26];
        for (int i = 0; i < 26; i++) {
            arr[i] = false;
        }
        /**
         * ascii
         */
        int idx = board[0][0] - 'A';
        arr[idx] = true;
        backTrack(0, 0, 1, arr);
        System.out.println(ans);
    }

    public static void backTrack(int x, int y, int curCnt, Boolean[] arr) {
        if (curCnt > ans) {
            ans = curCnt;
        }

        for (int i = 0; i < 4; i++) {
            int tx = x + dx[i];
            int ty = y + dy[i];

            if (tx >= 0 && tx < c && ty >= 0 && ty < r) {
                /**
                 * ex)
                 * 'A' -> idx == 0
                 * if(arr[idx] == false) => 아직 'A' 방문 X
                 */
                int idx = board[ty][tx] - 'A';
                if (arr[idx] == false) {
                    arr[idx] = true;
                    backTrack(tx, ty, curCnt + 1, arr);
                    arr[idx] = false;
                }
            }
        }
    }
}
