package Review.Gold.Bruteforce;

import java.io.*;

/**
 * Gold 4(N-Queen)
 *
 * https://www.acmicpc.net/problem/9663
 * 
 * Solution: Bruteforce + Backtracking
 * 1. 퀸은 상하좌우, 대각선으로 모두 움직일 수 있음
 *  -> 그러므로, y가 같은 곳에는 2개 이상의 퀸을 놓을 수 없음
 *      -> y 값을 1씩 늘려가면서 dfs 사용
 * 2. 각 y마다 x를 1부터 n까지 확인하면서 퀸을 놓을 수 있는지 확인하고, 놓을 수 있으면 놓고 다음 y로 이동
 *  ->퀸을 놓을때마다, 대각선으로는 다른 퀸들을 놓을 수 없으므로, 대각선으로 퀸이 없다는 것을 확인하고 퀸을 놓는다
 *      -> 대각선으로 퀸을 놓을 수 없도록 unPlaceable 업데이트
 *          -> 이때, unPlaceable 값을 1씩 증가 시켜주는 방식으로 업데이트
 *              -> 단순히 boolean 으로 업데이트하면, 여러 퀸이 도달할 수 있는 위치에 있는 좌표를 업데이트 할때 오류가 생길 수도 있음
 *                  -> ex:  (n = 4, Q는 퀸이 놓인 위치)
 *                          0 Q 0 0
 *                          0 0 0 Q
 *                          Q 0 0 0
 *                          0 0 Q 0 이렇게 퀸들이 배치되어 있을때,
 *                            -> (4, 2), (1,3)은 대각선으로 공통적으로 (2, 4)의 좌표 공격 가능
 *                            -> 해당 조합으로 퀸을 놓았을때의 결과를 확인하고 백트래킹하면 (2,4)의 unPlaceable 값을 업데이트 해야함
 *                            -> (1, 3)의 퀸을 다른 위치로 옮기면 (2,4)의 unPlaceable 값을 변경 시켜야함
 *                            -> 이때, 단순히 true-> false로 하면, (4,2)도 (2,4)를 대각선으로 공격을 할 수 없는 것으로 변경이 됨
 *                              -> But, int로 해서 (2,4)의 unPlaceable 값을 1 감소 시키면, 여전히 (4,2)는 (2,4)를 대각선으로 공격 할 수 있는것을 알 수 있음
 *                                  
 *  -> 퀸을 놓으면 columnVisited 를 업데이트 시켜서 현재 x 좌표에는 더 이상 퀸을 놓을 수 없다는 것을 표시
 * 3. 아무문제 없이 총 n개의 퀸을 배치할 수 있으면 ans 값 증가
 * 4. ans 값 출력
 */
public class Prob9663 {

    static int n;
    static int[] dx = {1, 1, -1, -1};
    static int[] dy = {-1, 1, 1, -1};
    static boolean[] columnVisited;
    static int[][] unPlaceable;
    static int ans = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        columnVisited = new boolean[n + 1];
        unPlaceable = new int[n + 1][n + 1];

        placeQueen(1);

        System.out.println(ans);
    }

    public static void placeQueen(int y) {
        if (y > n) {
            ++ans;
            return;
        }

        for (int x = 1; x < n + 1; x++) {
            if(!columnVisited[x] && unPlaceable[y][x] == 0){
                columnVisited[x] = true;
                updatePlaceable(x, y);
                placeQueen(y + 1);
                columnVisited[x] = false;
                undoPlaceable(x, y);
            }
        }
    }

    public static void updatePlaceable(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x;
            int ny = y;
            while(true){
                nx += dx[i];
                ny += dy[i];
                if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                    break;
                }
                unPlaceable[ny][nx]++;
            }
        }
    }

    public static void undoPlaceable(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x;
            int ny = y;
            while(true){
                nx += dx[i];
                ny += dy[i];
                if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                    break;
                }
                unPlaceable[ny][nx]--;
            }
        }
    }
}