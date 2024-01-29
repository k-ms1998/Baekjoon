package Silver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 바둑알 정보를 체크해서 승자를 확인 또는 아직 결정되지 않았는지 확인
 * 	1-1. 둘이 동시에 이기거나, 두 군데 이상에서 동시에 이기는 경우는 없음
 * 2. 바둑알은 정확히 5개 연속해야 한다(6개 이상이면 이긴 경우 X)
 * 	2-1. 직선 또는 대각선 모두 가능
 * 3. 승부가 결정나지 않았으면 0, 검은 색이 이겼으면 1, 흰 색이 이겼으면 2 출력 && 바둑알의 가로줄 번호와 세러줄 번호를 출력
 */
public class Prob2615 {

    // deltaCol, deltaRow -> 오른쪽, 아래쪽, 오른쪽아래(대각선), 오른쪽위(대각선), 왼쪽, 위쪽, 왼쪽위(대각선), 윈쪽아래(대각선) 방향 순
    // idx랑 idx + 4의 방향이 서로 반대 방향이도록 설정
    static final int[] dCol = {1, 0, 1, 1, -1, 0, -1, -1};
    static final int[] dRow = {0, 1, 1, -1, 0, -1, -1, 1};
    static int[][] gameBoard = new int[20][20];
    static boolean[][][] checked = new boolean[8][20][20]; // 중복 탐색을 막기 위한 방문 확인 배열 -> [dir][row][col] => (row, col)에서 dir 방향으로 탐색을 완료했음

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();


        for(int row = 1; row <= 19; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 1; col <= 19; col++) {
                gameBoard[row][col] = Integer.parseInt(st.nextToken()); // 오목판 입력 및 저장
            }
        }

        boolean blackWin = false; // 검은색이 이겼는지 혹인
        int blackRow = 0; // 검은색이 이기는 조건의 가장 왼쪽 바둑알의 row
        int blackCol = 0; // 검은색이 이기는 조건의 가장 왼쪽 바둑알의 col
        boolean whiteWin = false;
        int whiteRow = 0; // 흰색이 이기는 조건의 가장 왼쪽 바둑알의 row
        int whiteCol = 0; // 흰색이 이기는 조건의 가장 왼쪽 바둑알의 col
        for(int row = 1; row <= 19; row++) {
            for(int col = 1; col <= 19; col++) { // 모든 좌표를 탐색
                int curColor = gameBoard[row][col];
                if(curColor != 0) { // 현재 좌표의 바둑알이 있는 경우
                    if((curColor == 1 && blackWin)
                            || (curColor == 2 && whiteWin)) {
                        // 현재 오목이 검은색이고 이미 검은색이 이기는 경우가 있거나,  현재 오목이 흰색이고 이미 흰색이 이기는 경우가 있으면 탐색 X
                        continue;
                    }
                    for(int dir = 0; dir < 4; dir++) { // 시작점부터 오른쪽, 아래쪽, 오른쪽아래(대각선), 오른쪽(대각선) 네 개의 방향 탐색
                        if(checked[dir][row][col]) { // 이미 해당 좌표에서 dir 방향으로 탐색을 한적이 있으면 탐색 진행 X
                            continue;
                        }
                        int oppositeDir  = dir + 4; // 반대 방형
                        checked[dir][row][col] = true;
                        checked[oppositeDir][row][col] = true; // 방문 완료한 좌표 업데이트
                        int count1 = dfs(row, col, curColor, 0, dir); // dir 방향으로 탐색 했을때 총 연속되는 바둑알의 수
                        int count2 = dfs(row, col, curColor, 0, oppositeDir); // 반대 방향으로도 탐색 진행

                        int totalCount = 1 + count1 + count2; // 현재 바둑알 + dir 방향으로 연속해서 갔을때 바둑알의 수 + 반대방향으로 연속해서 갔을때 바둑알의 수 +
                        if(totalCount == 5) { // 5개가 연속하는 경우
                            int leftColDir = col + count1 * dCol[dir];
                            int leftRowDir = row + count1 * dRow[dir]; // dir 방향으로 최대한 갔을때의 좌표
                            int leftColOpposite = col + count2 * dCol[oppositeDir];
                            int leftRowOpposite = row + count2 * dRow[oppositeDir]; // oppositeDir 방향으로 최대한 갔을때의 좌표

                            if(curColor == 1) { // 검은 바둑알 일 경우 업데이트
                                blackWin = true;
                                if(leftColDir < leftColOpposite) {
                                    blackRow = leftRowDir;
                                    blackCol = leftColDir;
                                }else {
                                    blackRow = leftRowOpposite;
                                    blackCol = leftColOpposite;
                                }

                            }else { // 흰 바둑알 일 경우 업데이트
                                whiteWin = true;
                                if(leftColDir < leftColOpposite) {
                                    blackRow = leftRowDir;
                                    blackCol = leftColDir;
                                }else {
                                    whiteRow = leftRowOpposite;
                                    whiteCol = leftColOpposite;
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }

        if(!blackWin && !whiteWin) { // 아직 둘 다 이기지 못한 경우
            System.out.println(0);
        }else if(blackWin) { // 검은색이 이긴 경우
            sb.append(1).append("\n");
            sb.append(String.format("%d %d\n", blackRow, blackCol));
        }else { // 흰색이 이긴 경우
            sb.append(2).append("\n");
            sb.append(String.format("%d %d\n", whiteRow, whiteCol));
        }

        System.out.println(sb);
    }

    public static int dfs(int row, int col, int target, int depth, int dir) {
        // 현재까지 움직인 방향과 동일한 방향으로 움직였을때의 좌표
        int nextRow = row + dRow[dir];
        int nextCol = col + dCol[dir];

        if(nextRow < 1 || nextRow > 19 || nextCol < 1 || nextCol > 19) { // 다음 좌표가 바둑알 판 밖이면 더 이상 탐색 불가능
            return depth;
        }

        int nextColor = gameBoard[nextRow][nextCol];
        if(nextColor == target) { // 다음좌표의 바둑알이 현재 바둑알과 동일하면 더 탐색 가능
            int oppositeDir  = (dir + 4) % 8;
            checked[dir][row][col] = true;
            checked[oppositeDir][row][col] = true;

            return dfs(nextRow, nextCol, target, depth + 1, dir);
        }else { // 다음좌표의 바둑알이 현재 바둑알과 다르면 더 이상의 탐색을 멈춤
            return depth;
        }

    }
}
/*
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 1 2 0 0 2 2 2 1 0 0 0 0 0 0 0 0 0 0
0 0 1 2 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0
0 0 0 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 2 2 0 0 0 0 0 0 0 0 0 0 0 0
0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 2 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
=>
1
3 2

0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
0 0 2 0 0 2 2 2 1 0 0 1 0 0 0 0 0 0 0
0 0 0 2 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0
0 0 0 0 2 0 0 0 0 1 0 0 0 0 0 0 0 0 0
0 0 0 0 0 2 2 1 1 0 0 0 0 0 0 0 0 0 0
0 0 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 1 2 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
=>
1
10 4

0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 2 0 0 2 2 2 1 0 1 0 0 0 0 0 0 0 0
0 0 0 2 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0
0 0 0 0 2 0 0 0 1 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 2 2 1 0 0 0 0 0 0 0 0 0 0 0
0 0 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 1 2 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
=>
1
15 19

0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 1 2 0 0 2 2 2 1 0 0 0 0 0 0 0 0 0 0
0 0 1 2 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0
0 0 0 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 2 2 0 0 0 0 0 0 0 0 0 0 0 0
0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 2 1 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
=> 0
 */
