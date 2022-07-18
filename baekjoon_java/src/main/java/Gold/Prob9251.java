package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gold 5: Dynamic Programming
 *
 * 문제
 * LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때, 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.
 * 예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.
 *
 * 입력
 * 첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 최대 1000글자로 이루어져 있다.
 *
 * 출력
 * 첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를 출력한다.
 *
 * Solution:
 * 각 문자열에 대해서, 길이를 1씩 증가 시키면서 공통 부분 수열의 최대 길이를 확인해서 2차원 배열에 저장
 *  ex)
 *  ACAYKP
 *  CAPCAK
 *
 *     C A P C A K
 *   0 0 0 0 0 0 0
 * A 0 0 1 1 1 1 1
 * C 0 1 1 1 2 2 2
 * A 0 1 2 2 2 3 3
 * Y 0 1 2 2 2 3 3
 * K 0 1 2 2 2 3 4
 * P 0 1 2 3 3 3 4
 *
 */
public class Prob9251 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String stringA = br.readLine();
        String stringB = br.readLine();

        String[] arrA = stringA.split("");
        String[] arrB = stringB.split("");

        int n = arrA.length;
        int m = arrB.length;


        Integer[][] dp = new Integer[n+1][m+1];

        for (int y = 0; y < n + 1; y++) {
            for (int x = 0; x < m + 1; x++) {
                /**
                 * 배열 초기화
                 */
                if (y == 0 || x == 0) {
                    dp[y][x] = 0;
                    continue;
                }
                /**
                 * 현재 비교하는 두 개의 문자가 같을때(arrA[y-1] == arrB[x-1]):
                 */
                if (arrA[y - 1].equals(arrB[x - 1])) {
                    /**
                     * 왼쪽 위 대각선에 있는 값 + 1 을 현재 DP에 저장
                     * -> arrA = [A,C,A,Y,K,P], arrB = [C,A,P,C,A,K]
                     * -> if y == 3 && x == 5 && arrA[y-1] == arrB[x-1]:
                     *      -> arrA[y-1] == A && arrB[x-1] == A
                     *      -> 문자열 A = 'ACA', 문자열 B = 'CAPCA' => LSC = 'ACA'
                     *      -> if y == 2 && x == 4:
                     *          -> 문자열 A = 'AC', 문지열 B = 'CAPC' +> LSC = 'AC'
                     *          -> 그러므로, y == 3, x == 5랑 비교해보면, 각 문자열 A랑 B에 'A'만 추가됨
                     *              -> LSC 도 'A'가 추가됨
                     *              -> Therefore, arrA[y-1] == arrB[x-1] 이면, dp[y][x] = dp[y-1][x-1] + 1
                     *
                     */
                    dp[y][x] = dp[y - 1][x - 1] + 1;
                } else {
                    /**
                     * 현재 비교하는 두 개의 문자가 같지 않을때(arrA[y-1] != arrB[x-1]):
                     *
                     * if y == 3 && x == 3 && arrA[y-1] != arrB[x-1]:
                     *      -> 문자열 A = 'ACA', 문자열 B = 'CAP'
                     *      -> 1. y == 2 && x == 3 -> 문자열 A = 'AC',  문자열 B = 'CAP' => LSC = 'C'
                     *      -> 2. y == 3 && x == 2 -> 문자열 A = 'ACA', 문자열 B = 'CA'  => LSC = 'CA'
                     *      -> 그러므로, dp[y][x] = max(dp[y-1][x], dp[y][x-1])
                     *
                     */
                    dp[y][x] = dp[y - 1][x] > dp[y][x - 1] ? dp[y - 1][x] : dp[y][x - 1];
                }
            }
        }

        System.out.println(dp[n][m]);

//        for (Integer[] integers : dp) {
//            for (Integer integer : integers) {
//                System.out.print(integer + " ");
//            }
//            System.out.println();
//        }
    }
}
