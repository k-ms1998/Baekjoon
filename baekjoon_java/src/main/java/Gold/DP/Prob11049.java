package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 3:
 *
 * 문제:
 * 크기가 N×M인 행렬 A와 M×K인 B를 곱할 때 필요한 곱셈 연산의 수는 총 N×M×K번이다. 행렬 N개를 곱하는데 필요한 곱셈 연산의 수는 행렬을 곱하는 순서에 따라 달라지게 된다.
 * 예를 들어, A의 크기가 5×3이고, B의 크기가 3×2, C의 크기가 2×6인 경우에 행렬의 곱 ABC를 구하는 경우를 생각해보자.
 *
 * AB를 먼저 곱하고 C를 곱하는 경우 (AB)C에 필요한 곱셈 연산의 수는 5×3×2 + 5×2×6 = 30 + 60 = 90번이다.
 * BC를 먼저 곱하고 A를 곱하는 경우 A(BC)에 필요한 곱셈 연산의 수는 3×2×6 + 5×3×6 = 36 + 90 = 126번이다.
 * 같은 곱셈이지만, 곱셈을 하는 순서에 따라서 곱셈 연산의 수가 달라진다.
 *
 * 행렬 N개의 크기가 주어졌을 때, 모든 행렬을 곱하는데 필요한 곱셈 연산 횟수의 최솟값을 구하는 프로그램을 작성하시오. 입력으로 주어진 행렬의 순서를 바꾸면 안 된다.
 *
 * 입력
 * 첫째 줄에 행렬의 개수 N(1 ≤ N ≤ 500)이 주어진다.
 * 둘째 줄부터 N개 줄에는 행렬의 크기 r과 c가 주어진다. (1 ≤ r, c ≤ 500)
 * 항상 순서대로 곱셈을 할 수 있는 크기만 입력으로 주어진다.
 *
 * 출력
 * 첫째 줄에 입력으로 주어진 행렬을 곱하는데 필요한 곱셈 연산의 최솟값을 출력한다. 정답은 231-1 보다 작거나 같은 자연수이다. 또한, 최악의 순서로 연산해도 연산 횟수가 231-1보다 작거나 같다.
 *
 * Solution: DP
 *
 */
public class Prob11049 {

    static Node[] matrix;
    static Node[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        matrix = new Node[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            matrix[i] = new Node(a, b, 0);
        }

        dp = new Node[n][n];
        for (int y = 0; y < n; y++) {
            dp[y][y] = matrix[y];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {

                /**
                 * 대각선으로 이동
                 */
                int y = j;
                int x = i + j;

                int curCnt = Integer.MAX_VALUE;
                int curA = 0;
                int curB = 0;

                /**
                 * dp[y][x]의 값 업데이트
                 */
                for (int r = 0; r < i; r++) {
                    Node mat1 = dp[y][x - r - 1];
                    Node mat2 = dp[y + i - r][x];

                    int mat1A = mat1.getA();
                    int mat1B = mat1.getB();
                    int mat1Cnt = mat1.getCnt();

                    int mat2A = mat2.getA();
                    int mat2B = mat2.getB();
                    int mat2Cnt = mat2.getCnt();

                    int total = mat1A * mat1B * mat2B + mat1Cnt + mat2Cnt;

                    if (total < curCnt) {
                        curCnt = total;
                        curA = mat1A;
                        curB = mat2B;
                    }
                }

                dp[y][x] = new Node(curA, curB, curCnt);
            }
        }

        int ans = dp[0][n - 1].getCnt();
        System.out.println(ans);

    }

    public static class Node{
        int a;
        int b;
        int cnt;

        public Node(int a, int b, int cnt) {
            this.a = a;
            this.b = b;
            this.cnt = cnt;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public int getCnt() {
            return cnt;
        }

        @Override
        public String toString() {
            return "{ " + a + ", " + b + ", " + cnt + " }";
        }
    }
}