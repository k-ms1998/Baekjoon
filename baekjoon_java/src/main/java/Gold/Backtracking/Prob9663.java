package Gold.Backtracking;

import java.util.Scanner;

/**
 * Gold 4:
 *
 * 문제
 * N-Queen 문제는 크기가 N × N인 체스판 위에 퀸 N개를 서로 공격할 수 없게 놓는 문제이다.
 *
 * N이 주어졌을 때, 퀸을 놓는 방법의 수를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N이 주어진다. (1 ≤ N < 15)
 *
 * 출력
 * 첫째 줄에 퀸 N개를 서로 공격할 수 없게 놓는 경우의 수를 출력한다.
 * 
 * Solution: Backtracking
 * 1. y = 0 부터 x의 값을 1씩 증가시키면서 Queen 을 놓을 수 있는지 확인
 * 2. Queen 이 놓이기 위해서는 전에 놓았던 Queen 들과 x 좌표가 겹치면 안되며, 이전에 놓였던 Queen 들이 대각선으로 움직였을때 현재 y 좌표에서 어떤 x 좌표로 공격을 할 수 있는지 확인
 *  -> ex: (1 , 0) 에 Queen 을 배치했다고 가정 했을때,
 *      y == 1 일때 Queen 이 배치가 불가능한 위치들은:
 *          1. (1 , 1) -> (1, 0)에 있는 Queen 이 수직으로 공격 가능
 *          2. (0 , 1), (2, 1) -> (1, 0)에 있는 Queen 이 대각선으로 공격 가능
 * 3. 2차원 배열로 이전에 배치했던 Queen 들의 위치를 저장하면서 풀면 메모리 초과 발생
 *  => 그러므로, 1차원 배열로 해결
 *      -> board = new Integer[n]
 *          -> index 0 => y == 0 일때 Queen의 x 좌표 저장, index 1 => y == 1 일때 Queen의 x 좌표 저장 ...
 */
public class Prob9663 {

    static int n;
    static int ans;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();

        Integer[] board = new Integer[n];
        for(int x = 0; x < n; x++){
            board[0] = x;
            backtrack(1, board);
        }

        System.out.println(ans);
    }

    public static void backtrack(int y, Integer[] board) {
        if (y >= n) {
            ans++;
            return;
        }

        for (int tx = 0; tx < n; tx++) {
            boolean flag = true;
            for (int ty = 0; ty < y; ty++) {
                if (board[ty] != tx) { // Col check
                    int diagonalA = board[ty] - (y - ty);
                    int diagonalB = board[ty] + (y - ty);
                    if ((diagonalA < 0 || diagonalA != tx) && (diagonalB >= n || diagonalB != tx)) {
                        continue;
                    }
                    flag = false;
                }
                flag = false;
            }
            if (flag) {
                board[y] = tx;
                backtrack(y + 1, board);
            }
        }
    }
}
