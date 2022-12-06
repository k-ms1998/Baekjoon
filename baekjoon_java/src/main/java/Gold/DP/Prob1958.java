package Gold.DP;

import java.io.*;

/**
 * Gold 3(LCS 3)
 *
 * https://www.acmicpc.net/problem/1958
 *
 * Solution: DP
 * 기본 문자열 두개로의 LCS 문제와 동일하게 풀이
 * 대신, 문자열 3개에 대해서 LCS 를 구하기 때문에 2차원 배열이 아닌 3차열 배열 사용
 */
public class Prob1958 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String strA = br.readLine();
        String strB = br.readLine();
        String strC = br.readLine();

        System.out.println(findLcs(strA, strB, strC));
    }

    public static int findLcs(String strA, String strB, String strC) {
        int sizeA = strA.length();
        int sizeB = strB.length();
        int sizeC = strC.length();

        int[][][] dp = new int[sizeA + 1][sizeB + 1][sizeC + 1];
        for (int i = 1; i < sizeA + 1; i++) {
            char chA = strA.charAt(i - 1);
            for (int j = 1; j < sizeB + 1; j++) {
                char chB = strB.charAt(j - 1);
                for (int r = 1; r < sizeC + 1; r++) {
                    char chC = strC.charAt(r - 1);

                    /**
                     * 현제 인덱스의 값들이 모두 같을 경우 3개의 문자열에서 공통으로 나타나는 문자열의 길이 추가
                     */
                    if(chA == chB && chB == chC){
                        dp[i][j][r] = dp[i - 1][j - 1][r - 1] + 1;
                    }else{
                        dp[i][j][r] = Math.max(dp[i - 1][j][r], Math.max(dp[i][j - 1][r], dp[i][j][r - 1]));
                    }
                }
            }
        }
        return dp[sizeA][sizeB][sizeC];
    }
}