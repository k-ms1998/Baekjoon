package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * N줄에 0 이상 9 이하의 숫자가 세 개씩 적혀 있다. 내려가기 게임을 하고 있는데, 이 게임은 첫 줄에서 시작해서 마지막 줄에서 끝나게 되는 놀이이다.
 * 먼저 처음에 적혀 있는 세 개의 숫자 중에서 하나를 골라서 시작하게 된다. 그리고 다음 줄로 내려가는데, 다음 줄로 내려갈 때에는 다음과 같은 제약 조건이 있다.
 * 바로 아래의 수로 넘어가거나, 아니면 바로 아래의 수와 붙어 있는 수로만 이동할 수 있다는 것이다. 이 제약 조건을 그림으로 나타내어 보면 다음과 같다.
 * 별표는 현재 위치이고, 그 아랫 줄의 파란 동그라미는 원룡이가 다음 줄로 내려갈 수 있는 위치이며, 빨간 가위표는 원룡이가 내려갈 수 없는 위치가 된다
 * . 숫자표가 주어져 있을 때, 얻을 수 있는 최대 점수, 최소 점수를 구하는 프로그램을 작성하시오. 점수는 원룡이가 위치한 곳의 수의 합이다.
 *
 * 입력
 * 첫째 줄에 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 숫자가 세 개씩 주어진다. 숫자는 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 중의 하나가 된다.
 *
 * 출력
 * 첫째 줄에 얻을 수 있는 최대 점수와 최소 점수를 띄어서 출력한다.
 *
 * Solution : DP
 */
public class Prob2096 {

    static int n;
    static Integer[] maxDp;
    static Integer[] minDp;

    static Integer[] tx = {0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        maxDp = new Integer[3];
        minDp = new Integer[3];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            Integer[] currentRow = new Integer[3];
            for (int x = 0; x < 3; x++) {
                int num = Integer.parseInt(st.nextToken());
                if (y == 0) {
                    maxDp[x] = num;
                    minDp[x] = num;
                } else {
                    currentRow[x] = num;
                }
            }

            /**
             * ex:
             * 1 2 3
             * 4 5 6
             * 4 9 0
             *
             * maxDp = [1, 2, 3]
             * currentRow = [4, 5, 6]
             * x == 0 -> maxDp[x] = Math.max(4 + 1, 4 + 2) => 6
             * x == 1 -> maxDp[x] = Math.max(4 + 1, 4 + 2, 4 + 3) => 7
             * x == 2 -> maxDp[x] = Math.max(6 + 2, 6 + 3) => 9
             *
             * maxDp = [6, 7, 9]
             * currentRow = [4, 9, 0]
             * x == 0 -> maxDp[x] = Math.max(4 + 6, 4 + 7) => 13
             * x == 1 -> maxDp[x] = Math.max(9 + 6, 9 + 7, 9 + 9) => 18
             * x == 2 -> maxDp[x] = Math.max(0 + 9, 0 + 3) => 9
             *
             * maxAns = Math.max(13, 18, 9) => 18
             */
            if (y != 0) {
                Integer[] tmpMax = new Integer[3];
                Integer[] tmpMin = new Integer[3];
                for(int x = 0; x < 3; x++){
                    int curMax = 0;
                    int curMin = Integer.MAX_VALUE;
                    for (int i = 0; i < 3; i++) {
                        int nx = x + tx[i];

                        if(nx >= 0 && nx < 3){
                            curMax = Math.max(curMax, maxDp[nx] + currentRow[x]);
                            curMin = Math.min(curMin, minDp[nx] + currentRow[x]);
                        }
                    }
                    tmpMax[x] = curMax;
                    tmpMin[x] = curMin;
                }
                for (int x = 0; x < 3; x++) {
                    maxDp[x] = tmpMax[x];
                    minDp[x] = tmpMin[x];
                }
            }
        }

        int maxAns = 0;
        int minAns = Integer.MAX_VALUE;
        for (int x = 0; x < 3; x++) {
            if (maxAns < maxDp[x]) {
                maxAns = maxDp[x];
            }
            if (minAns > minDp[x]) {
                minAns = minDp[x];
            }
        }

        System.out.println(maxAns + " " + minAns);
    }
}
